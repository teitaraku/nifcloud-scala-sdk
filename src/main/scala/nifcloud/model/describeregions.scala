package nifcloud.model

import scala.annotation.meta.field

import javax.xml.bind.annotation._
import javax.xml.bind.annotation.adapters._

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

case class RegionSetType
(
  item: RegionItemType
) {
  private def this() = this(null)
}

case class RegionItemType
(
  regionName: String,
  regionEndpoint: String,
  messageSet: RegionMessageSetType,
  isDefault: Boolean
) {
  private def this() = this(null, null, null, false)
}

case class RegionMessageSetType
(
  item: RegionMessageType
) {
  private def this() = this(null)
}

case class RegionMessageType
(
  message: String
) {
  private def this() = this(null)
}
