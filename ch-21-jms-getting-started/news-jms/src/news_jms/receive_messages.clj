(ns news-jms.receive-messages 
  (:import (javax.jms MessageConsumer)
           
           (javax.jms Session))) 
(defn receive-messages

  "receive a message from the queue" [jmsServer]
  (let [cf (.lookup jmsServer "/cf")
        queue (.lookup jmsServer "/queue/queue1")
        connection (.createConnection cf)
        session (.createSession connection false Session/AUTO_ACKNOWLEDGE)
        messageConsumer (.createConsumer session queue)] (.start connection)
       (let [messageReceived (.receive messageConsumer 1000)]
         (println (str "Received message:" (.getText messageReceived)))) 
       (.close connection))
  (println "finished receiving messages"))

