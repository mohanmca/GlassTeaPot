package com.glassteapot.sample;
import com.glassteapot.mailparser._
import com.glassteapot.json.JsonUtil
import org.elasticsearch.client.transport.TransportClient
import org.elasticsearch.common.transport.InetSocketTransportAddress
import org.elasticsearch.common.settings.ImmutableSettings
import org.elasticsearch.common.xcontent.XContentType
import org.apache.commons.io.filefilter.DirectoryFileFilter
import org.apache.commons.io.filefilter.RegexFileFilter
import org.apache.commons.io.FileUtils
import java.io.File
import scala.collection.JavaConversions

object Start {

  def main(args: Array[String]) {
    val directoryName = args(0);

    val fileNames_ = FileUtils.listFiles(new File(directoryName), new RegexFileFilter("^(.*?)"), DirectoryFileFilter.DIRECTORY);
    val fileNames = JavaConversions.asScalaIterator(fileNames_.iterator())

    for (fileName <- fileNames)
      indexFile(fileName.getPath)
  }

  def indexFile(fileName: String) {
    val mp = MailParser(fileName);
    val messge = mp.getMessaage
    val settings = ImmutableSettings.settingsBuilder().put("cluster.name", "localstream-dev").build();
    val data = mp.toMap(mp.getMessaage())
    System.out.println(JsonUtil.convertMaptoJson(data))

    val esClient = new TransportClient(settings)
    esClient.addTransportAddress(new InetSocketTransportAddress("localhost", 9300))
    val response = esClient.prepareIndex("mail_forum", "technology").setSource(data, XContentType.JSON).execute().actionGet()
  }

}
