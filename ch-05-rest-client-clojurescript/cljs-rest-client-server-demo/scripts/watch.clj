(require '[cljs.build.api :as b])

(b/watch "src"
  {:main 'cljs-rest-client-server-demo.core
   :output-to "out/cljs_rest_client_server_demo.js"
   :output-dir "out"})
