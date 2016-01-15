**[Clojure Recipes](https://github.com/juliangamble/clojure-recipes) - Chapter 11 - Simplifying Datomic Syntax by Writing a DSL**

This is the code for Chapter 11 of Clojure Recipes. 

**Benefits**

In this chapter you learn how to write a DSL-reader and code-generator in Clojure. This will also aide you when writing Datomic syntax, which can be cumbersome. 

Imagine we wanted to create a new schema in Clojure for a library use case. A naive schema would have a schema for Book, with an Author field and a Title field. To represent this in Datomic, we’d write a schema like this:

    [
    ;; book
    {:db/id #db/id[:db.part/db] 
     :db/ident :book/title
     :db/valueType :db.type/string 
     :db/cardinality :db.cardinality/one 
     :db/doc "A book's title" 
     :db.install/_attribute :db.part/db}
    
    {:db/id #db/id[:db.part/db] 
     :db/ident :book/author 
     :db/valueType :db.type/string 
     :db/cardinality :db.cardinality/one 
     :db/doc "A book's author" 
     :db.install/_attribute :db.part/db}
    ]

We understand that representing a table row concept in a key value store implementation drives some of the complexity here. But at the same time, for a reader who comes from a relational database background—there appears to be lots of repetition there.

Ideally we just want to do something like this:

`(create-schema :book {:title :string :author :string})`

To use a relational database mindset, you can see that the essentials of the *schema name*, the *column name* and *column type* are there.

