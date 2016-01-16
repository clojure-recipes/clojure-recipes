(ns sass-dsl.core-test
  (:require [clojure.test :refer :all]
            [sass-dsl.core :as core]
            [sass-dsl.common-test :as common-test]))

(deftest basic-css-test
  (testing "Ensure we get css output"
    (is (.contains
         (core/sass-to-css-basic common-test/basic-css)
         "table.hl {\n"))))

(deftest basic-css-constants-test
  (testing "Ensure we get css"
    (is (.contains
         (core/sass-to-css common-test/basic-css-constants)
         ".content-navigation {\n"))))
