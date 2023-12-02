(ns aoc)
(require '[clojure.string :as str])

(def available-cubes {"red" 12, "green" 13, "blue" 14})

(defn read-input [file] (str/split-lines (slurp file)))

;(defn to-cubes
;  [x](
;  assoc
;  {"red" 0, "green" 0, "blue" 0}
;  (for [draw x] [(second draw) (read-string (first draw))]))

(defn game-number [x] (read-string (str/replace x #"Game " "")))

(defn parse-cubes
  [x]
  (for [a (str/split x #";")]
    ((fn [x]
       (vec (map (fn [a] (vec (reverse (str/split a #" "))))
              (str/split x #","))))
      a)))

(defn get-games
  [x]
  (into (sorted-map)
        (map (fn [a]
               (let [b (str/split a #":")]
                 [(game-number (first b)) (parse-cubes (second b))]))
          x)))


(defn solve1
  [input]
  (->> input
       (get-games)
       (println)))

(solve1 (read-input "../test/day02.txt"))
