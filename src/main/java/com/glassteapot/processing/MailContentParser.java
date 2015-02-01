//package com.glassteapot.processing;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.StringWriter;
//
//import javax.mail.BodyPart;
//import javax.mail.Multipart;
//import javax.mail.Part;
//
//import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
//import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.MimeUtility;
//
//public class MailContentParser {
//
//	public static String fetchEmailcontent(Part message, String messageid) throws IOException, MessagingException {
//
//        StringWriter sw = new StringWriter(1024);
//
//        if (message != null && message.getContent() != null) {
//            if (message.getContent() instanceof Multipart) {
//                Multipart parts = (Multipart) message.getContent();
//                BodyPart p;
//                boolean alternative = parts.getContentType().trim().toLowerCase().startsWith("multipart/alternative") ? true : false;
//
//                InputStreamReader isr;
//                int retrieved;
//                char[] buffer = new char[512];
//                for (int i = 0; i < parts.getCount(); i++) {
//                    p = parts.getBodyPart(i);
//
//                    if (p.getContentType().toLowerCase().startsWith("multipart")) {
//                        //sw.write(fetchEmailcontent(p, messageid));
//                        break;
//                    } else if ((Part.INLINE.equalsIgnoreCase(p.getDisposition()) || p.getDisposition() == null) && p.getContentType().toLowerCase().startsWith("text") && p.getFileName() == null) {
//
//                        if (InputStream.class.isInstance(p.getContent())) {
//                            InputStream ip = p.getInputStream();
//
//                            StringWriter subwriter = new StringWriter(ip.available());
//                            isr = new InputStreamReader(ip);
//                            while (isr.ready()) {
//                                retrieved = isr.read(buffer, 0, 512);
//                                subwriter.write(buffer, 0, retrieved);
//                            }
//                            sw.write(subwriter.toString());
//                        } else {
//                            Object content = p.getContent();
//                            if (java.io.ByteArrayInputStream.class.isInstance(content)) {
//                                int bcount = ((java.io.ByteArrayInputStream) content).available();
//                                byte[] c = new byte[bcount];
//                                ((java.io.ByteArrayInputStream) content).read(c, 0, bcount);
//                                sw.write(new String(c));
//                            } else {
//                                sw.write(content.toString());
//                            }
//                        }
//                        if (alternative && !"".equals(sw.toString().trim())) {
//                            break;
//                        }
//                        sw.write("\r\n");
//                    } else if (p.getDisposition() != null && (p.getDisposition().equalsIgnoreCase(Part.ATTACHMENT) || p.getDisposition().equalsIgnoreCase(Part.INLINE))) {
//                       // saveFile(MimeUtility.decodeText(p.getFileName()), p.getInputStream(), messageid);
//                    }
//                }
//            } else if (message.getContentType().toLowerCase().startsWith("text")) {
//                sw.write(message.getContent().toString());
//            }
//        }
//        return sw.toString();
//    }
//}
