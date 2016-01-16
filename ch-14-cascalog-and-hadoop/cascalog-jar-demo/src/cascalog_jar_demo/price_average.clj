(ns cascalog_jar_demo.price_average (:require [cascalog.logic.ops :as c]
                                              [cascalog.api :refer :all]) (:gen-class))
(def prices
  [;; [stock-symbol price]
   ["APPL" 527.00] ["MSFT" 26.74] ["YHOO" 19.86] ["FB" 28.76] ["AMZN" 259.15]])
(defn -main []
  (let [price-list (<- [?price]
                       (prices ?stock-symbol ?price))]
    (?<- (stdout) [?avg]
         (price-list ?prices) (c/avg ?prices :> ?avg))))

