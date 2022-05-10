(ns more-examples
  "Additional examples of Clojure interop with different Java SDK features."
  (:import
   (com.launchdarkly.sdk.server Components LDClient LDConfig$Builder)
   (com.launchdarkly.sdk LDUser$Builder LDValue)
   (java.time Duration)))

;; Put your SDK key here
(def sdk-key "")

;; Set the feature flag you want to evaluate
(def flag-key "my-boolean-flag")

;; Example of targeting a user with several parameters
(defn user-with-more-parameters
  "https://docs.launchdarkly.com/sdk/features/user-config#java"
  []
  (.. (LDUser$Builder. "example-user-key2")
      (name "Ernestina Evans")
      (email "ernestina@example.com")
      ;; Most attributes can optionally be marked "private", i.e. they can be used in targeting rules,
      ;; but won't be sent back to LaunchDarkly.
      (privateIp "1.2.3.4")
      ;; Users also support "custom" attributes, which are arbitrary data.
      (custom "groups" (.. (LDValue/buildArray)
                           (add "Google")
                           (add "Microsoft")
                           (build)))
      (build)))

;; Example of setting configuration on the LDClient
(defn client-with-more-configuration
  "The LDClient optionally takes a variety of config parameters.
  This example illustrates some of the different config options and how to interop with them from Clojure.

  See the Javadocs for the full set of config options:
  https://launchdarkly.github.io/java-server-sdk/com/launchdarkly/sdk/server/LDConfig.Builder.html"
  []
  (LDClient.
   sdk-key
   (.. (LDConfig$Builder.)
       (http
        (.. (Components/httpConfiguration)
            (connectTimeout (Duration/ofSeconds 3))
            (socketTimeout (Duration/ofSeconds 3))))
       (events
        (.. (Components/sendEvents)
            (flushInterval (Duration/ofSeconds 10))))
       (applicationInfo
        (.. (Components/applicationInfo)
            (applicationId "hello-clojure")
            (applicationVersion "1.0.0")))
       (build))))

(defn -main
  [& args]
  (when (= "" sdk-key)
    (println "Please edit more_examples.clj to set sdk-key to your LaunchDarkly SDK key first.")
    (System/exit 1))
  (with-open [^LDClient client (client-with-more-configuration)]
    (let [flag-value (.boolVariation client flag-key (user-with-more-parameters) false)]
      (printf "Feature flag '%s' is %s for this user\n"
              flag-key flag-value))))
