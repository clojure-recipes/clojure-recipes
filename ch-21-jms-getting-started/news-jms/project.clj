(defproject news-jms "0.1.0-SNAPSHOT" 
	:dependencies [[org.clojure/clojure "1.7.0"]
				   [org.hornetq/hornetq-core "2.2.2.Final"] 
       			   [org.hornetq/hornetq-jms "2.2.2.Final"] 
				   [org.jboss.netty/netty "3.2.0.BETA1"] 
				   [org.jboss.javaee/jboss-jms-api "1.1.0.GA"]]
	:main news-jms.core)
