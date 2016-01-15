(ns cascalog-sentence-reader.max-sentence
	(:require
		[cascalog.cascading.util :as util] 
		[cascalog.logic [vars :as v] [ops :as c]] 
		[clojure.string :as s :refer [join]] 
		[cascalog.api :refer :all] 
		[cascalog.cascading.io :refer [get-bytes]] 
		[cascalog.more-taps :refer [hfs-delimited]])
	(:import [cascalogSentenceReader SentenceFileCascading] 
		[cascading.tuple Fields])
	(:gen-class))

(defn sentence-file
	"Custom scheme for dealing with entire files." 
	[field-names]
	(SentenceFileCascading. 
		(util/fields field-names)))

(defn hfs-sentencefile
	"Subquery to return distinct files in the supplied directory. Files will be returned as 2-tuples, formatted as '<filename, file>' The filename is a text object, while the entire, unchopped file is
	encoded as a Hadoop 'BytesWritable' object." 
	[path & opts]
	(let [scheme (-> 
		(:outfields (apply array-map opts) Fields/ALL) (sentence-file))]
	(apply hfs-tap scheme path opts)))

(defmapcatfn split [line]
"reads in a line of string and splits it by regex"
	(s/split line #"[\[\]\\\(\),.)\s]+"))

(defn -main [in out & args]
	(let [counts (<- [?word-count ?sentence]
					((hfs-sentencefile in :skip-header? false) ?sentence _) 
					(split ?sentence :> ?word-dirty)
					(c/count ?word-count))]
	(?<- (hfs-delimited out) [ ?word-count ?sentence] 
		((c/first-n counts 1
					:sort ["?word-count"]
					:reverse true) ?word-count ?sentence))))



