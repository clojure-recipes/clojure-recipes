(ns sass-dsl.transform-test
  (:require [clojure.test :refer :all]
            [sass-dsl.parse :as parse]
            [sass-dsl.transform :as transform]
            [sass-dsl.common-test :as common-test]
            [sass-dsl.parse-test :as parse-test]
            [sass-dsl.common-test :as common-test]))

(def sass-simpler-nesting-flattened-colons 
  "Given a nested vector of sass declarations - flatten the parents down to prefixes"
  (transform/flatten-colon-suffixes (parse/transform-to-sass-nested-tree common-test/basic-css)))

(deftest flatten-parents
  (testing "Ensure a nested vector of sass declarations is flattened the parents down to prefixes"
    (is (= 
         sass-simpler-nesting-flattened-colons 
         ["table.hl" ["margin: 2em 0" "td.ln" ["text-align: right"]] "li" ["font-size: 1.2em" "font-weight: bold" "font-family: serif"]]))))

;referred to in render-test
(def sass-denested-simpler-nesting-flattened-colons 
  "Wrap the sass-de-nest in a vector zipper"
  (transform/sass-de-nest-flattened-colons-vec-zip sass-simpler-nesting-flattened-colons))

(deftest flatten-colon-suffixes-test
  (testing "Ensure colon suffixes are flat"
    (is (= 
         sass-denested-simpler-nesting-flattened-colons 
         [["table.hl" ["margin: 2em 0"] "table.hl td.ln" ["text-align: right"] "li" ["font-size: 1.2em" "font-weight: bold" "font-family: serif"]] nil]))))

(def sass-constants-nested-tree 
  "Given a sass string return a nested vector"
  (parse/transform-to-sass-nested-tree common-test/basic-css-constants))

(def constants-map (transform/extract-constants sass-constants-nested-tree {}))

(deftest sass-constants-test
  (testing "Ensure we get sass constants"
    (is (= 
         constants-map
         {"$blue" "#3bbfce", "$margin" "16px"}))))

(def sass-constants-stripped-nested-tree (transform/strip-constant-declarations sass-constants-nested-tree))

(deftest remove-constants-test
  (testing "Ensure we stripped the constant declarations"
    (is (= 
         sass-constants-stripped-nested-tree
         [".content-navigation" ["border-color: $blue" "color: darken($blue, 9%)"] ".border" ["padding: $margin / 2" "margin: $margin / 2" "border-color: $blue"]]))))

(deftest remove-constants
  (testing "Ensure we stripped the constant declarations"
    (is (= 
         (transform/replace-const-references "border-color: $blue" {"$blue" "#3bbfce" "$margin" "16px"})
         "border-color: #3bbfce"))))

(deftest replaced-constants-structure
  (testing "Ensure we replaced the constants structure"
    (is (= 
         (transform/replaced-constants-structure-vec-zip 
          sass-constants-stripped-nested-tree
          constants-map)
         [".content-navigation" ["border-color: #3bbfce" "color: darken(#3bbfce, 9%)"] ".border" ["padding: 16px / 2" "margin: 16px / 2" "border-color: #3bbfce"]]))))

