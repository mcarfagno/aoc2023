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
    [5 0] 0 ;;Five of a kind
    [4 1] 1 ;;Four of a kind
    [3 2] 2 ;;Full house
    [3 1] 3 ;;Three of a kind
    [2 2] 4 ;;Two pair
    [2 1] 5 ;;One pair
    [1 1] 6));;High card

(defn card-priority
  [x]
  (case (str x)
    "A" 0
    "K" 1
    "Q" 2
    "J" 3
    "T" 4
    "9" 5
    "8" 6
    "7" 7
    "6" 8
    "5" 9
    "4" 10
    "3" 11
    "2" 12))

(defn has-priority?
  [x y order]
  (let [a (get x :hand)
        b (get y :hand)]
    (loop [idx 0]
      (case (compare (order (get a idx)) (order (get b idx)))
        -1 true
        1 false
        0 (recur (inc idx))))))

(defn total_winnings
  [x]
  (reduce +
    (for [i (range (count x))]
      (let [rank (- (count x) i)
            bid (get (nth x i) :bid)]
        (* rank (parse-long bid))))))

(defn solve1
  [input]
  (->> input
       (map get-hand)
       (group-by #(rank (hand-type %))) ;initial "sorting", group by rank
       (into (sorted-map)) ; make sure its sorted
       (map #(sort has-priority? (second %))) ; sort the groups
       (flatten) ; make 1-d
       (total_winnings)))

;; Messy part 2
(defn card-priority-joker
  [x]
  (case (str x)
    "A" 0
    "K" 1
    "Q" 2
    "T" 3
    "9" 4
    "8" 5
    "7" 6
    "6" 7
    "5" 8
    "4" 9
    "3" 10
    "2" 12
    "J" 13))

(defn apply-jokers [j [h1 h2]] [(+ h1 j) h2])

(defn vec-remove
  "remove elem in coll"
  [pos coll]
  (into (subvec coll 0 pos) (subvec coll (inc pos))))

(defn has-priority-joker?
  [x y]
  (let [a (get x :hand)
        b (get y :hand)]
    (loop [idx 0]
      (case (compare (card-priority-joker (get a idx))
                     (card-priority-joker (get b idx)))
        -1 true
        1 false
        0 (recur (inc idx))))))

;; count each occurrence then take the first higher 2
;; this "encodes" the hand
(defn hand-type-joker
  [x]
  (let [occ (for [c camel-cards] (char-count c (get x :hand)))
        jokers (nth occ 3)]
    (apply-jokers jokers (take 2 (reverse (sort (vec-remove 3 (vec occ)))))))) ;remove jokers then count

(defn solve2
  [input]
  (->> input
       (map get-hand)
       (group-by #(rank (hand-type-joker %))) ;initial "sorting", group by rank
       (into (sorted-map)) ; make sure its sorted
       (map #(sort has-priority-joker? (second %))) ; sort the groups
       (flatten) ; make 1-d
       (total_winnings)))

(println (solve1 (read-input "../input/day07.txt")))
(println (solve2 (read-input "../input/day07.txt")))
