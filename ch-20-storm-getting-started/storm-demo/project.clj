(defproject storm-demo "0.1.0-SNAPSHOT"
:dependencies [[org.clojure/clojure "1.5.1"] 
;https://github.com/rbrush/clara-storm/issues/1#issuecomment-50899227
[org.apache.storm/storm-core "0.9.5"]] 
:aot [storm-demo.CookieSpoutClj storm-demo.PrinterBolt]
:main storm-demo.core)
