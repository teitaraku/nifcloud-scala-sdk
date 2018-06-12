package nifcloud

import java.io.StringReader

import javax.xml.bind.JAXBContext
import nifcloud.model._

import java.util.{ArrayList => JArrayList}
import java.util.{Arrays => JArrays}

object Pilot {

  def main(args: Array[String]): Unit = {
    //marshal()
    unmarshal()
  }

  def unmarshal(): Unit = {
    val sampleXml = "<DescribeRegionsResponse><requestId>ac501097-4c8d-475b-b06b-a90048ec181c</requestId><regionInfo><item><regionName>east-1</regionName><regionEndpoint>east-1.cloud.nifty.com</regionEndpoint><messageSet><item><message/></item></messageSet><isDefault>true</isDefault></item></regionInfo></DescribeRegionsResponse>"
    val context = JAXBContext.newInstance(classOf[DescribeRegionsResponseType])
    val mapped = context.createUnmarshaller().unmarshal(new StringReader(sampleXml))
    print(mapped)
  }

  def marshal(): Unit = {
    val c = DescribeRegionsResponseType("ac501097-4c8d-475b-b06b-a90048ec181c",
      RegionSetType(
        new JArrayList[RegionItemType](JArrays.asList(
        RegionItemType(
          "region-name",
          "region-endpoint",
          RegionMessageSetType(new JArrayList[RegionMessageType](JArrays.asList(RegionMessageType("fuga"),RegionMessageType("piyo")))),
          true)))))
    val context = JAXBContext.newInstance(classOf[DescribeRegionsResponseType])
    context.createMarshaller().marshal(c, System.out)
    //print(mapped)
  }

}
