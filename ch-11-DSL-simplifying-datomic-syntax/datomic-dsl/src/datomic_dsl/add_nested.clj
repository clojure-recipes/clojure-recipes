(ns datomic-dsl.add-nested (:require
                            [datomic.api :as d]
                            [clojure.zip :as zip]
                            [datomic-dsl.create-nested :refer 
                             [map-zip dsl-zipper zip-nodes]]))

(defn up-stepper
  "Step over the map-entries up." [node]
  (take-while (complement nil?)
              (iterate zip/up node)))

(defn zip-node-map-entries
  "If this is a map-entry - get the key out of the zipper." [me]
  (if (= (type (zip/node me)) clojure.lang.MapEntry)
    (key (zip/node me))))

(defn steps-up
  "Keys to the top for a given node." [node]
  (filter (comp not nil?)
          (map zip-node-map-entries (up-stepper node))))

(defn map-key-seqs
  "For a given dsl, get a sequence of steps to each of the nested nodes." [dsl]
  (filter
   (comp not empty?) (map steps-up
                          (zip-nodes (dsl-zipper dsl)))))

(defn get-key-sequences
  "Get each of the nested nodes as a flat vector of nodes to map over." 
  [nested-dsl]
  (let [nest-datom-dsl-zipper (map-zip nested-dsl)]
    (map vec (map-key-seqs nested-dsl))))

(defn append-in
  "Given a key sequence - append a value at the nested location." 
  [nested-coll nested-key-seq param]
  (assoc-in nested-coll nested-key-seq
            (conj (get-in nested-coll nested-key-seq) param)))

(defn replace-children
  "For a map of maps - replace the children maps with their nested dbids." [[k v]]
  (if (= clojure.lang.PersistentHashMap (class v))
    [k (:db/id v)] [k v]))

(defn add-matching-dbids-to-nested-child
  "Map this onto each of the nested children using a key-sequence to give
them a dbid."
  [nest-datom-dsl-input current-seq]
  (let [current-seq-rev (reverse current-seq)
        parent-seq (drop-last current-seq) 
        last-two (take-last 2 current-seq-rev) 
        dbid (d/tempid :db.part/user) 
        child-dbid {:db/id dbid} 
        parent-keyword (case (count last-two)
                         2 (keyword (first last-two) (second last-two))
                         1 (keyword (first last-two)))
        parent-dbid (if (> (count current-seq) 1) 
                      {parent-keyword dbid})
        child-replace (append-in nest-datom-dsl-input current-seq-rev child-dbid)
        parent-replace (merge child-replace parent-dbid)] 
    parent-replace))

(defn key-sequences
  "Get the key sequences from the dsl and filter out the top-level ones and
return a set of vectors." [dsl]
  (set (map vec
            (map rest
                 (filter #(> (count %) 1)
                         (get-key-sequences dsl))))))

(defn nested-insert-dsl
  "Step through the existing dsl and add matching dbids to future parent and
children entities."
  [schema-name dsl]
  (reduce add-matching-dbids-to-nested-child
          dsl (key-sequences dsl)))

(defn convert-key-sequence-fn
  "For a given dsl entry with dbids - convert it to Datomic syntax - and append it to the results" 
  [nested-dsl]
  (fn [key-sequence]
    (let [local-datom (get-in nested-dsl key-sequence)
          db-id-ref (:db/id local-datom)
          local-datom-minus-dbid (dissoc local-datom :db/id) 
          local-datom-minus-children 
          (into {} 
                (map replace-children local-datom-minus-dbid))
          parent-name (take-last 1 key-sequence)
          last-two (take-last 2 key-sequence) 
          last-one (first (take-last 1 key-sequence)) 
          parent-keyword (case (count last-two)
                           2 (keyword (first last-two) 
                                      (second last-two))
                           1 (keyword (first last-two)))
          new-keys (into {} (map (fn [[k v]] 
                                   [k (keyword (str last-one "/" k))])
                                 local-datom-minus-children))
          new-map (clojure.set/rename-keys 
                   local-datom-minus-children new-keys) 
          new-map-with-dbid (merge new-map {:db/id db-id-ref})]
      new-map-with-dbid)))

(defn key-sequences-rev "Given a dsl - return the   key-sequences to each node of the nested
	structure in reverse order"
  [dsl]
  (map reverse (key-sequences dsl)))

(defn convert-dsl-to-datomic-syntax
  "Map over the key-sequences to step through the dsl with dbids and return
datomic syntax."
  [schema-name dsl]
  (let [dsl-with-dbids (nested-insert-dsl schema-name dsl)]
    (let [convert-key-sequence (convert-key-sequence-fn dsl-with-dbids)] 
      (map convert-key-sequence (key-sequences-rev dsl)))))



