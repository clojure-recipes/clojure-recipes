;lein test :only datomic-dsl.create-test 
(ns datomic-dsl.create-test
  (:require [clojure.test :refer :all] [datomic-dsl.create :refer :all]
            [datomic-dsl.common-test :refer :all] 
            [clojure.pprint :refer :all] [datomic.api :as d]))

(def book-schema
  "Use the API we've created to create some Datomic schema syntax. Add a pprint to see what this looks like."
  (vec
   (create-schema "book"
                  "title" "string"
                  "author" "string"))) 

(pprint book-schema)

(def trans-result
  "Add the schema to the database. Save the result in a symbol if we need to look at it."
  (d/transact conn book-schema))

(def book-schema-query-result
  "Query the schema to see if it exists - schemas are entities too!" 
  (d/touch (d/entity (d/db conn) :book/title)))

(deftest schema-created-test
  (testing "Is the schema created with a book title?"
    (is book-schema-query-result)))


