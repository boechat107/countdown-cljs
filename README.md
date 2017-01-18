# countdown

A Clojure library designed to ... well, that part is up to you.

## Usage

FIXME

## Development

The environment is targeted to Android by using

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

To [shutdown the avd emulator](http://stackoverflow.com/a/20155436/747872):

```sh
$ adb kill-server
```

## License

Copyright © 2017 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
