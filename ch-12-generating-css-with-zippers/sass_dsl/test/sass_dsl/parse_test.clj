(ns sass-dsl.parse-test
  (:require [clojure.test :refer :all]
            [sass-dsl.parse :as parse]
            [sass-dsl.common-test :as common-test]))

(deftest get-lines-test
  (testing "Ensure we can splits the lines into a sequence"
    (is (= 
         (parse/get-lines common-test/basic-css)
         ["table.hl" "  margin: 2em 0" "  td.ln" "    text-align: right" "li" "  font:" "    family: serif" "    weight: bold" "    size: 1.2em"]))))

(deftest push-down-test
  (testing "Ensure push-down-indents returns a value"
    (is (= 
         (parse/transform-to-sass-nested-tree-without-zipper common-test/basic-css)
         ["table.hl" ["margin: 2em 0" "td.ln" ["text-align: right"]] "li" ["font:" ["family: serif" "weight: bold" "size: 1.2em"]]]))))
