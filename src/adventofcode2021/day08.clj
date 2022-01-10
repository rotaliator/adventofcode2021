(ns adventofcode2021.day08
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.set :as set]))

(def input (->> "day08.txt" io/resource slurp
                (str/split-lines)
                (mapv #(str/split % #"[ |]+"))
                (mapv #(split-at 10 %))))

(defn get-wiring
  "
 0
1 2
 3
4 5
 6

segment 0 occurs 8 times and is not in 1 (2 segs)
segment 1 occurs 6 times !
segment 2 occurs 8 times and is in 1 (2 segs)
segment 3 occurs 7 times and is in 4 (4 segs)
segment 4 occurs 4 times !
segment 5 occurs 9 times !
segment 6 occurs 7 times and is not in 4 (4 segs)
"
  [pattern]
  (let [freqs  (-> (apply str pattern) frequencies)
        freqs' (group-by val freqs)
        counts (->> pattern (mapv set) (group-by count))]
    {0 (first (set/difference (set (mapv first (get-in freqs' [8]))) (get-in counts [2 0])))
     1 (get-in freqs' [6 0 0])
     2 (first (set/intersection (set (mapv first (get-in freqs' [8]))) (get-in counts [2 0])))
     3 (first (set/intersection (set (mapv first (get-in freqs' [7]))) (get-in counts [4 0])))
     4 (get-in freqs' [4 0 0])
     5 (get-in freqs' [9 0 0])
     6 (first (set/difference (set (mapv first (get-in freqs' [7]))) (get-in counts [4 0])))}))

(defn get-wiring' [pattern]
  (set/map-invert (get-wiring pattern)))

(def digits
  {#{0 1 2 4 5 6}   0
   #{2 5}           1
   #{0 4 6 3 2}     2
   #{0 6 3 2 5}     3
   #{1 3 2 5}       4
   #{0 1 6 3 5}     5
   #{0 1 4 6 3 5}   6
   #{0 2 5}         7
   #{0 1 4 6 3 2 5} 8
   #{0 1 6 3 2 5}   9})

(defn line->number [[pattern signal]]
  (let [wires  (get-wiring' pattern)
        number (->> signal
                    (map #(into #{} (map wires %)))
                    (map digits)
                    (apply str)
                    (Long/parseLong))]
    number))

(comment
  ;; part 1
  (->> input
       (mapcat last)
       (mapv count)
       (filter #{2 4 3 7}) ;; 1, 4, 7, 8
       count)
  ;; => 352

  ;;part 2
  (->> input
       (map line->number)
       (reduce +))
  ;; => 936117

  )
