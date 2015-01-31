package com.glassteapot.sample;
import java.io.FileInputStream
import java.io.InputStream
import java.util.Properties
import javax.mail.Session
import javax.mail.internet.MimeMessage
import scala.collection.JavaConversions._
import javax.mail.Header

object Start {

  def main(args: Array[String]) {
    val props: Properties = System.getProperties();
    props.put("mail.host", "smtp.dummydomain.com");
    props.put("mail.transport.protocol", "smtp");

    val mailSession: Session = Session.getDefaultInstance(props, null);
    val source: InputStream = new FileInputStream(args(0));
    val message: MimeMessage = new MimeMessage(mailSession, source);

    val headers: scala.collection.Iterator[Any] = message.getAllHeaders
    val reqHeaders: scala.collection.Iterator[Header] = headers.filter { _.asInstanceOf[Header].getName.startsWith("Received") }.asInstanceOf[Iterator[Header]]
    val reqHeaderPair = reqHeaders.map { header => header.getName + " $,$ " + header.getValue } 

    println(reqHeaderPair.mkString("\n"))

  }

}
