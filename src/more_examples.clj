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

;; Example of setting configuration on the LDClient
(defn client-with-more-configuration
  "Example of creating an LDClient with several configuration options set.

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

(defn user-with-more-parameters
  "Example of targeting a user with multiple attributes.

  For more info on user attributes:
  https://docs.launchdarkly.com/sdk/features/user-config#java"
  []
  (.. (LDUser$Builder. "example-user-key2")
      (name "Ernestina Evans")
      (email "ernestina@example.com")
      ;; Users also support "custom" attributes, which are arbitrary data.
      (custom "groups" (.. (LDValue/buildArray)
                           (add "Google")
                           (add "Microsoft")
                           (build)))
      (build)))

(defn -main
  [& args]
  (when (= "" sdk-key)
    (println "Please edit more_examples.clj to set sdk-key to your LaunchDarkly SDK key first.")
    (System/exit 1))
  (with-open [^LDClient client (client-with-more-configuration)]
    (let [flag-value (.boolVariation client flag-key (user-with-more-parameters) false)]
      (printf "Feature flag '%s' is %s for this user\n"
              flag-key flag-value))))
