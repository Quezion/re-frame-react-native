(ns example.app
  (:require
   ["react-native" :as rn]
   ["react" :as react]
   [reagent.core :as r]
   [re-frame.core :as rf]
   [example.events]
   [example.subs]))

;; must use defonce and must refresh full app so metro can fill these in
;; at live-reload time `require` does not exist and will cause errors
;; must use path relative to :output-dir

(defonce splash-img (js/require "../assets/shadow-cljs.png"))

(def styles
  ^js (-> {:container
           {:flex 1
            :backgroundColor "#fff"
            :alignItems "center"
            :justifyContent "center"}
           :title
           {:fontWeight "bold"
            :fontSize 24
            :color "blue"}
           :button
           {:fontWeight "bold"
            :fontSize 18
            :padding 6
            :backgroundColor "blue"
            :borderRadius 10}
           :buttonText
           {:paddingLeft 12
            :paddingRight 12
            :fontWeight "bold"
            :fontSize 18
            :color "white"}
           :label
           {:fontWeight "normal"
            :fontSize 15
            :color "blue"}}
          (clj->js)
          (rn/StyleSheet.create)))

(defn root2 []
  (fn []
    [:> rn/Text {:style (.-title styles)} "Test thing2"]
    )
  )

(defn root []
  (let [counter (rf/subscribe [:get-counter])]
    (fn []
      [:> rn/View {:style (.-container styles)}
       [root2]
       [:> rn/Text {:style (.-title styles)} "Clicked: " @counter]
       [:> rn/TouchableOpacity {:style (.-button styles)
                                :on-press #(rf/dispatch [:inc-counter])}
        [:> rn/Text {:style (.-buttonText styles)} "Click me, I'll count 8"]]
       [:> rn/Image {:source splash-img :style {:width 200 :height 200}}]
       [:> rn/Text {:style (.-label styles)} "Using: shadow-cljs+reagent+re-frame"]])))

(defn start
  {:dev/after-load true}
  []
  (rn/AppRegistry.registerComponent "AwesomeProject" #(r/reactify-component root)))

(defn init []
  (rf/dispatch-sync [:initialize-db])
  (start))
