(defproject storm-jms-demo "0.1.0-SNAPSHOT"
	:dependencies [[org.clojure/clojure "1.5.1"] 
	;https://github.com/rbrush/clara-storm/issues/1#issuecomment-50899227
				   [org.clojure/data.json "0.2.1"] 
				   [org.hornetq/hornetq-core "2.2.2.Final"] 
				   [org.hornetq/hornetq-jms "2.2.2.Final"] 
				   [org.jboss.netty/netty "3.2.0.BETA1"] 
				   [org.jboss.javaee/jboss-jms-api "1.1.0.GA"] 
				   [com.github.juliangamble/storm-jms "0.8.1"] 
				   [javax.jms/jms-api "1.1-rev-1"] 
				   [org.apache.storm/storm-core "0.9.5"]]
	:source-paths ["src/clj"]
	:java-source-paths ["src/java"]
	:aot [storm-jms-demo.PrinterBolt storm-jms-demo.ValueBuyBolt] 
	:main storm-jms-demo.core)

