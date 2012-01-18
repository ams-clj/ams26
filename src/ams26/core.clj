(ns ams26.core
  (:use ring.adapter.jetty
        compojure.core
        compojure.route
        ring.middleware.file
        ring.util.response))

(defroutes application
  (GET "/" [] (redirect "/index.html"))
  (resources "/"))

(defn start-server
  [port]
  (run-jetty #'application {:port port
                            :join? false}))
