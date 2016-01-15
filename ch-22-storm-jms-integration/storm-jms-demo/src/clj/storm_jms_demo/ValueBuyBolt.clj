(ns storm-jms-demo.ValueBuyBolt 
	(:gen-class 
		:extends backtype.storm.topology.base.BaseRichBolt 
		:implements [java.io.Serializable]
		:init init
		:state state
		:methods [[initPriceEarningsRatio (java.math.BigDecimal) void]])
	(:require 
			[storm-jms-demo.enqueue-messages :as enqueue-messages] 
			[storm-jms-demo.jms-utils :as jms-utils]) 
		(:import
		[backtype.storm.task OutputCollector TopologyContext] 
		[backtype.storm.topology OutputFieldsDeclarer] 
		[backtype.storm.topology.base BaseRichBolt] 
		[backtype.storm.tuple Fields Tuple Values] 
		[org.json.simple JSONValue]
		[java.io Serializable] 
		[java.math BigDecimal] 
		[java.util Map]))

(defn -init []
	[[] {:pe (make-array BigDecimal 1)
		:collector (make-array OutputCollector 1)}])

(defn -initPriceEarningsRatio 
	[this peVal] 
	(-> (.state this)
		:pe
	(aset 0 peVal)))

(defn -prepare [this ^Map stormConf 
	^TopologyContext context 
	^OutputCollector socollector]
	(-> (.state this) :collector
		(aset 0 socollector)))

(defn is-value-buy[this ^BigDecimal price 
		^BigDecimal eps] 
		(try
	(let [stockPricePE (.divide price eps BigDecimal/ROUND_HALF_UP) 
		state (.state this)
	priceToEarningsRatio (-> state :pe (aget 0))
	valueBuy (< (.compareTo stockPricePE priceToEarningsRatio) 0) 
	result (into-array String [(if valueBuy "BUY" "SELL")])]

	(println "result of value-buy: " (aget result 0))
		result)
	(catch ArithmeticException e (str "caught exception: " (.getMessage
e)))))

(defn -execute 
	[this ^Tuple input]
	(let [jsonString (.getString input 0) 
		obj (JSONValue/parse jsonString) 
		^Map messageMap ^Map obj
		price (BigDecimal. (.get messageMap "price")) 
		eps (BigDecimal. (.get messageMap "eps")) 
		state (.state this)
		collector (-> state :collector (aget 0))]
	(println "Executing tuple in value buy bolt: " + jsonString) 
	(println "message received in bolt = "
		(.get messageMap "stock-code") " "
		(.get messageMap "price") " " 
		(.get messageMap "eps" ))
	(.emit collector input (Values. (is-value-buy this price eps)))
		(.ack collector input)))

(defn -declareOutputFields 
	[this ^OutputFieldsDeclarer declarer] (.declare declarer (Fields. ["peFilter"])))



