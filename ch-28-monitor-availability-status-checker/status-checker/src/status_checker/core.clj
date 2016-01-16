(ns status-checker.core (:gen-class)
    (:import (java.net URL)))
(def status-page-url "http://localhost:9000/")

(def regex #"Error")

(defn fetch-url [url-to-fetch]
  (let [url-obj (URL. url-to-fetch)]
    (with-open [buf (clojure.java.io/reader url-obj)] 
      (apply str (line-seq buf)))))

(defn alert-exists-url []
  (let [page-src (fetch-url status-page-url)
        regex-result (re-find regex page-src)] 
    (if (nil? regex-result)
      (println "OK")
      (println "System down!!!"))))

(defn -main [& args] 
  (while true
    (alert-exists-url) (Thread/sleep 5000)))

