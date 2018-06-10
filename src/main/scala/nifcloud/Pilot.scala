package nifcloud

import java.io.StringReader
import javax.xml.bind.JAXBContext

import nifcloud.model.DescribeRegionsResponseType

object Pilot {

  def main(args: Array[String]): Unit = {
    val sampleXml = "<DescribeRegionsResponse><requestId>ac501097-4c8d-475b-b06b-a90048ec181c</requestId><regionInfo><item><regionName>east-1</regionName><regionEndpoint>east-1.cloud.nifty.com</regionEndpoint><messageSet><item><message/></item></messageSet><isDefault>true</isDefault></item></regionInfo></DescribeRegionsResponse>"
    val context = JAXBContext.newInstance(classOf[DescribeRegionsResponseType])
    val mapped = context.createUnmarshaller().unmarshal(new StringReader(sampleXml))
    print(mapped)
  }

}
