(ns monitor-log.core
  (:gen-class)
  (:import (java.io RandomAccessFile)))
(def sleep-interval 5000)

(def pattern "^([\\d.]+) (\\S+) (\\S+) \\[([\\w:/]+\\s[+\\-]\\d{4})\\] \"(.+?)\" (\\d{3}) (\\d+) \"([^\"]+)\" \"([^\"]+)\"")
;(def pattern "([^\\s]+) ([^\\s]+)")


(defn process-log-entry
  "Extract information from the log file line."
  [line]
  (let [log-line-seq (first (re-seq (re-pattern pattern) line))
		log-line-info 
    (zipmap [:everything :ipaddress :block1 :block2 :datetime :request :response
             :bytessent :referer :browser] log-line-seq)] 
  (if (not (empty? log-line-seq))
    (do
      (println "IP Address: " (:ipaddress log-line-info)) 
      (println "Date and Time: " (:datetime log-line-info)) 
      (println "Request: " (:request log-line-info)) 
      (println "Response: " (:response log-line-info)) 
      (println "Bytes Sent: " (:bytessent log-line-info)) 
      (println "Referer: " (:referer log-line-info)) 
      (println "Browser: " (:browser log-line-info)))
    (println "line: \n" line "\ndoesn't match regex")) 
  (println "")))

(defn read-log
  "Loop through the log file - sit in a loop whilst waiting for updates." 
  [^RandomAccessFile log-file]
  (let [line (.readLine log-file)]
    (if line
      (process-log-entry line)))
  (if (= (.getFilePointer log-file) (.length log-file)) 
    (Thread/sleep sleep-interval))
  (recur log-file))

(defn -main
  "Called from the :main entry in the Leiningen file." [& args]
  (println "main run")
  (read-log (RandomAccessFile. "logfile.log" "r")))
