(ns sass-dsl.render
  (:require [clojure.zip :as zip]
            [sass-dsl.common :as common]))

(defn output-css 

  ([loc acc]
   (let [is-element (common/is-element loc)
         has-parent (common/get-parent loc)
         is-loc-last-element (common/is-last-element loc)
         is-loc-selector (common/has-no-colon loc)
         prefix (if (not is-loc-selector) "  ")
         suffix (if is-loc-selector 
                  " {\n" 
                  (if is-loc-last-element
                    ";\n}\n\n"
                    ";\n"))
         final-line (if is-element 
                      (str prefix (zip/node loc) suffix))]
     (if (zip/end? loc)
       acc
       (recur       
        (zip/next loc)
        (str acc 
             final-line)))))
  ([loc] (output-css loc "")))
