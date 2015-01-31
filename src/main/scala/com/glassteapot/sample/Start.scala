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

    //val headers: scala.collection.Iterator[Any] = message.getAllHeaders
    //val reqHeaders: scala.collection.Iterator[Header] = headers.filter { _.asInstanceOf[Header].getName.startsWith("DKIM-Signature") }.asInstanceOf[Iterator[Header]]
    //headers.next()
    message
  }
  def toJson(msg: MimeMessage): Unit = {
        
    val objectWriter = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    // display to console
    val sw = new StringWriter()
    val pw = new PrintWriter(sw)

    objectWriter.writeValue(pw, msg);
    System.out.println(sw.toString());
  }
}
