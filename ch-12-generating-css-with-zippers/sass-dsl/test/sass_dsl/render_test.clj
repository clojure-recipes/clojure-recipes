(ns sass-dsl.render-test
  (:require [clojure.test :refer :all]
            [sass-dsl.render :as render] [sass-dsl.transform :as transform] 
            [sass-dsl.transform-test :as transform-test]))
(deftest basic-css-render-test (testing "Ensure we get CSS output."
                                 (is (=
                                      (render/output-css transform-test/sass-denested-simpler-nesting-flattened-colons "")
                                      "table.hl {\n margin: 2em 0;\n}\n\ntable.hl td.ln {\n text-align: right;\n}\n\nli {\n font-size: 1.2em;\n font-weight: bold;\n font-family: serif;\n}\n\n"))))
(deftest constant-replacement-render-test (testing "Ensure we get CSS."
                                            (is (= (render/output-css
                                                    (transform/replaced-constants-structure-vec-zip-vz transform-test/sass-constants-stripped-nested-tree transform-test/constants-map)
                                                    "")
                                                   ".content-navigation {\n border-color: #3bbfce;\n color: darken(#3bbfce, 9%);\n}\n\n.border {\n padding: 16px / 2;\n margin: 16px / 2;\n border-color: #3bbfce;\n}\n\n"))))

