;lein test :only datomic-dsl.create-nested-test
(ns datomic-dsl.create-nested-test
  (:require [clojure.test :refer :all]
            [datomic-dsl.create-nested :refer :all]
            [datomic-dsl.common-test :refer :all]
            [clojure.pprint :refer :all]
            [datomic.api :as d]))

(def borrowevent-schema-dsl  
  "define our data structure"
  {"book" {"title" "string" 
           "author" "string"}
   "borrower" {"name" "string"}
   "date" "string"})

(def nested-book-schema
  "Use the API we've created to create some Datomic schema syntax. 
  Add a pprint to see what this looks like" 
  (create-schema-nested "borrowevent" borrowevent-schema-dsl))

(def trans-result 
  "Add the schema to the database. Save the result in a symbol if we need to look at it."
  (d/transact conn nested-book-schema))

;(pprint nested-book-schema)

(def book-schema-query-result
  "Query the schema to see if it exists - schemas are entities too!"
  (d/touch (d/entity (d/db conn) :borrower/name)))

(deftest schema-created-test
  (testing "Is the schema created with a borrower name?"
    (is book-schema-query-result)))
