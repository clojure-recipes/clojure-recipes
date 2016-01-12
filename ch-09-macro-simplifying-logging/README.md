**[Clojure Recipes](https://github.com/juliangamble/clojure-recipes) - Chapter 9 - Simplifying Logging with a Macro**

This is the code for Chapter 9 of Clojure Recipes. 

**Benefits**

The primary benefit of macros is extending the compiler. The two features that macros have, that functions do not, is that:

 * they don’t automatically evaluate their arguments
 * macros can evaluate their contents at macro-expansion time, or at runtime.

