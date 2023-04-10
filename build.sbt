/* build.sbt...sbtがビルドを行う際の設定ファイル,  設定はScalaを拡張した文法で書ける */
scalaVersion := "2.12.10"

//Scalaのコンパイル時のオプションを、配列で渡す
/* 
  -deprecation...今後廃止の予定のAPIを利用した時は警告が出力
  -feature...実験的なAPI利用時は警告が出力
  -unchecked...型を利用したパターンマッチという機能が正しく動かない時に警告が出力
  -Xlint...望ましい書き方をされていない場合に警告が出力がされる
*/
scalacOptions ++= Seq("-deprecation", "-feature", "-unchecked", "-Xlint")

//使用しているOSを検知して、その種類を保存？
val osName: SettingKey[String] = SettingKey[String]("osName")
osName := (System.getProperty("os.name") match {
    case name if name.startsWith("Linux") => "linux"
    case name if name.startsWith("Mac") => "mac"
    case name if name.startsWith("Windows") => "win"
    case _ => throw new Exception("Unknown platform!")
})

//ライブラリの依存関係を記述
//コンパイル時に導入される
//npmのpackage.jsonと似たような感じ？
libraryDependencies += "org.openjfx" % "javafx-base" % "11-ea+25" classifier osName.value
libraryDependencies += "org.openjfx" % "javafx-controls" % "11-ea+25" classifier osName.value
libraryDependencies += "org.openjfx" % "javafx-fxml" % "11-ea+25" classifier osName.value
libraryDependencies += "org.openjfx" % "javafx-graphics" % "11-ea+25" classifier osName.value
libraryDependencies += "org.openjfx" % "javafx-web" % "11-ea+25" classifier osName.value

//作成したアプリケーションを、単一のファイルとして配布できるような形でビルド(=jarファイルとして、単一ファイルにまとめる形でビルド)
assembly / assemblyMergeStrategy := {
    case PathList("module-info.class") => MergeStrategy.first
    case x => (assembly / assemblyMergeStrategy).value(x)
}
