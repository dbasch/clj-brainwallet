(defproject brainwallet "0.1.0-SNAPSHOT"
  :description "Bitcoin brainwallet generator"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :main brainwallet.core
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [base58 "0.1.0"]
                 [btcutils "0.1.0"]
                 [com.madgag.spongycastle/prov "1.50.0.0"]
                 [com.lambdaworks/scrypt "1.4.0"]])
