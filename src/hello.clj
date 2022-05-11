(ns hello
  "A simple 'Hello World' LaunchDarkly application."
  (:import
   (com.launchdarkly.sdk.server LDClient)
   (com.launchdarkly.sdk LDUser$Builder)))

;; Put your SDK key here
(def sdk-key "")

;; Set the feature flag you want to evaluate
(def flag-key "my-boolean-flag")

;; Basic hello world test
(defn -main
  [& args]
  (when (= "" sdk-key)
    (println "*** Please edit hello.clj to set sdk-key to your LaunchDarkly SDK key first.\n")
    (System/exit 1))
  ;; Here we set up the client, and use `with-open` to ensure the client gets cleanly shutdown before
  ;; the program exits, to deliver any analytics events still waiting in the buffer.
  (with-open [client (LDClient. sdk-key)]
    (if (.isInitialized client)
      (println "*** SDK successfully initialized!\n")
      (do (println "*** SDK failed to initialize\n")
          (System/exit 1)))
    (let [;; Set up the user properties. This user should appear on your LaunchDarkly
          ;; users dashboard soon after you run the demo.
          user (.. (LDUser$Builder. "example-user-key")
                   (name "Sandy")
                   (build))
          flag-value (.boolVariation client flag-key user false)]
      (printf "*** Feature flag '%s' is %s for this user\n\n"
              flag-key flag-value))))
