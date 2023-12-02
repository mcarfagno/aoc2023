(ns aoc)
(require '[clojure.string :as str])

(def available-cubes {"red" 12, "green" 13, "blue" 14})

(defn read-input [file] (str/split-lines (slurp file)))

(defn to-cubes [x] (assoc {"red" 0, "green" 0, "blue" 0} x))

(defn parse-cubes
  [x]
  (for [draw (str/split x #";")]
    ((fn [x] (map (fn [a] (reverse (str/split a #" "))) (str/split x #",")))
      draw)))

(defn get-games
  [x]
  (into (sorted-map)
        (map (fn [a]
               (let [[game-number game-results] (str/split a #":")]
                 [(read-string (str/replace game-number #"Game " ""))
                  (parse-cubes game-results)]))
          x)))


(defn solve1
  [input]
  (->> input
       (get-games)
       (println)))

(solve1 (read-input "../test/day02.txt"))
