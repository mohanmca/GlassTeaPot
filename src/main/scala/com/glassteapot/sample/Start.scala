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
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.impl.Future
import scala.concurrent.impl.Future
import scala.concurrent.{ Promise, ExecutionContext, Future }
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import org.elasticsearch.action.index.IndexRequestBuilder
import java.util.concurrent.Callable

object Start {

  val threadPoolExector = Executors.newFixedThreadPool(5);

  def main(args: Array[String]) {
    val directoryName = args(0);

    val fileNames_ = FileUtils.listFiles(new File(directoryName), new RegexFileFilter("^(.*?)"), DirectoryFileFilter.DIRECTORY);
    val fileNames = JavaConversions.asScalaIterator(fileNames_.iterator())
    val index = getIndexBuilder()
    for (fileName <- fileNames) {
      indexFile(index, fileName.getPath)
    }
  }

  def indexFile(index: IndexRequestBuilder, fileName: String) {
    val task = new Callable[Unit]() {
      def call() {
        val mp = MailParser(fileName);
        val messge = mp.getMessaage
        val data = mp.toMap(mp.getMessaage())
        System.out.println(JsonUtil.convertMaptoJson(data))
        val response = index.setSource(data, XContentType.JSON).execute().actionGet()
      }
    }
    threadPoolExector.submit(task);
  }

  //Replace below code to make use of IndexBuilder
  def getIndexBuilder(): IndexRequestBuilder = {
    val settings = ImmutableSettings.settingsBuilder().put("cluster.name", "localstream-dev").build();
    val esClient = new TransportClient(settings)
    esClient.addTransportAddress(new InetSocketTransportAddress("localhost", 9300))
    val indexRequestBuilder = esClient.prepareIndex("mail_forum", "technology")
    indexRequestBuilder
  }

}
