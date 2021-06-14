def call(Map config = [:]) {
  def resourceContent = mainScript.libraryResource('tombo.test')
  println(resourceContent)
}