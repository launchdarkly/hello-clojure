# LaunchDarkly Sample Clojure Application

We've built a simple console application that demonstrates how to use LaunchDarkly's Java SDK in a Clojure application. There isn't a separate Clojure SDK.
Below, you'll find the basic build procedure, but for more comprehensive instructions, you can visit your [Quickstart page](https://app.launchdarkly.com/quickstart#/)
or the [Java SDK reference guide](https://docs.launchdarkly.com/sdk/server-side/java).

## Build instructions

This project uses the [Leiningen](https://leiningen.org/) build tool. It requires that Java is already installed on your system (version 8 or higher). It will automatically use the latest version of the LaunchDarkly Java SDK with major version 5.

1. Edit `src/hello.clj` and set the value of `sdk-key` to your LaunchDarkly SDK key. If there is an existing boolean feature flag in your LaunchDarkly project that you want to evaluate, set `flag-key` to the flag key.

```clojure
(def sdk-key "1234567890abcdef")

(def flag-key "my-feature-flag-key")
```

2. On the command line, run `lein run`.

You should see the message `"Feature flag '<flag key>' is <true/false> for this user"`.

A second namespace with richer examples can be found in `more_examples.clj`, and can be run via `lein run -m more-examples`.