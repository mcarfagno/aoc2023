(ns aoc)
(require '[clojure.string :as str])

(defn read-input [file] (str/split-lines (slurp file)))

(defn str-to-ASCII [x] (map int (seq (char-array x))))
(defn HASH [x] (reduce (fn [a b] (rem (* (+ a b) 17) 256)) 0 x))
(println (str-to-ASCII "HASH"))
(println (HASH (str-to-ASCII "HASH")))

