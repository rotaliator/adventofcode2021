(ns adventofcode2021.day01
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def input (->> "day01.txt" io/resource slurp str/split-lines (mapv read-string)))

(comment

  (->> input
       (partition 2 1)
       (filter (fn [[p1 p2]] (< p1 p2)))
       count)
  ;; => 1557

  (->> input
       (partition 3 1)
       (map #(reduce + %))
       (partition 2 1)
       (filter (fn [[p1 p2]] (< p1 p2)))
       count)
  ;; => 1608

  )
