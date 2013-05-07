package utils.formaters

import play.api.data.format.Formatter
import play.api.data.FormError

/**
 * Created with IntelliJ IDEA.
 * User: mseid
 * Date: 5/2/13
 * Time: 1:46 PM
 * To change this template use File | Settings | File Templates.
 */
trait AbstractFormatter {
  implicit def stringFormat: Formatter[String] = new Formatter[String] {
    def bind(key: String, data: Map[String, String]) = data.get(key).toRight(Seq(FormError(key, "error.required", Nil)))
    def unbind(key: String, value: String) = Map(key -> value)
  }
  def parsing[T](parse: String => T, errMsg: String, errArgs: Seq[Any])(key: String, data: Map[String, String]): Either[Seq[FormError], T] = {
    stringFormat.bind(key, data).right.flatMap { s =>
      scala.util.control.Exception.allCatch[T]
        .either(parse(s))
        .left.map(e => Seq(FormError(key, errMsg, errArgs)))
    }
  }
}
