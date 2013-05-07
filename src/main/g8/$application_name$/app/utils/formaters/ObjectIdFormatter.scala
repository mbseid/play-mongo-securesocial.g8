package utils.formaters

import play.api.data.format.Formatter
import org.bson.types.ObjectId
import play.api.data.{FormError, Mapping}
import play.api.data.Forms._
import play.api.data.format._
import play.api.data.format.Formats._

/**
 * Created with IntelliJ IDEA.
 * User: mseid
 * Date: 5/2/13
 * Time: 12:38 PM
 * To change this template use File | Settings | File Templates.
 */
object ObjectIdFormatter extends AbstractFormatter{
  implicit val objectIdFormatter: Formatter[ObjectId] = new Formatter[ObjectId] {
    def bind(key: String, data: Map[String, String]) = {
      parsing(new ObjectId(_), "error.objectId", Nil)(key, data)
    }
    def unbind(key: String, value: ObjectId) = Map(key -> value.toString)
  }
  implicit val objectId = of[ObjectId]



}