(defproject cascalog-sentence-reader "0.1.0-SNAPSHOT" 
	:description "Sentence stream reader"
	:source-paths ["src/clj"] 
	:java-source-paths ["src/java" "test/java"] 
	:junit ["test/java"]
	;:main cascalog-sentence-reader.max-length 
	:main cascalog-sentence-reader.max-sentence 
	:uberjar-name "sentence-reader.jar" 
	:dependencies [[org.clojure/clojure "1.7.0"]
					[org.apache.hadoop/hadoop-core "0.20.2"] 
					[org.apache.hadoop/hadoop-common "0.22.0"] 
					[junit/junit "4.6"] 
					[cascalog/cascalog-core "2.0.0"] 
					[cascalog/cascalog-more-taps "2.0.0"]])
