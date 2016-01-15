(ns storm-jms-demo.jms-utils
  (:import (recipes.jms.provider ProviderState)
           (org.hornetq.core.config.impl ConfigurationImpl) (org.hornetq.api.core TransportConfiguration) (org.hornetq.core.remoting.impl.netty NettyAcceptorFactory
                                                                                                                                                NettyConnectorFactory)
           (org.hornetq.jms.server.config.impl JMSConfigurationImpl
                                               ConnectionFactoryConfigurationImpl JMSQueueConfigurationImpl) (org.hornetq.jms.server.embedded EmbeddedJMS)
           (java.util ArrayList) (javax.jms Session)))
(defn get-server
  "Return a programamtically configured HornetQ JMS Server instance." []
  (let [configuration (ConfigurationImpl.)
        connectorConfig (TransportConfiguration. (.getName NettyConnectorFactory))
        jmsConfig (JMSConfigurationImpl.) connectorNames (ArrayList.) jmsServer (EmbeddedJMS.)]
    (doto configuration

      (.setPersistenceEnabled false)
      (.setSecurityEnabled false))
    (.add (.getAcceptorConfigurations configuration)
          (TransportConfiguration. (.getName NettyAcceptorFactory))) (.put (.getConnectorConfigurations configuration)
                                                                           "connector"
                                                                           connectorConfig)
    (.add connectorNames "connector")
    (let [cfConfig (ConnectionFactoryConfigurationImpl.
                    "cf"
                    false
                    connectorNames (into-array '("/cf")))
          queueConfig (JMSQueueConfigurationImpl. "queue1"
                                                  nil
                                                  false
                                                  (into-array '("/queue/queue1")))]
      (.add (.getConnectionFactoryConfigurations jmsConfig) ^ConnectionFactoryConfiguration cfConfig)
      (.add (.getQueueConfigurations jmsConfig) queueConfig)) (doto jmsServer
                                                                (.setConfiguration configuration) (.setJmsConfiguration jmsConfig) (.start))
    jmsServer))
(defn stop-server
  "Stop the JMS Server instance." [server]
  (.stop server))
(defn get-destination
  "Get the Queue (Destination) from the server."
  [jms-server]
  (let [destination ^Destination (.lookup jms-server "/queue/queue1")]
    destination))
(defn get-session
  "Get the JMS Session." [jms-server]
  (let [cf (.lookup jms-server "/cf")
        destination (get-destination jms-server)] (println (str "destination: " destination)) (ProviderState/setCF cf) (ProviderState/setDestination destination) (let [connection (.createConnection cf)

                                                                                                                                                                        session (.createSession connection false Session/AUTO_ACKNOWLEDGE)]
                                                                                                                                                                    session)))
(defn get-producer
  "Get the JMS Producer."
  [jms-server]
  (let [session (get-session jms-server)
        destination (get-destination jms-server)
        producer (.createProducer session destination)] producer))


