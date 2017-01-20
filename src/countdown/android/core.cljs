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
      [ui/view {:style {:flex-direction "column" :margin 40 :align-items "center"
                        :justify-content "center" :flex 1}}
       [ui/text {:style {:font-size 30 :font-weight "100" :margin-bottom 20
                         :text-align "center"}}
        "Simple Countdown"]
       [ui/text {:style {:font-size 30 :font-weight "100" :margin-bottom 20
                         :text-align "center"}}
        @cnt]
       ;; Button.
       ;; TODO - Problem:
       ;; how to stop the countdown and cancel the already scheduled events?
       ;; -> :dec-counter handler should be responsible to schedule itself,
       ;; once, if the countdown is running. We never schedule all of them at
       ;; once, as it's done now.
       [ui/btn {:title (if @running? "Stop" "Start")
                :on-press #(when-not @running?
                             (dispatch [:countdown-start]))}]])))

(defn init []
  ;; `dispatch-sync` is used only when the application is being started.
  (dispatch-sync [:initialize-db])
  (.registerComponent ui/app-registry
                      "countdown"
                      #(r/reactify-component app-root)))
