(ns aoc)
(require '[clojure.string :as str])
(require '[clojure.set :as cs])

(defn read-input [file] (str/split-lines (slurp file)))

;  (map (fn [[winning hand]] [(set (read-string winning))
;                             (set (read-string hand))])
(defn parse-card
  [card]
  (map #(set (filter some? (map parse-long (str/split % #" "))))
    (str/split (str/replace card #"Card \d[:]" "") #"[|]")))
(defn exp [x n] (reduce * (repeat n x)))
(defn solve1
  [input]
  (->> input
       (map parse-card)
       (map (fn [[hand winning]]
              (count (clojure.set/intersection hand winning))))
       (filter pos?)
       (map #(exp 2 (dec %)))
       (reduce +)))


(println (solve1 (read-input "../input/day04.txt")))
