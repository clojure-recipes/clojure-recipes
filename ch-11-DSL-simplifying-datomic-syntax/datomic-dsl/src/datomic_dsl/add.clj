(ns datomic-dsl.add
  (:require 
   [datomic.api :as d]))

(defn convert-dsl-to-keywords-fn
  "pass this in as a parameter so we can use it later"
  [schema-name]
  (fn [[attribute-key attribute-value]]
      ;Build a vector for the datomic fact add, if the value is a map then we'll set the ID
    [(keyword schema-name attribute-key) attribute-value]))

(defn convert-to-map 
  "format the input vectors for the datomic map syntax" 
  [input]
  [(apply hash-map (first (concat input)))])

(defn add-datom 
  "Given a schema name and some key-value pairs, generate a Datomic insert string.
  Note nested values are out of scope."
  [schema-name attributes]
  (let [convert-dsl-to-keywords (convert-dsl-to-keywords-fn schema-name)]
    (convert-to-map
     [(concat [:db/id (d/tempid :db.part/user)]
              (mapcat convert-dsl-to-keywords attributes))])))
