(ns datomic-dsl.create-nested
  (:require 
   [clojure.zip :as zip]
   [datomic.api :as d]))

(defn map-zip 
  "define a custom zipper for maps similar to zip/vector-zip and zip/seq-zip"
  [m]
  (zip/zipper 
   (fn [x] (or (map? x) (map? (nth x 1))))
   (fn [x] (seq (if (map? x) x (nth x 1))))
   (fn [x children] 
     (if (map? x) 
       (into {} children) 
       (assoc x 1 (into {} children))))
   m))

(defn dsl-zipper 
  "Given a dsl, return a zipper so its nested nodes can be walked with a HOF like map."
  [dsl] 
  (map-zip dsl))

;http://josf.info/blog/2014/04/14/seqs-of-clojure-zippers/
(defn zip-nodes
  "Returns all nodes in loc. "
  [loc]
  (take-while (complement zip/end?) ;take until the :end
              (iterate zip/next loc)))


(defn create-schema-nested-fn 
  "When mapped onto a zipped node of a nested schema dsl, create correspdonding dsl entity syntax."
  [schema-name zipped-node]
  (if (= (type (zip/node zipped-node)) clojure.lang.MapEntry)
    (let [elem (zip/node zipped-node)
          attr-key (key elem)
          attr-val (val elem)
          has-parent (not (nil? (zip/up (zip/up zipped-node))))
          parent (if has-parent (first (zip/node (zip/up zipped-node))) schema-name)
          is-nested (map? attr-val)
          first-val-val   (if is-nested :db.type/ref (keyword "db.type"  attr-val))
          doco (str "A " parent "'s " attr-key)]
      {:db/id  (d/tempid :db.part/db)
       :db/ident (keyword parent attr-key)
       :db/valueType first-val-val
       :db/cardinality :db.cardinality/one
       :db/doc doco
       :db.install/_attribute :db.part/db})))

(defn create-schema-nested 
  "Given a schema name and a dsl input, return datomic syntax for creating nested schema entities."
  [schema-name dsl]
  (filter (comp not nil?)
          (map #(create-schema-nested-fn schema-name %)
               (zip-nodes (dsl-zipper dsl)))))


