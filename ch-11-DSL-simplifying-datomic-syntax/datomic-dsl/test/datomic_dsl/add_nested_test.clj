;lein test :only datomic-dsl.add-nested-test
(ns datomic-dsl.add-nested-test
  (:require
   [clojure.test :refer :all] 
   [datomic-dsl.add-nested :refer :all] 
   [datomic-dsl.common-test :refer :all] 
   [datomic-dsl.create-nested-test :refer :all] 
   [clojure.pprint :refer :all]
   [datomic.api :as d])) 

(def nest-datom-dsl

  "Define our data structure." 
  {"borrowevent"                                
   {"book" {"title" "Mutiny on the Bounty" "author" "Charles Nordhoff"}
    "borrower" {"name" "John Smith"} "date" "today"}})

(def nested-datomic-add-syntax
  "Convert our data structure to datomic syntax." 
  (convert-dsl-to-datomic-syntax "borrowevent" nest-datom-dsl))

(pprint nested-datomic-add-syntax)

(def add-result
  "Keep a reference to the transaction result if we need to look at it
later."
  (d/transact conn nested-datomic-add-syntax))

(def borrower-datom 
  "Query against the result. Note the full stop in the query to find the first one."
  (d/touch 
   (d/entity 
    (d/db conn) 
    (d/q '[:find ?e . :where [?e :borrower/name]]
         (d/db conn)))))


(comment
  (def borrower-datom
    "Query against the result. Note the full stop in the query to find the
first one." 
    (let [dconn (d/db conn)
          query (d/q
                 '[:find ?e . :where [?e :borrower/name]] dconn)
          entity (d/entity dconn query)]
      (println dconn)
      (println query)
      (println entity)
      (d/touch entity))))
;  (d/touch
;   (d/entity (d/db conn)
;             (d/q
;              '[:find ?e . :where [?e :borrower/name]] 
;              (d/db conn))))))

;(pprint borrower-datom)

(deftest datom-added-test
  (testing "Is the datom added in the database?"    
    (is (= 1 (count borrower-datom)))))
