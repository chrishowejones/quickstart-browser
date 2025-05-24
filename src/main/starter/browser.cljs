(ns ^:figwheel-hooks starter.browser
  (:require [reagent.core :as r]
            [reagent.dom.client :as rc]
            [goog.dom :as gdom]))

(defonce state (r/atom {:items ["Hello" "World!"]}))

(defn- new-item []
  [:input
   {:id "new-item"
    :type "text"
    :placeholder "Enter a new item"
    :on-key-down (fn [e]
                   (when (= "Enter" (.-key e))
                     (swap! state update :items conj (.. e -target -value))
                     (set! (.. e -target -value) "")))
    :on-blur (constantly "Enter a new item")}])

(defn- hello-world []
  [:div
   [new-item]
   [:ul (map (fn [item]
               [:li {:key item} item])
             (:items @state))]])

(defonce root (rc/create-root (gdom/getElement "app")))

;; start is called by init and after code reloading finishes
(defn ^{:after-load true :dev/after-load true} start []
  (rc/render root [hello-world]))

(defn init []
  ;; init is called ONCE when the page loads
  ;; this is called in the index.html and must be exported
  ;; so it is available even in :advanced release builds
  (js/console.log "init")
  (start))

;; this is called before any code is reloaded
(defn ^{:before-load true :dev/before-load true} stop []
  (js/console.log "stop"))

(init)
