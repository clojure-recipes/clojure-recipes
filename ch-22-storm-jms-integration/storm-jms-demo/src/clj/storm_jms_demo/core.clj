
(ns storm-jms-demo.core (:gen-class
                         :name storm-jms-demo.core)
    (:require [storm-jms-demo.enqueue-messages :as enqueue-messages]
              [storm-jms-demo.topology :as topology] [storm-jms-demo.jms-utils :as jms-utils]))
(defn -main [& args]
  (let [jms-server (jms-utils/get-server)]
    (enqueue-messages/enqueue-msgs jms-server) (topology/run-demo jms-server)))

