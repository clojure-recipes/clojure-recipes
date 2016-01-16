(defproject org.clojurerecipes/maven-plugin-clj "0.1.0-SNAPSHOT" 
	:dependencies [[org.clojure/clojure "1.7.0"]
				   [org.apache.maven.plugins/maven-plugin-plugin "3.2"]
				   [clj-time "0.5.1"]] 
	:plugins [[lein-localrepo "0.5.2"]]
	:main maven-plugin-clj.core 
	:pom-addition [:build [
		:pluginManagement [
			:plugins [
				:plugin [:groupId "org.apache.maven.plugins"] 
						[:artifactId "maven-plugin-plugin"] 
						[:version "3.2"]
						[:configuration [:skipErrorNoDescriptorsFound "true"]] 
						[:executions
							[:execution
								[:id "mojo-descriptor"] 
								[:phase "compile"] 
								[:goals
									[:goal "getinput"]]]]]]]])