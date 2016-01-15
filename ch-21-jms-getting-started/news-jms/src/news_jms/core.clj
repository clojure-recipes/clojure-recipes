(ns news-jms.core (:gen-class)
    (:require [news-jms.send-messages :as sender] 
              [news-jms.receive-messages :as receiver])
    (:import (org.hornetq.core.config.impl ConfigurationImpl) 
             (org.hornetq.api.core TransportConfiguration) 
             (org.hornetq.core.remoting.impl.netty 
             	NettyAcceptorFactory
                                                   NettyConnectorFactory)
             (org.hornetq.jms.server.config.impl 
             	JMSConfigurationImpl
                                                 ConnectionFactoryConfigurationImpl 
                                                 JMSQueueConfigurationImpl) 
             (org.hornetq.jms.server.embedded EmbeddedJMS)
             (java.util ArrayList)))
(defn get-server
  "Instantiate and programmatically configure a Hornet JMS Server." [] 
  (let [configuration (ConfigurationImpl.)
        connectorConfig (TransportConfiguration. 
        	(.getName NettyConnectorFactory))
        jmsConfig (JMSConfigurationImpl.)
        connectorNames (ArrayList.)
        queueConfig (JMSQueueConfigurationImpl. 
        	"queue1" nil false 
                                                (into-array '("/queue/queue1"))) 
        jmsServer (EmbeddedJMS.)]
    (doto configuration (.setPersistenceEnabled false) 
          (.setSecurityEnabled false))
    (.add (.getAcceptorConfigurations configuration) 
          (TransportConfiguration. (.getName NettyAcceptorFactory)))
    (.put (.getConnectorConfigurations configuration) 
    	"connector" connectorConfig)
    (.add connectorNames "connector")
    (let [cfConfig (ConnectionFactoryConfigurationImpl. 
                    "cf"
                    false connectorNames 
                    (into-array '("/cf")))]
      (.add (.getConnectionFactoryConfigurations jmsConfig)

            ^ConnectionFactoryConfiguration cfConfig))
    (.add (.getQueueConfigurations jmsConfig) queueConfig) 
    (doto jmsServer
      (.setConfiguration configuration) 
      (.setJmsConfiguration jmsConfig) 
      (.start))
    jmsServer))

(defn stop-server "stop the server" [server] (.stop server))

(defn -main [& args] (println "main called") 
  (let [server (get-server)]
    (sender/send-messages server) 
    (receiver/receive-messages server) 
    (stop-server server))
  (println "done"))

