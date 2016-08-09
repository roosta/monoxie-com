(ns monoxie-com.core
  (:require 
    [reagent.core :as r :refer [atom]]
     [garden.units :as u]
     [cljsjs.snapsvg :as snap]
     [garden.core :as g]
     [goog.labs.userAgent.device :as device]
     [garden.selectors :as s]
     [garden.stylesheet :as stylesheet]
     ))

(enable-console-print!)

;; define your app data so that it doesn't get over-written on reload
(defonce app-state (atom {:text "Hello world!"}))

(defn svg-init []
  (let [svg (js/Snap. "#svg")]
    (.load js/Snap "/svg/splotshy.svg"
           (fn [el]
             (.append svg el)
             ))

    ))

(def style
  (g/css
    [:body
     {:background-color "#1C1B19"
      }]
    [:#wrapper
     {:display "flex"
      :height "100vh"
      :justify-content "center"
      :align-items "center"}]
    [:#svg 
     ])
  )

(defn Page []
  ; (let [timer (timer. (rand-int 10000))]
    (r/create-class
     {:display-name "monoxie.com"
      :component-did-mount #(svg-init)
      ; :component-will-unmount #(.dispose timer)
      ; :component-did-mount #(.init js/smoothscroll)
      ; :component-will-unmount #()
      :reagent-render
      (fn []
        [:div#wrapper
         [:style style]
         [:svg#svg {:width 322 :height 552 :viewBox "0 0 322 552"}]]
        )}))


(r/render-component [Page]
                    (. js/document (getElementById "app")))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)
