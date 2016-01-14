(ns sass-dsl.core-test
  (:require [clojure.test :refer :all]
            [sass-dsl.core :as core]
            [sass-dsl.common-test :as common-test]))

(deftest basic-css-test
  (testing "Ensure we get css output"
    (is (= 
         (core/sass-to-css-basic common-test/basic-css)
         "table.hl {\n  margin: 2em 0;\n}\n\ntable.hl td.ln {\n  text-align: right;\n}\n\nli {\n  font-size: 1.2em;\n  font-weight: bold;\n  font-family: serif;\n}\n\n"))))

(deftest basic-css-constants-test
  (testing "Ensure we get css"
    (is (= 
         (core/sass-to-css common-test/basic-css-constants)
         ".content-navigation {\n  border-color: #3bbfce;\n  color: darken(#3bbfce, 9%);\n}\n\n.border {\n  padding: 16px / 2;\n  margin: 16px / 2;\n  border-color: #3bbfce;\n}\n\n"))))
