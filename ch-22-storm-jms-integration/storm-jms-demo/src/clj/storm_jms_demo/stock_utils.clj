(ns storm-jms-demo.stock-utils
  (:require [storm-jms-demo.price-feed :as price-feed]
            [clojure.data.json :as json])
  (:import (org.hornetq.jms.server.embedded EmbeddedJMS)
           (javax.jms Session MessageProducer TextMessage) (java.util Date)
           (java.util.concurrent Executors)))
(defn return-stock-message
  "Put the pricing message on the queue."
  [stock-code price eps session producer]
  (let [message (.createTextMessage session (str "Price sent at " (Date.)))
        message-map {:stock-code stock-code :price price :eps eps}
        jsonText (json/write-str message-map)]
    (.setText message jsonText)
    (println (str "Sending message: " (.getText message))) (.send producer message)))
(defn pump-stock-prices
  "This provides random prices for the stocks." [num-iterations session producer]
  (dotimes [n num-iterations]
    (Thread/sleep 1000)
    (let [stock-code (rand-nth price-feed/stock-codes)
          price (price-feed/curr-price stock-code)
          stock-info (price-feed/stock-map (keyword stock-code)) eps (:eps stock-info)]
      (return-stock-message stock-code price eps session producer)
      (println (str stock-code " " price " " eps " " stock-info " " (java.util.Date.) "\n")))))


