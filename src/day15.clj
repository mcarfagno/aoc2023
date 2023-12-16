(ns aoc)
(require '[clojure.string :as str])

(defn read-input [file] (str/split (str/replace (slurp file) #"\n" "") #","))

(defn str-to-ASCII [x] (map int (seq (char-array x))))
(defn HASH [x] (reduce (fn [a b] (rem (* (+ a b) 17) 256)) 0 (str-to-ASCII x)))

(defn solve1
  [input]
  (->> input
       (map HASH)
       (reduce +)))

(println (solve1 (read-input "../input/day15.txt")))

;; part 2
(def boxes (into {} (for [box (range 256)] [box []])))
;;(println boxes)

(defn split-instr
  [x]
  (if (= 4 (count x))
    [(subs x 0 2) (nth x 2) (nth x 3)]
    [(subs x 0 2) (nth x 2) nil]))

(defn process_eq [boxes id lens] (assoc boxes id (conj (boxes id) lens)))

;; remove lens
(defn process_da
  [boxes id label]
  (assoc boxes id (remove #(= label (first %)) (boxes id))))

(defn process-instr
  [boxes instr]
  (let [[label op focal] (split-instr instr)]
    (case op
      \= (process_eq boxes (HASH label) {label focal})
      \- (process_da boxes (HASH label) label))))

(defn set-lenses [x] (reduce process-instr boxes x))



(defn solve2
  [input]
  (->> input
       (set-lenses)
       (println)))

(println (solve2 (read-input "../test/day15.txt")))
