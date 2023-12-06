(ns aoc)
(require '[clojure.string :as str])
(defn read-input
  [file]
  (map #(map parse-long (re-seq #"\d+" %)) (str/split-lines (slurp file))))

(defn time-distance [[x y]] (map vector x y))
(defn winnables
  [[race-dur best-dist]]
  (for [t (range race-dur)
        :let [s t
              d (* s (- race-dur t))]
        :when (> d best-dist)]
    d))


(defn solve1
  [input]
  (->> input
       (time-distance)
       (map winnables)
       (map count)
       (reduce *)))

(println (solve1 (read-input "../input/day06.txt")))
