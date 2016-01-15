(ns cascalog-file-output.report-file 
	(:require [cascalog.logic.ops :as c] 
			  [cascalog.api :refer :all]
			  [cascalog.more-taps :refer [hfs-delimited]])
			  (:gen-class))

(defn -main [in out & args] 
	(let [price-list (<- [?price] 
		((hfs-delimited in :skip-header? true) ?stock-symbol
?price-string)
		(read-string ?price-string :> ?price))] 
	(?<- (hfs-delimited out) [?avg]
		(price-list ?prices) (c/avg ?prices :> ?avg))))

