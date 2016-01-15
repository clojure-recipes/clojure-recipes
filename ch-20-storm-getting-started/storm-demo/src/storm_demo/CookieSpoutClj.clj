(ns storm-demo.CookieSpoutClj (:gen-class
:extends backtype.storm.topology.base.BaseRichSpout :implements [java.io.Serializable]
:init init
:state state)
(:import [backtype.storm.utils Utils] [backtype.storm.spout SpoutOutputCollector] [backtype.storm.topology OutputFieldsDeclarer] [backtype.storm.topology.base BaseRichSpout] [backtype.storm.tuple Fields Values]
[java.io Serializable]
[java.util.concurrent LinkedBlockingQueue]))
(def cookie-list
["Almond" "Amaretto" "Biscotti" "Half-Moon" "Bourbon cream"
"Butter pecan" "Caramel shortbread" "Chocolate" "Chocolate chip" "Coconut macaroon" "Custard cream" "Florentine" "Fortune" "Gingerbread" "Ladyfinger" "Lincoln" "Macaroon" "Oreo"
"Peanut butter" "Shortbread" "Wafer"])

(defn- populated-queue []
(let [^LinkedBlockingQueue queue (LinkedBlockingQueue. 100)]
(dotimes [_ 100] (.offer queue (rand-nth cookie-list))) queue))
(defn -init []
[[] {:queue (populated-queue)
:collector (make-array SpoutOutputCollector 1)}])

(defn -open [this conf context ^SpoutOutputCollector socollector] (-> (.state this)
:collector
(aset 0 socollector)))

(defn -nextTuple [this] 
  (let [state (.state this)
		collector (-> state :collector (aget 0))] 
	(if-let [cookie (-> state :queue (.poll)) ]
	  (.emit collector (Values. (into-array [cookie]))) 
	  (Utils/sleep 50))))

(defn -declareOutputFields [this ^OutputFieldsDeclarer declarer] (.declare declarer (Fields. ["current-cookie-type"])))

