package nifcloud.model

import scala.annotation.meta.field

import javax.xml.bind.annotation._
import javax.xml.bind.annotation.adapters._

import java.util.{List => JList}
import java.util.{ArrayList => JArrayList}

package object model {
  type xmlElement = XmlElement @field
  type xmlTypeAdapter = XmlJavaTypeAdapter @field
}

@XmlRootElement(name = "DescribeRegionsResponse")
@XmlAccessorType(XmlAccessType.FIELD)
case class DescribeRegionsResponseType
(
  requestId: String,
  regionInfo: RegionSetType
) {
  private def this() = this(null, null)
}

@XmlAccessorType(XmlAccessType.FIELD)
case class RegionSetType
(
  item: JList[RegionItemType]
) {
  private def this() = this(new JArrayList[RegionItemType])
}

@XmlAccessorType(XmlAccessType.FIELD)
case class RegionItemType
(
  regionName: String,
  regionEndpoint: String,
  messageSet: RegionMessageSetType,
  isDefault: Boolean
) {
  private def this() = this(null, null, null, false)
}

@XmlAccessorType(XmlAccessType.FIELD)
case class RegionMessageSetType
(
  item: JList[RegionMessageType]
) {
  private def this() = this(new JArrayList[RegionMessageType])
}

@XmlAccessorType(XmlAccessType.FIELD)
case class RegionMessageType
(
  message: String
) {
  private def this() = this(null)
}
