(ns aoc)
(require '[clojure.string :as str])

(defn read-input
  [file]
  (map #(vec (str/split-lines %)) (str/split (slurp file) #"\n\n")))

(defn palindrome? [coll] (= (seq coll) (reverse coll)))

(defn reflection? [x] (every? palindrome? x))

(defn get-reflection-idx
  [x]
  (let [l (count (first x))]
    (loop [i 0
           s x]
      ;(println i)
      ;(println s)
      (cond (= i l) nil
            :else (if (reflection? s)
                    i
                    (recur (inc i) (map #(apply str (drop 1 %)) s)))))))

(defn solve1
  [input]
  (->> input
       (first)
       (get-reflection-idx)))

(println (solve1 (read-input "../test/day13.txt")))
