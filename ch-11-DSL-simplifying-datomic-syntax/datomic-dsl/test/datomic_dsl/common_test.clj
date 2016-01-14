(ns datomic-dsl.common-test
  (:require [clojure.test :refer :all]
            [datomic.api :as d]))

(def uri "datomic:mem://datomic-dsl") (d/create-database uri)
(def conn (d/connect uri))