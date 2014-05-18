(ns brainwallet.core
  (:require [base58.core :as b]
            [btcutils.core :as u]
            [clojure.string :refer [trim split join lower-case]])
  (:import com.lambdaworks.crypto.SCrypt))

;; words.txt must contain one word per line, at least
;; 4096 words for 84-bit entropy
(def words (split (slurp "words.txt") #"\n"))

(def r (java.security.SecureRandom.))

(defn phrase
  "Generate a random 7-word phrase using words from words.txt"
  []
  (let [n (count words)
        nums (take 7 (repeatedly #(.nextInt r n)))
        phrase (map (partial get words) nums)]
    (join " " phrase)))

(defn bitcoin-key
  "Convert a string to a bitcoin key using Scrypt"
  [phrase]
  (let [k (SCrypt/scrypt (.getBytes phrase "UTF8")
                         (byte-array [])
                         16384 8 1 32)]
    (BigInteger. 1 k)))

(defn key-address
  "Derive a key/address pair for a phrase"
  [p]
  (let [k (bitcoin-key p)
        wif (b/encode-check (take-last 32 (.toByteArray k)) (byte -128))
        address (-> k
                    u/public-from-private
                    u/public-to-address)]
    {wif address}))

(defn -main [& args]
  (let [p (if args
            ;; we really should check that it's seven words,
            ;; and perhaps that the words are in the list.
            (-> args first trim lower-case)
            (phrase))
         ka (key-address p)]
    (println p "\n" ka)))