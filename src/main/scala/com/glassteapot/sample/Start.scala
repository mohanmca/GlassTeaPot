package com.glassteapot.sample;
import java.io.FileInputStream
import java.io.InputStream
import java.util.Properties
import javax.mail.Session
import javax.mail.internet.MimeMessage
import scala.collection.JavaConversions._
import javax.mail.Header
import com.fasterxml.jackson.databind.ObjectMapper
import java.io.StringWriter
import java.io.PrintWriter
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.DeserializationConfig
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility
import com.fasterxml.jackson.annotation.PropertyAccessor
import com.fasterxml.jackson.databind.SerializationFeature
import com.sun.org.apache.xalan.internal.xsltc.compiler.Include
import com.fasterxml.jackson.annotation.JsonInclude.Include
import java.lang.reflect.Field
import java.util.Enumeration
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.GsonBuilder
import com.google.gson.stream.JsonReader
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonWriter
import com.thoughtworks.xstream.XStream
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver

object Start {

  def main(args: Array[String]) {
    val mp = MailParser(args(0));
    val messge = mp.getMessaage()
    mp.toJson(mp.getMessaage())
  }

}

case class MailParser(file: String) {
  def getMessaage(): MimeMessage = {
    val props: Properties = System.getProperties();
    props.put("mail.host", "smtp.dummydomain.com");
    props.put("mail.transport.protocol", "smtp");

    val mailSession: Session = Session.getDefaultInstance(props, null);
    val source: InputStream = new FileInputStream(file);
    val message: MimeMessage = new MimeMessage(mailSession, source);

    val headers: scala.collection.Iterator[Any] = message.getAllHeaders
    //val reqHeaders: scala.collection.Iterator[Header] = headers.filter { _.asInstanceOf[Header].getName.startsWith("DKIM-Signature") }.asInstanceOf[Iterator[Header]]
    //headers.next()
    message
  }

  def toJson(msg: MimeMessage): Unit = {

    var map = Map[AnyRef, AnyRef]()
    val headers: Enumeration[Header] = msg.getAllHeaders.asInstanceOf[Enumeration[Header]]
    val mailHeaders = headers map (header => (header.getName -> header.getValue))
    map = map ++ mailHeaders
    map = map + ("Recipients" -> msg.getAllRecipients)
    map = map + ("From" -> msg.getFrom)
    map = map + ("Sender" -> msg.getSender)
    map = map + ("Send Date" -> msg.getSentDate)
    map = map + ("Received Date" -> msg.getReceivedDate)
    map = map + ("Subject" -> msg.getSubject)
    map = map + ("MessageId" -> msg.getMessageID)
    map = map + ("ReplyTo" -> msg.getReplyTo)
    map = map + ("Message Number" -> new Integer(msg.getMessageNumber))
    map = map + ("Content" -> "Content")
    toJson2(map)
  }

  def toJson3(msg: MimeMessage): Unit = {

    //val gson = new GsonBuilder().registerTypeAdapter(msg.getClass, new MyTypeAdapter[MimeMessage]()).create();
    val gson = new Gson
    System.out.println(gson.toJson(msg, msg.getClass));

    //    val headers: Enumeration[Header] = msg.getAllHeaders.asInstanceOf[Enumeration[Header]]
    //    val sHeaders = for {
    //      it <- enumerationAsScalaIterator[Header](headers)
    //    } yield Headers(it.getName, it.getValue)
    //      val receipients = msg.getAllRecipients
    //
    //    val (mapper, pw, sw) = toJsonString()
    //    mapper.writeValue(pw, msg.getAllHeaders);
    //    System.out.println(sw.toString());
  }

  def toJson2(msg: AnyRef): Unit = {
    val (mapper, pw, sw) = toJsonString()
    mapper.writeValue(pw, msg);
    System.out.println(sw.toString());
  }

  def toJsonString() = {
    val mapper = new ObjectMapper() with ScalaObjectMapper
    mapper.registerModule(DefaultScalaModule)
    mapper.enable(SerializationFeature.INDENT_OUTPUT)
    mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
    //    mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    //    //mapper.setSerialization
    //    mapper.setSerializationInclusion(Include.NON_NULL);
    //    mapper.setSerializationInclusion(Include.NON_EMPTY);
    //    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    // display to console
    val sw = new StringWriter()
    val pw = new PrintWriter(sw)
    (mapper, pw, sw)
  }

}

case class Headers(name: String, value: String) {

}

class MyTypeAdapter[T >: Null] extends TypeAdapter[T] {
  def read(reader: JsonReader): T = {
    return null;
  }

  def write(writer: JsonWriter, obj: T): Unit = {
    if (obj == null) {
      writer.nullValue();
      return ;
    }
    writer.value(obj.toString());
  }
}