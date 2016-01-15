(ns news-jms.send-messages
  (:import (javax.jms Session MessageProducer TextMessage)
           (java.util Date)))
(defn send-messages
  "Add a message to the queue on the server." [jmsServer]
  (let [cf (.lookup jmsServer "/cf")
        queue (.lookup jmsServer "/queue/queue1")
        connection (.createConnection cf)
        session (.createSession connection false Session/AUTO_ACKNOWLEDGE) 
        producer (.createProducer session queue)
        message (.createTextMessage session 
                                    (str "Hello sent at "
                                         (Date.)))]
    (.send producer message)
    (.close connection))
  (println "sent messages"))

