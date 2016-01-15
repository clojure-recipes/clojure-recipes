(defproject cascalog-load-file "0.1.0-SNAPSHOT"
  :description "Demo loading a file into Cascalog"
  :uberjar-name "cascalog-load-file.jar"
  :repositories  {"conjars" "http://conjars.org/repo/"}
  :main cascalog-load-file.load-file
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [cascading/cascading-hadoop2-mr1 "2.7.0" ]
                 [cascalog/cascalog-core "2.1.1"]
                 [cascalog/cascalog-more-taps "2.1.1"]]
  :profiles {:provided
             {:dependencies
              [[org.apache.hadoop/hadoop-mapreduce-client-jobclient "2.7.0"]
               [org.apache.hadoop/hadoop-common "2.7.0"]]}})
