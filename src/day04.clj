(ns aoc)
(require '[clojure.string :as str])
(require '[clojure.set :as cs])

(defn read-input [file] (str/split-lines (slurp file)))

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
       (map #(if (pos? %) (exp 2 (dec %)) 0))
       (reduce +)))


(defn multipliers
  [x]
  (second (reduce (fn [[ptr multipliers] ticket]
                    (let [indices (range (inc ptr) (+ (inc ptr) ticket))]
                      [(inc ptr)
                       (reduce (fn [old-multipliers i]
                                 (update old-multipliers
                                         i
                                         #(+ % (nth old-multipliers ptr))))
                         multipliers
                         indices)]))
            [0 (vec (repeat (count x) 1))]
            x)))

(defn solve2
  [input]
  (->> input
       (map parse-card)
       (map (fn [[hand winning]]
              (count (clojure.set/intersection hand winning))))
       (multipliers)
       (reduce +)))

(println (solve1 (read-input "../input/day04.txt")))
(println (solve2 (read-input "../input/day04.txt")))
