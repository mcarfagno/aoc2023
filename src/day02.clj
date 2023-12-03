(ns aoc)
(require '[clojure.string :as str])

(defn read-input [file] (str/split-lines (slurp file)))

(defn get-games
  [x]
  (into (sorted-map)
        (map (fn [a]
               (let [[game-number game-results] (str/split a #":")]
                 [(read-string (str/replace game-number #"Game " ""))
                  (str/split game-results #";")]))
          x)))

;; this is ugly but im bad with regex
(defn get-max-reds
  [a]
  (apply max
    (map read-string (map second (re-seq #"(\d+)[^\d]+red" (str/join a))))))

(defn get-max-blue
  [a]
  (apply max
    (map read-string (map second (re-seq #"(\d+)[^\d]+blue" (str/join a))))))

(defn get-max-green
  [a]
  (apply max
    (map read-string (map second (re-seq #"(\d+)[^\d]+green" (str/join a))))))

(defn solve1
  [input]
  (->> input
       (get-games)
       (filter (fn [[k v]] (<= (get-max-reds v) 12)))
       (filter (fn [[k v]] (<= (get-max-green v) 13)))
       (filter (fn [[k v]] (<= (get-max-blue v) 14)))
       (keys)
       (reduce +)))

(defn solve2
  [input]
  (->> input
       (get-games)
       (map (fn [[k v]]
              (* (* (get-max-reds v) (get-max-green v)) (get-max-blue v))))
       (reduce +)))

(println (solve1 (read-input "../input/day02.txt")))
(println (solve2 (read-input "../input/day02.txt")))
