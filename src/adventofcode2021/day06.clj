(ns adventofcode2021.day06
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def input (->> "day06.txt" io/resource slurp
                (#(str/split % #","))
                (map str/trim)
                (mapv #(Integer/parseInt %))
                (frequencies)))

(def input' (->> (str/split "3,4,3,1,2" #",")
                 (mapv #(Integer/parseInt %))
                 (frequencies)))

(defn new-generation [old-generation]
  (reduce-kv (fn [m k v]
               (cond
                 (< 0 k)
                 (-> m
                     (update (dec k) #((fnil + 0) % v))
                     (update k #((fnil - 0) % v)))

                 :else
                 (-> m
                     (update k #((fnil - 0) % v))
                     (update 8 #((fnil + 0) % v))
                     (update 6 #((fnil + 0) % v))))) old-generation old-generation))

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
