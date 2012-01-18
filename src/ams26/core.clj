(ns ams26.core
  (:use ring.adapter.jetty
        compojure.core
        compojure.route
        ring.middleware.file
        ring.util.response
        net.cgrand.enlive-html
        clojure.data.json))

(def api-key (System/getenv "meetup_key"))

(defn meetup-event-url [key]
  (str "https://api.meetup.com/2/events?key="
       key
       "&sign=true&status=upcoming&group_urlname=The-Amsterdam-Clojure-Meetup-Group&page=1"))

(defn fetch-event [key]
  (first
   (:results
    (read-json
     (slurp (meetup-event-url key))))))

(deftemplate home "public/index.html" [api-key]
  [:title] (content "Amsterdam Clojurians")
  [:a.brand] (content "Amsterdam Clojurians Meetup")
  [:#next-meetup] (content ()))

(defn home-wrap [req]
  {:pre [(:key req)]}
  (home (:key req)))

(defroutes application
  (GET "/" [] home)
  (resources "/"))

(defn start-server
  [port]
  (run-jetty #'application {:port port
                            :join? false}))
