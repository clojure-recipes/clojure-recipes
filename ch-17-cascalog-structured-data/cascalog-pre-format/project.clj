(defproject cascalog-pre-format "0.1.0-SNAPSHOT" 
	:uberjar-name "query-novel.jar"
	:main cascalog-pre-format.query-novel
	:repositories {"conjars" "http://conjars.org/repo/"} 
	:dependencies [[org.clojure/clojure "1.7.0"]
				   [cascading/cascading-hadoop2-mr1 "2.7.0" ] 
				   [cascalog/cascalog-core "2.1.1"] 
				   [cascalog/cascalog-more-taps "2.1.1"]]
	:profiles {
		:provided {
			:dependencies [[org.apache.hadoop/hadoop-mapreduce-client-jobclient "2.7.0"] 
						   [org.apache.hadoop/hadoop-common "2.7.0"]]}})
