(ns cascalog-pre-format.query-novel (:require [clojure.string :as s]
                                              [cascalog.api :refer :all]
                                              [cascalog.more-taps :refer [hfs-delimited]]) (:gen-class))
(defmapcatfn split [line]
  "Reads in a line of string and splits it by regex."
  (filter #(not (empty? %)) (map clojure.string/trim (s/split line #"\."))))
(defmapcatfn filter-condition [sentence] "Find matching examples."
  (if
   (and
    (> (count (re-seq #"Jim" sentence)) 0)
    (> (count (re-seq #"free man" sentence)) 0))
    (list sentence)))
(defn -main [in out & args] (?<- (hfs-delimited out)
                                 [?filtered]
                                 ((hfs-delimited in :skip-header? false) ?line) (split ?line :> ?sentence)
                                 (filter-condition ?sentence :> ?filtered)))

