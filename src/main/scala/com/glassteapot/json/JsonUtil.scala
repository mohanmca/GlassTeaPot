package com.glassteapot.json
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

object JsonUtil {
  def convertMaptoJson(msg: Map[AnyRef, AnyRef]): String = {
    val (mapper, pw, sw) = toJsonString()
    mapper.writeValue(pw, msg);
    sw.toString()
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