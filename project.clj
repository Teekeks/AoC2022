(defproject de.teawork.aoc2022 "1.0.0"
  :description "AoC 2022 solutions"
  :url "https://github.com/Teekeks/AoC2022"
  :license {:name "MIT"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [proto-repl "0.3.1"]
                 [uncomplicate/fluokitten "0.9.1"]
                 [prismatic/plumbing "0.6.0"]
                 [org.clojure/tools.cli "1.0.206"]
                 [clj-http "3.12.3"]]
  :main ^:skip-aot de.teawork.aoc2022
  :target-path "target/%s"
  :resource-paths ["resources"]
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
