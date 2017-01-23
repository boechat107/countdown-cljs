# Countdown

Countdown is a toy mobile app (only Android for now) based
on
[Clojurescript](https://clojurescript.org/),
[re-frame](https://github.com/Day8/re-frame)
and [React Native](https://facebook.github.io/react-native/). The code was
bootstrapped by [re-natal](https://github.com/drapanjanas/re-natal).

The first app we never forget...

## Screenshots

<img src="https://raw.githubusercontent.com/boechat107/countdown-cljs/master/_screenshots/android.png" height="450">

## Motivation

To have my first mobile app!
([details](http://boechat107.github.io/my-first-stupid-mobile-app/)).

## Requirements

* JDK 8
* Leiningen
* Nodejs
* react-native-cli
* Android Studio

These were the main references I used to install all the dependencies:

* https://github.com/drapanjanas/re-natal#dependencies
* https://gadfly361.github.io/gadfly-blog/2016-11-13-clean-install-of-ubuntu-to-re-natal.html
* https://facebook.github.io/react-native/docs/getting-started.html#content

## Development

The environment was targeted to Android by using

```sh
re-natal use-android-device avd
# To use figwheel.
re-natal use-figwheel
```

The following steps are a simple to start a dev environment:

1. `$ android avd`
2. `$ react-native start`
3. `cider-jack-in` on Emacs and then, in the REPL, call
    `(start-figwheel "android")`
4. `$ react-native run-android`

To [shutdown the avd emulator](http://stackoverflow.com/a/20155436/747872):

```sh
$ adb kill-server
```

## License

Copyright © 2017 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
