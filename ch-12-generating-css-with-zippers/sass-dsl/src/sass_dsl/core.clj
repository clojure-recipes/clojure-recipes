(ns sass-dsl.core
  (:require [clojure.test :refer :all]
            [sass-dsl.parse :as parse]
            [sass-dsl.render :as render]
            [sass-dsl.transform :as transform]))

(defn sass-to-css-basic 
  "Given a basic set of SASS, transform it to css"
  [sass-val]
  (-> sass-val
      parse/transform-to-sass-nested-tree 
      transform/flatten-colon-suffixes 
      transform/sass-de-nest-flattened-colons-vec-zip
      render/output-css)) 

(defn sass-to-css 
  "Given some css with constants defined, transform it to css"
  [sass-val]
  (let [tree (parse/transform-to-sass-nested-tree sass-val)]
    (-> tree 
        (transform/replaced-constants-structure-vec-zip-vz  (transform/extract-constants tree {}))
        transform/strip-constant-declarations 
        render/output-css)))
