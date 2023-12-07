(ns aoc)
(require '[clojure.string :as str])
(def camel-cards ["A" "K" "Q" "J" "T" "9" "8" "7" "6" "5" "4" "3" "2"])

(defn read-input
  [file]
  (map #(str/split % #" ") (str/split-lines (slurp file))))

;; https://stackoverflow.com/questions/74269667/clojure-count-how-many-times-any-character-appear-in-a-string
(defn char-count [chars s] (count (filter (set chars) s)))

(defn get-hand [[hand bid]] {:hand hand, :bid bid})

;; count each occurrence then take the first higher 2
;; this "encodes" the hand
(defn hand-type
  [x]
  (vec (take 2
             (reverse (sort (for [c camel-cards]
                              (char-count c (get x :hand))))))))

(defn rank
  [x]
  (case x
    [5 0] 7 ;;Five of a kind
    [4 1] 6 ;;Four of a kind
    [3 2] 5 ;;Full house
    [3 1] 4 ;;Three of a kind
    [2 2] 3 ;;Two pair
    [2 1] 2 ;;One pair
    [1 1] 1));;High card

;(group-by #(get % :category) [{:category "a" :id 1}
;                                     {:category "a" :id 2}
;                                     {:category "b" :id 3}]
(defn solve1
  [input]
  (->> input
       (map get-hand)
       (group-by #(rank (hand-type %)))
       (println)))

(println (solve1 (read-input "../test/day07.txt")))
