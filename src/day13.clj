(ns aoc)
(require '[clojure.string :as str])
(def file "../input/day13.txt")

(defn read-input
  [file]
  (map #(vec (str/split-lines %)) (str/split (slurp file) #"\n\n")))

(defn transpose [m] (apply mapv vector m))

(defn palindrome?
  [coll]
  (and (even? (count coll)) (= (seq coll) (reverse coll))))

(defn reflection? [x] (every? palindrome? x))

;; this check counting from the right only
(defn get-reflection-cols-r
  [x]
  (let [l (count (first x))]
    (loop [i 0
           s x]
      ;(println i)
      ;(println s)
      (cond (= i l) 0.0 ; no columns left -> no reflection
            :else (if (reflection? s)
                    (+ i (* 0.5 (count (first s)))) ; columns on LEFT
                    (recur (inc i) (map #(apply str (drop 1 %)) s)))))))

;; this check counting from the left only
(defn get-reflection-cols-l
  [x]
  (loop [i (count (first x))
         s x]
    ;(println i)
    ;(println s)
    (cond (= i 0) 0.0 ; no columns left -> no reflection
          :else (if (reflection? s)
                  (* 0.5 (count (first s))) ; columns on LEFT
                  (recur (dec i) (map #(apply str (drop-last 1 %)) s))))))

(defn get-reflection-cols
  [x]
  (let [a (get-reflection-cols-r x) b (get-reflection-cols-l x)] (max a b)))

(defn solve1p1
  [input]
  (->> input
       (map get-reflection-cols)
       (reduce +)))

(defn solve1p2
  [input]
  (->> input
       (map transpose)
       (map get-reflection-cols)
       (reduce +)))

(println (+ (solve1p1 (read-input file)) (* 100 (solve1p2 (read-input file)))))
