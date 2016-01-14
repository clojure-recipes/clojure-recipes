(ns cascalog-load-file.load-file
  (:require [cascalog.api :refer :all]
            [cascalog.more-taps :refer [hfs-delimited]])
  (:gen-class))

(defn -main [in]
  (?<- (stdout)
       [?doc ?line]
       ((hfs-delimited in :skip-header? true) ?doc ?line)))