(ns countdown.android.ui
  (:require [reagent.core :refer [adapt-react-class]]))

(def ReactNative (js/require "react-native"))

(def app-registry (.-AppRegistry ReactNative))

(def text (adapt-react-class (.-Text ReactNative)))

(def btn (adapt-react-class (.-Button ReactNative)))

(def view (adapt-react-class (.-View ReactNative)))

(def image (adapt-react-class (.-Image ReactNative)))

(def touchable-highlight (adapt-react-class (.-TouchableHighlight ReactNative)))

(defn alert [title]
  (.alert (.-Alert ReactNative) title))
