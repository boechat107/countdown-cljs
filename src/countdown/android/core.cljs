(ns countdown.android.core
  (:require [reagent.core :as r :refer [atom]]
            [re-frame.core :refer [subscribe dispatch dispatch-sync]]
            [countdown.events]
            [countdown.subs]
            [countdown.android.ui :as ui]))

(def logo-img (js/require "./images/cljs.png"))

(defn app-root []
  (let [cnt (subscribe [:get-counter])
        running? (subscribe [:get-running-flag])]
    (fn []
      [ui/view {:style {:flex-direction "column" :margin 40
                        :justify-content "center" :flex 1}}
       [ui/text {:style {:font-size 30 :font-weight "100" :margin-bottom 20
                         :text-align "center"}}
        "Simple Countdown"]
       [ui/text {:style {:font-size 30 :font-weight "100" :margin-bottom 20
                         :text-align "center"}}
        @cnt]
       [ui/view {:style {:flex-direction "row" :justify-content "space-around"}}
        ;; Start/Stop Button, different title and action accordingly to the app's state.
        [ui/btn {:title (if @running? "Stop" "Start")
                 :on-press #(if @running?
                              (dispatch [:stop-countdown])
                              (dispatch [:start-countdown]))}]
        ;; Resets the app's state to its initial value.
        [ui/btn {:title "Reset" :on-press #(dispatch [:initialize-db])}]]])))

(defn init []
  ;; `dispatch-sync` is used only when the application is being started.
  (dispatch-sync [:initialize-db])
  (.registerComponent ui/app-registry
                      "countdown"
                      #(r/reactify-component app-root)))
