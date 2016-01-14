(ns cascalog-intro.core-test
  (:require [clojure.test :refer :all]
            [cascalog-intro.core :refer :all]
            [cascalog.api :refer :all]
            [cascalog.logic [ops :as c]]))

(def prices
  [;; [stock-symbol price]
   ["APPL" 527.00]
   ["MSFT" 26.74]
   ["YHOO" 19.86]
   ["FB" 28.76]
   ["AMZN" 259.15]])

(deftest find-matching-stock-symbol
  (testing "Given a price, find the corresponding stock symbol."
  	(is (=
      (??<- [?stock-symbol] 
        (prices ?stock-symbol 28.76))
      '(["FB"])))))

(deftest find-matching-stock-symbol-and-show-symbol-and-price
  (testing "Given a price, find the corresponding stock symbol and display the price."
    (is (=
      (??<- [?stock-symbol ?price] 
        (prices ?stock-symbol ?price) (= ?price 28.76))
      '(["FB" 28.76])))))

(deftest find-average-price
  (testing "Given a list of prices, find the average price."
    (let [price-list (<- [?price]  
                       (prices ?stock-symbol ?price))]
      (is (=
        (??<- [?avg] 
          (price-list ?prices) 
          (c/avg ?prices :> ?avg))
        '([172.302]))))))
