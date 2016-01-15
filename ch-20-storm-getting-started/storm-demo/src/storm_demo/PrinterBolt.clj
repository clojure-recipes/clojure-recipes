(ns storm-demo.PrinterBolt (:gen-class
                            :extends backtype.storm.topology.base.BaseBasicBolt) (:import [backtype.storm.topology BasicOutputCollector
                                                                                           OutputFieldsDeclarer]
                                                                                          [backtype.storm.topology.base BaseBasicBolt]
                                                                                          [backtype.storm.tuple Tuple] [java.io Serializable]))
(defn -execute [this ^Tuple tuple ^BasicOutputCollector collector] 
  (println tuple))
(defn -declareOutputFields [this ^OutputFieldsDeclarer ofd])

