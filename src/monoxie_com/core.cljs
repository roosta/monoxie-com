(ns monoxie-com.core
  (:require
    [reagent.core :as r :refer [atom]]
     [goog.dom :as dom]
     [cljsjs.snapsvg :as snap]
     [garden.units :as u]
     [garden.core :as g]
     [goog.labs.userAgent.device :as device]
     [garden.selectors :as s]
     [garden.stylesheet :as stylesheet]
     ))

(enable-console-print!)

;; define your app data so that it doesn't get over-written on reload
(defonce app-state (atom {:text "Hello world!"}))

(defn test-repl
  []
  (.log js/console "hello from emacs")
  )

(defn get-square-width
  [width]
  (loop [x 70]
    (if (= (mod width x) 0)
      x
      (recur (dec x))
      ))
  )

(defn get-square-count
  [wwidth swidth]
  (wwidth / swidth)

  )
(defn svg-init
  []
  (let [window (dom/getWindow)
        svg (js/Snap. "#svg")
        fltr (.filter svg (.blur js/Snap.filter 4 4)) 
        filterChild (aget fltr "node" "firstChild")
        ]
    (doseq [i (range 4)
            j (range 30)]
      (as-> (.rect svg (+ (* j 63.75) 6) (+ (* i 63.75) 12) 58 58) square
        (.attr square #js {:fill 
                      (case i
                        0 "#e5fcc2"
                        1 "#9de0ad"
                        2 "#45ada8"
                        3 "#547980"
                        "#ffffff")
                      :filter fltr
                      })
        (.hover square (fn []
                    (.attr square #js {:filter nil})
                    ; (.animate square #js {:opacity 0} 1000 )
                    ; (.animate js/Snap 0 10 (fn [value] (aset filterChild "attributes" 0 "value" (str value "," value))) 1000)
                    )
                  (fn []
                    (.attr square #js {:filter fltr})
                    ; (.animate js/Snap 0 10 (fn [value] (aset filterChild "attributes" 0 "value" (str value "," value))) 1000)
                    ))))))

(def style
  (g/css
    [:body
     {:background-color "#594f4f"
      }]
    [:#wrapper
     {:display "flex"
      :height "100vh"
      :justify-content "center"
      :align-items "center"}]
    )
  )

(defn Page []
    (r/create-class
     {:display-name "monoxie.com"
      :component-did-mount #(svg-init)
      ; :component-will-unmount #()
      ; :component-did-mount #()
      ; :component-will-unmount #()
      :reagent-render
      (fn []
        [:div#wrapper
         [:style style]
         [:svg#svg {:width "100%"  :height 268}]]
        )}))


(r/render-component [Page] (. js/document (getElementById "app")))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)

#_(defn svg-init []
  (let [svg (js/Snap. "#svg")
        ]
    (.load js/Snap "/svg/splotshy.svg"
           (fn [el]
             (.append svg el)
             (let [path (.select svg "#runner2")]
               ; (.animate path #js {:transform "s0,50t0,50"} 1000000)
               (.animate path #js {:transform "s0,50"} 1000000)
               ; (.animate path #js {:transform "t0,100"} 1000000)
               )
             
             #_(.hover svg
                     (fn [] (.animate svg #js {:opacity 0} 1000) (.-bounce js/mina))
                     (fn [] (.animate svg #js {:opacity 1} 1000) (.-bounce js/mina)))

             ; (.log js/console (.select svg "#runner1"))



             ))

    ))

