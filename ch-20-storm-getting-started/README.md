**[Clojure Recipes](https://github.com/juliangamble/clojure-recipes) - Chapter 20 - Connecting to Datomic from Your Application**

This is the code for Chapter 20 of Clojure Recipes. 

**Benefits**

The benefits discussion is contextual. The process of working out the benefits goes as follows:

*  Why would you choose Storm to solve your business problem? Storm is designed to make stream processing robust. You can retry any step until the whole pipe- line is done and structure your transformations around immutable data so that a repeated message is harmless.
*  Storm is well suited to real-time information, generally information coming in chunks small enough to fit into a Java String object. It was originally developed for reporting on Twitter trends but has the potential to be adopted for stock- price information.
*  If you needed to do a big reduce job, that possibly ran once a day, where you consolidated all the data output from your operations into a single list, then a map-reduce framework like Cascalog is better suited. Storm is focused on stream processing rather than batch processing.


