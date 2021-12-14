(ns adventofcode2021.day02
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def input (->> "day02.txt" io/resource slurp str/split-lines
                (map #(str/split % #" "))
                (map (fn [[direction step]] [direction (Integer/parseInt step)]))))

(comment
  (->> input
       (reduce (fn [[x y] [direction steps]]
                 (case direction
                   "forward" [(+ x steps) y]
                   "down"    [x (+ y steps)]
                   "up"      [x (- y steps)]))
               [0 0])
       (apply *))
  ;; => 2120749

  (->> input
       (reduce (fn [[x y aim] [direction steps]]
                 (case direction
                   "down"    [x y (+ aim steps)]
                   "up"      [x y (- aim steps)]
                   "forward" [(+ x steps) (+ y (* aim steps)) aim]
                   ))
               [0 0 0])
       (take 2)
       (apply *));; => 2138382217
  )
