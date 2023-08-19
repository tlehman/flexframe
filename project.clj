(defproject flexframe "0.1.0-SNAPSHOT"
  :description "Flexframe flexibly processes video frames using OpenCV (Clojure-based)"
  :url "https://github.com/tlehman/flexframe"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [org.bytedeco/javacv "1.5.9"]
                 [org.bytedeco/javacpp-platform "1.5.9"]
                 [org.bytedeco/opencv-platform "4.7.0-1.5.9"]]
  :main ^:skip-aot flexframe.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
