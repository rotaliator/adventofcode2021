(ns adventofcode2021.day06
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def input (merge
            (zipmap (range 9) (repeat 0))
            (->> "day06.txt" io/resource slurp
                       (#(str/split % #","))
                       (map str/trim)
                       (mapv #(Long/parseLong %))
                       (frequencies))))

(defn new-generation [old-generation]
  (reduce-kv (fn [m k v]
               (cond
                 (< 0 k) (-> m
                             (update (dec k) + v)
                             (update k - v))

                 :else (-> m
                           (update k - v)
                           (update 8 + v)
                           (update 6 + v)))) old-generation old-generation))

(defn state-after [input day]
  (last (take (inc day) (iterate new-generation input))))

(comment
  ;;part 1
  (reduce + (vals (state-after input 80)))
  ;; => 362740

  ;;part 2
  (reduce + (vals (state-after input 256)))
  ;; => 1644874076764
)
