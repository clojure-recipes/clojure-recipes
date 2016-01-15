(ns storm-jms-demo.enqueue-messages
  (:require [storm-jms-demo.stock-utils :as stock-utils]
            [storm-jms-demo.jms-utils :as jms-utils]) (:import (recipes.jms.provider ProviderState)
                                                               (org.hornetq.jms.server.embedded EmbeddedJMS) (javax.jms Session MessageProducer TextMessage) (java.util Date)))
(defn enqueue-msgs
  "Put some stock prices on the queue." [jms-server]
  (let [session (jms-utils/get-session jms-server)
        producer (jms-utils/get-producer jms-server)] (stock-utils/pump-stock-prices 10 session producer)))

