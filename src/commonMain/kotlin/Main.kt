sealed class Sealed

class A : Sealed() {
  fun a() {}
}

fun Sealed.foo() {
  if (this !is A) {
    if (false) {
      check(this is A) { "Always throws" }
    } else {
      return
    }
  }
  this.a()
}
