(ns aoc)
(require '[clojure.string :as str])

(defn read-input [file] (str/split (str/replace (slurp file) #"\n" "") #","))

(defn str-to-ASCII [x] (map int (seq (char-array x))))
(defn HASH [x] (reduce (fn [a b] (rem (* (+ a b) 17) 256)) 0 x))

(defn solve1
  [input]
  (->> input
       (map str-to-ASCII)
       (map HASH)
       (reduce +)))

(println (solve1 (read-input "../input/day15.txt")))
