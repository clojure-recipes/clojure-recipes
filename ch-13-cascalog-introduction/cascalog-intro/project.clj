(defproject cascalog-intro "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [cascalog "2.1.1"]];
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}
             :dev {:dependencies [[org.apache.hadoop/hadoop-core "1.2.1"]]}})
