(ns aoc)
(require '[clojure.string :as str])

(def available-cubes {"red" 12, "green" 13, "blue" 14})

(defn read-input [file] (str/split-lines (slurp file)))

(defn get-games
  [x]
  (into (sorted-map)
        (map (fn [a] (let [b (str/split a #":")] [(first b) (second b)])) x)))

(defn solve1
  [input]
  (->> input
       (get-games)
       (keys)
       (println)))

(solve1 (read-input "../test/day02.txt"))
