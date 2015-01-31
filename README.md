GlassTeaPot
===========

Parser and Classifier of Assorted Content


Build notes
========================
reload
eclipse with-source=true
F5 - to reload project

========================
Download mail code from

https://java.net/projects/javamail/downloads/download/source/javamail-1.5.2-src.zip

========================



Exception in thread "main" com.fasterxml.jackson.databind.JsonMappingException: 
	(was java.lang.NullPointerException) 
	(through reference chain: javax.mail.internet.MimeMessage["session"]
		->javax.mail.Session["transport"]
		->com.sun.mail.smtp.SMTPTransport["urlname"]->javax.mail.URLName["url"])

Exception in thread "main" com.fasterxml.jackson.databind.JsonMappingException: (was java.lang.NullPointerException) (through reference chain: javax.mail.internet.MimeMessage["session"]->javax.mail.Session["transport"]->com.sun.mail.smtp.SMTPTransport["urlname"]->javax.mail.URLName["url"])
	at com.fasterxml.jackson.databind.JsonMappingException.wrapWithPath(JsonMappingException.java:210)
	at com.fasterxml.jackson.databind.JsonMappingException.wrapWithPath(JsonMappingException.java:177)
	at com.fasterxml.jackson.databind.ser.std.StdSerializer.wrapAndThrow(StdSerializer.java:190)
	at com.fasterxml.jackson.databind.ser.std.BeanSerializerBase.serializeFields(BeanSerializerBase.java:671)
	at com.fasterxml.jackson.databind.ser.BeanSerializer.serialize(BeanSerializer.java:156)
	at com.fasterxml.jackson.databind.ser.BeanPropertyWriter.serializeAsField(BeanPropertyWriter.java:575)
	at com.fasterxml.jackson.databind.ser.std.BeanSerializerBase.serializeFields(BeanSerializerBase.java:663)
	at com.fasterxml.jackson.databind.ser.BeanSerializer.serialize(BeanSerializer.java:156)
	at com.fasterxml.jackson.databind.ser.BeanPropertyWriter.serializeAsField(BeanPropertyWriter.java:575)
	at com.fasterxml.jackson.databind.ser.std.BeanSerializerBase.serializeFields(BeanSerializerBase.java:663)
	at com.fasterxml.jackson.databind.ser.BeanSerializer.serialize(BeanSerializer.java:156)
	at com.fasterxml.jackson.databind.ser.BeanPropertyWriter.serializeAsField(BeanPropertyWriter.java:575)
	at com.fasterxml.jackson.databind.ser.std.BeanSerializerBase.serializeFields(BeanSerializerBase.java:663)
	at com.fasterxml.jackson.databind.ser.BeanSerializer.serialize(BeanSerializer.java:156)
	at com.fasterxml.jackson.databind.ser.DefaultSerializerProvider.serializeValue(DefaultSerializerProvider.java:129)
	at com.fasterxml.jackson.databind.ObjectMapper._configAndWriteValue(ObjectMapper.java:3383)
	at com.fasterxml.jackson.databind.ObjectMapper.writeValue(ObjectMapper.java:2759)
	at com.glassteapot.sample.MailParser.toJson(Start.scala:63)
	at com.glassteapot.sample.Start$.main(Start.scala:29)
	at com.glassteapot.sample.Start.main(Start.scala)
Caused by: java.lang.NullPointerException
	at java.net.Parts.<init>(URL.java:1290)
	at java.net.URL.<init>(URL.java:399)
	at java.net.URL.<init>(URL.java:303)
	at javax.mail.URLName.getURL(URLName.java:412)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:483)
	at com.fasterxml.jackson.databind.ser.BeanPropertyWriter.serializeAsField(BeanPropertyWriter.java:536)
	at com.fasterxml.jackson.databind.ser.std.BeanSerializerBase.serializeFields(BeanSerializerBase.java:663)
	... 16 more
