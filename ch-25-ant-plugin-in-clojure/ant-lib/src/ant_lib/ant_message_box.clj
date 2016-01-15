(ns ant-lib.ant-message-box
  (:import (javax.swing JFrame JOptionPane)
           (org.apache.tools.ant Task)) 
  (:gen-class
   :name clojurerecipes.AntMessageBox
   :extends org.apache.tools.ant.Task
   :exposes-methods {execute executeSuper getProject getProjectSuper}))

(defn get-JFrame []
  (let [jFrame (proxy [JFrame] [])]
    (doto jFrame
      (.setVisible true)
      (.setDefaultCloseOperation JFrame/EXIT_ON_CLOSE))
    jFrame))

(defn run-JFrame [this jFrame]
  (let [result (JOptionPane/showConfirmDialog jFrame "Close this
application?")] (cond
                  (= result JOptionPane/YES_OPTION) 
                  (System/exit 0)
                  (= result JOptionPane/NO_OPTION) 
                  (.setNewProperty 
                   (.getProjectSuper this) "msgBoxResult" "No")
                  (= result JOptionPane/CANCEL_OPTION) 
                  (.setNewProperty (.getProjectSuper this) "msgBoxResult" "Cancel"))))

(defn -execute [this] (run-JFrame this 
                                  (get-JFrame)))

(defn -main [& args])
