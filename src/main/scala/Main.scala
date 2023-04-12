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
      背景が黒の幅800px、高さ600pxのエリアを作成。

      Groupクラス...シーングラフ内の複数のノードをまとめたい場合に使用する。
    */
    val root = new Group()
    val scene = new Scene(root, 800, 600, Color.BLACK)
    primaryStage.setScene(scene)


    /* 
      表示&アニメーションする図形(今回は、ゆっくり動く円)を管理するために、circlesというGroupクラスのオブジェクトを作る。
    */
    val circles = new Group()


    /* 
      /*
        表示する図形の設定

        作成したGroupクラスのオブジェクトに、
        図形として円を設定し、その円の縁と中身の色を白とし、さらにそれぞれの透明度を設定する。
        作成される円の数だけ、ループする。
      */
      //30個の円が表示される(アプリ側で設定されるので、アプリ実行者は表示数を変更できない)
      for (i <- 1 to 30) {
        val circle = new Circle(150, Color.web("white", 0.05))
        circle.setStrokeType(StrokeType.OUTSIDE)
        circle.setStroke(Color.web("white", 0.16))
        circle.setStrokeWidth(4)
        circles.getChildren().add(circle)
      } 
    */


    /* 
      表示する円の数を、アプリ実行者がjarファイル実行時に引数で設定できるようにする。
      以下の実装だと、jarファイルをコマンドライン実行時に、--num=100 のような形式で引数を渡せる。
      (引数を渡さなければデフォルト数の30個が作成される)
    */
    val circleNum = getParameters.getNamed.getOrDefault("num","30").toInt

    /* 
      表示する図形の設定

      作成したGroupクラスのオブジェクトに、
      図形として円を設定し、その円の縁と中身の色を白とし、さらにそれぞれの透明度を設定する。
      作成される円の数だけ、ループする。
    */
    for (i <- 1 to circleNum) {
      val circle = new Circle(150, Color.web("white", 0.05))  
      circle.setStrokeType(StrokeType.OUTSIDE)
      circle.setStroke(Color.web("white", 0.16))
      circle.setStrokeWidth(4)
      circles.getChildren().add(circle)
    }

    //作成した円を、rootという一番基底となるグループに追加していく
    root.getChildren().add(circles)


    /* 
      表示された図形のアニメーションを設定
      実装に使われている技術は今の知識では理解できないので、 簡単に説明(コピペ)

      ここではTimelineというクラスでアニメーションを定義し、
      各円全てに対してアニメーション開始0秒時点でランダムな位置にポジショニングしたあと、
      アニメーションが開始した後40秒経過した時点で、別なランダムな位置に移動完了するという設定をしている。
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
