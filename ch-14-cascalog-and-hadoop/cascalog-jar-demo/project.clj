(defproject cascalog-jar-demo "0.1.0-SNAPSHOT"
  :source-paths ["src"]
  :main cascalog_jar_demo.price_average
  :uberjar-name "cascalog-jar-demo.jar"
  :repositories  {"conjars" "http://conjars.org/repo/"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [cascading/cascading-hadoop2-mr1 "2.7.0" ]
                 [cascalog/cascalog-core "2.1.1"]]
  :profiles {:provided
             {:dependencies
              [[org.apache.hadoop/hadoop-mapreduce-client-jobclient "2.7.0"]
               [org.apache.hadoop/hadoop-common "2.7.0"]]}})