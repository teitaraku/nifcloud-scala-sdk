package nifcloud.client

sealed abstract class HttpMethod(val name: String)

case object GET extends HttpMethod("GET")

case object POST extends HttpMethod("POST")

case class Credential(accessKeyId: String, secretAccessKey: String)

case class Proxy(host: String, port: Int)

case class NifClientConfig
(
  service: String,
  region: String,
  domain: String = "api.cloud.nifty.com",
  basePath: String = "/api/",
  apiVersion: Option[String] = None,
  signatureMethod: String = "HmacSHA256",
  signatureVersion: Int = 2,
  credential: Credential,
  httpMethod: HttpMethod = GET,
  params: Map[String, String] = Map(),
  proxy: Option[Proxy] = None,
) {
  def url(): String =
    "https://" + endpoint + path

  def endpoint(): String =
    Seq(service, region, domain)
      .reduce((a, b) => a + "." + b)

  def path(): String = apiVersion match {
    case Some(x) => basePath + x + "/"
    case None => basePath
  }
}
