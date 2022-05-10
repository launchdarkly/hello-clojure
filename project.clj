(defproject hello-clojure "0.0.1"
  ;; Don't warn about version ranges
  :pedantic? false
  :dependencies [[org.clojure/clojure "1.11.1"]

                 [com.launchdarkly/launchdarkly-java-server-sdk "[5.0,6.0)"]
                 [org.slf4j/slf4j-simple "1.7.22"]]
  :main hello)