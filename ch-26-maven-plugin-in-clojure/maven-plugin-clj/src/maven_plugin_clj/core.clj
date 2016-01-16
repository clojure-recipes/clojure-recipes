(ns maven-plugin-clj.core 
  (:gen-class
   :name ^{org.apache.maven.plugins.annotations.Mojo 
   	{:name "getinput"}} org.clojurerecipes.InputBox
   :state state
   :init init
   :prefix "-"
   :extends org.apache.maven.plugin.AbstractMojo :methods 
   [[setPrompt [String] void]
    [setTitle [String] void]
    [setFieldDefault [String] void]]) (:import (javax.swing JFrame JOptionPane))
  (:require
   [clj-time.format :as time-format] [clj-time.core :as time-core]))

(defn -init [] 
            [[] (atom              
              {:prompt "What label would you like to apply?"
               :title "Maven Label Dialog (in Clojure)" :fieldDefault 
               (str "PROJECT_LABEL_"
                    (time-format/unparse (time-format/formatter "yyyyMMdd")
                                         (time-core/now)))})])

(defn setfield
  [this key value]
  (swap! (.state this) into {key value}))

(defn getfield
  [this key]
  (@(.state this) key))

(defn -setPrompt [this loc] 
  (setfield this :prompt loc))

(defn -getPrompt [this]
  (getfield this :prompt))

(defn -setTitle [this loc] 
  (setfield this :title loc))

(defn -getTitle [this]
  (getfield this :title))

(defn -setFieldDefault [this loc] 
  (setfield this :fieldDefault loc))

(defn -getFieldDefault [this]
  (getfield this :fieldDefault))

(defn get-JFrame []
  (let [jFrame (proxy [JFrame] [])]
    (.setVisible jFrame true)
    (.setDefaultCloseOperation jFrame JFrame/EXIT_ON_CLOSE) jFrame))

(defn run-input-box [this jFrame] 
  (let [prompt (-getPrompt this)
        optionPaneTitle (-getTitle this)
        textBoxDefault (-getFieldDefault this)
        result 
        (JOptionPane/showInputDialog jFrame prompt optionPaneTitle 
        	JOptionPane/PLAIN_MESSAGE nil nil textBoxDefault)]
    (.dispose jFrame)
    (java.lang.System/setProperty "MAVEN_LABEL" result)))

(defn -execute [this & args] 
  (run-input-box this (get-JFrame)))

(defn -main [& args])


