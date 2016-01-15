(ns storm-demo.core (:gen-class) (:import
                                  (backtype.storm.topology TopologyBuilder) (storm_demo CookieSpoutClj PrinterBolt) (backtype.storm Config)
                                  (backtype.storm LocalCluster) (backtype.storm.utils Utils)))
(defn -main [& args]
  (let [builder (TopologyBuilder.)
        cookieSpout (CookieSpoutClj.) MY_SPOUT "MySpout"
        conf (Config.)
        cluster (LocalCluster.)]
    (.setSpout builder MY_SPOUT cookieSpout)
    (.shuffleGrouping (.setBolt builder "print" (PrinterBolt.)) MY_SPOUT) (.submitTopology cluster "storm-demo" conf (.createTopology builder)) (Utils/sleep 10000)
    (.killTopology cluster "storm-demo")
    (.shutdown cluster)))

