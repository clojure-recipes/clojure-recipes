(ns storm-jms-demo.topology
  (:require [storm-jms-demo.jms-utils :as jms-utils])
  (:import
   (backtype.storm.topology TopologyBuilder) (recipes.jms.provider HornetQJmsProvider) (backtype.storm.contrib.jms.spout JmsSpout) (backtype.storm.contrib.jms.example JsonTupleProducer) (storm_jms_demo PrinterBolt ValueBuyBolt) (backtype.storm Config)
   (backtype.storm LocalCluster) (backtype.storm.utils Utils) (javax.jms Session) (java.math BigDecimal)))
(defn run-demo
  "Configure the topology with spouts and bolts and submit it to the
cluster."
  [jms-server]
  (let [builder (TopologyBuilder.)

        jmsSpout (JmsSpout.)
        jmsProvider (HornetQJmsProvider.) tuple-producer (JsonTupleProducer.) MY_SPOUT "MySpout"
        conf (Config.)
        cluster (LocalCluster.) valueBuyBolt (ValueBuyBolt.)]
    (doto jmsSpout
      (.setJmsProvider jmsProvider) (.setJmsTupleProducer tuple-producer) (.setJmsAcknowledgeMode Session/CLIENT_ACKNOWLEDGE) (.setDistributed false))
    (.setSpout builder MY_SPOUT jmsSpout)
    (.shuffleGrouping (.setBolt builder "print" (PrinterBolt.)) MY_SPOUT) (.initPriceEarningsRatio valueBuyBolt (BigDecimal. 10)) (.shuffleGrouping (.setBolt builder "buy" valueBuyBolt) MY_SPOUT) (.submitTopology cluster "storm-demo" conf (.createTopology builder)) (Utils/sleep 10000)
    (doto cluster
      (.killTopology "storm-demo") (.shutdown))))


