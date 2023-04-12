import java.util.function.Consumer
import javafx.animation.{KeyFrame, KeyValue, Timeline}
import javafx.application.Application
import javafx.scene.{Group, Node, Scene}
import javafx.scene.paint.Color
import javafx.scene.shape.{Circle, StrokeType}
import javafx.stage.Stage
import javafx.util.Duration
import java.lang.Math.random

object Main extends App {
  Application.launch(classOf[Main], args: _*)
}

class Main extends Application {

  override def start(primaryStage: Stage): Unit = {

    /* 
      背景が黒の幅800px、高さ600pxのエリアを作成

      Groupクラス...シーングラフ内の複数のノードをまとめたい場合に使用する
    */
    val root = new Group()
    val scene = new Scene(root, 800, 600, Color.BLACK)
    primaryStage.setScene(scene)


    /* 
      円を管理するcirclesというGroupクラスのオブジェクトを作り、
      そこに縁と中身の色が白でそれぞれ透明度が設定された円のオブジェクトを作成して、 circlesに追加し、
      それを更にrootという一番基底となるグループに追加していく
    */
    val circles = new Group()
    for (i <- 1 to 30) {
      val circle = new Circle(150, Color.web("white", 0.05))
      circle.setStrokeType(StrokeType.OUTSIDE)
      circle.setStroke(Color.web("white", 0.16))
      circle.setStrokeWidth(4)
      circles.getChildren().add(circle)
    }
    root.getChildren().add(circles)



    /* 
      実装に使われている技術は今の知識では理解できないので、 簡単に説明(コピペ)

      ここではTimelineというクラスでアニメーションを定義し、
      各円全てに対してアニメーション開始0秒時点でランダムな位置にポジショニングしたあと、
      アニメーションが開始した後40秒経過した時点で、別なランダムな位置に移動完了するという設定をしている
    */
    val timeline = new Timeline()
    circles.getChildren().forEach(new Consumer[Node] {

      override def accept(circle: Node): Unit = {
        timeline.getKeyFrames().addAll(
          new KeyFrame(Duration.ZERO,
            new KeyValue(circle.translateXProperty(), random() * 800: Number),
            new KeyValue(circle.translateYProperty(), random() * 600: Number)
          ),
          new KeyFrame(new Duration(40000),
            new KeyValue(circle.translateXProperty(), random() * 800: Number),
            new KeyValue(circle.translateYProperty(), random() * 600: Number)
          )
        )
      }

    })
    timeline.play()
    primaryStage.show()
  }

}