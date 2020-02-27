__Note:__ This fork is intended to demonstrate `M-x cider-jack-in-cljs` failing when attempting to use the clojure-cli. This functionality seems to run fine when using the shadow-cljs option, but errors when using the clojure-cli with a shadow-cljs alias.

The reproduction steps are included in [cider-repro.txt](cider-repro.txt), tested on CIDER 0.21.0 (Feb 2019)

For questions, contact me on Clojurians Slack with @Quest .

## re-frame-react-native

Example of a shadow-cljs react-native project using [re-frame](https://github.com/day8/re-frame).

This project is simple mashup of [reagent-react-native](https://github.com/thheller/reagent-react-native) & [rn-rf-shadow](https://github.com/PEZ/rn-rf-shadow)

## Instructions

```
$ npm install && cd react-native && npm install
$ shadow-cljs watch app

;; wait for first compile to finish or metro gets confused
$ cd react-native

$ npm start
;; and
$ react-native run-android

;; make sure to disable Fast Refresh / Hot Reloading / Live Reloading
;; see https://facebook.github.io/react-native/docs/debugging

;; production build
$ shadow-cljs release app

;; Create Android release
$ cd react-native/android
$ ./gradlew assembleRelease
;; APK should appear at android/app/build/outputs/apk/release
;; installs in Android as "Hello App Display Name"
```

## Notes

The `react-native` folder was generated by calling `react-native init AwesomeProject` and renaming the folder.

The `:app` build will create an `react-native/app/index.js`. In `release` mode that is the only file needed. In dev mode the `app` directory will contain many more `.js` files.

`:init-fn` is called after all files are loaded and in the case of `expo` must render something synchronously as it will otherwise complain about a missing root component.

`reagent.core` loads `reagent.dom` which will load `react-dom` which we don't have or need. Including the `src/main/reagent/dom.cljs` to create an empty shell. Copied from [re-natal](https://github.com/drapanjanas/re-natal/blob/master/resources/cljs-reagent6/reagent_dom.cljs).
