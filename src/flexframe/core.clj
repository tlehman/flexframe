(ns flexframe.core
  (:gen-class) ; This tells Clojure to generate a Java class, useful for compiling to a jar file
  (:import [org.bytedeco.opencv.global opencv_highgui]
           [org.bytedeco.opencv.opencv_core Mat CvArr]
           [org.bytedeco.opencv.opencv_videoio VideoCapture]
           [org.bytedeco.javacpp Loader]
           [org.bytedeco.javacv OpenCVFrameConverter$ToMat]
           [org.bytedeco.javacv Java2DFrameConverter]
           [javax.swing JFrame JLabel ImageIcon]
           [java.awt Image]))

(defn initialize-camera []
  "Load the native libraries and test access to the camera"
  (Loader/load CvArr) ; Load the native OpenCV libraries
  (VideoCapture. 0))

(defn initialize-display []
  "Create the Swing JFrame object used to display the current buffered image"
  (let [label (JLabel.)
        jframe (JFrame. "Camera Display")]
    (.add jframe label)
    (.setSize jframe 640 480)
    (.setDefaultCloseOperation jframe JFrame/EXIT_ON_CLOSE)
    (.setVisible jframe true)
    {:jframe jframe, :label label}))

(defn capture-frame [^org.bytedeco.opencv.opencv_videoio.VideoCapture capture]
  "Take the VideoCapture object and read it into an OpenCV Mat"
  (let [frame (Mat.)]
    (.read capture frame)
    frame))

(defn mat-to-buffered-image [^org.bytedeco.opencv.opencv_core.Mat mat]
  (let [open-cv-converter (OpenCVFrameConverter$ToMat.)
        java2d-converter (Java2DFrameConverter.)
        frame (.convert open-cv-converter mat)]
    (.convert java2d-converter frame)))

(defn update-display [display image]
  "Update the JFrame's ImageLabel with a new image"
  (let [label (:label display)
        icon (ImageIcon. image)]
    (.setIcon label icon)))

(defn capture-and-display []
  (let [capture (initialize-camera)
        display (initialize-display)]
    (loop []
      (let [frame (capture-frame capture)
            image (mat-to-buffered-image frame)]
        (update-display display image)
        (Thread/sleep 33)) ; Delay to control the frame rate
      (recur))))

(capture-and-display)
