package com.glassteapot.sample;
import com.glassteapot.mailparser._
import com.glassteapot.json.JsonUtil
import org.elasticsearch.client.transport.TransportClient
import org.elasticsearch.common.transport.InetSocketTransportAddress
import org.elasticsearch.common.settings.ImmutableSettings
import org.elasticsearch.common.xcontent.XContentType
object Start {

  def main(args: Array[String]) {
    val mp = MailParser(args(0));
    val messge = mp.getMessaage
    val settings = ImmutableSettings.settingsBuilder().put("cluster.name", "localstream-dev").build();
    val data = mp.toMap(mp.getMessaage())
    System.out.println(JsonUtil.convertMaptoJson(data))

    val esClient = new TransportClient(settings)
    esClient.addTransportAddress(new InetSocketTransportAddress("localhost", 9300))
    val response = esClient.prepareIndex("mail_forum", "technology").setSource(data, XContentType.JSON).execute().actionGet()

  }

}
