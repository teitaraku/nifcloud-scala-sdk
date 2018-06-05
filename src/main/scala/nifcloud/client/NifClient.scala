package nifcloud.client

object NifClient {

  def apply(): NifClient[ClientOption.Empty] = new NifClient(
    NifClientConfig(
      service = "",
      region = "",
      credential = Credential("", "")
    )
  )

  sealed trait ClientOption

  object ClientOption {

    type CompleteClient = Empty with HasService with HasRegion with HasCredential

    sealed trait Empty extends ClientOption

    sealed trait HasService extends ClientOption

    sealed trait HasRegion extends ClientOption

    sealed trait HasCredential extends ClientOption
  }

}

class NifClient[A <: NifClient.ClientOption] private(config: NifClientConfig) {

  import NifClient.ClientOption._
  import scalaj.http.HttpConstants._
  import scalaj.http._

  def service(p: String): NifClient[A with HasService] =
    new NifClient(config.copy(service = p))

  def region(p: String): NifClient[A with HasRegion] =
    new NifClient(config.copy(region = p))

  def domain(p: String): NifClient[A] =
    new NifClient(config.copy(domain = p))

  def basePath(p: String): NifClient[A] =
    new NifClient(config.copy(basePath = p))

  def apiVersion(p: String): NifClient[A] =
    new NifClient(config.copy(apiVersion = Some(p)))

  def signatureMethod(p: String): NifClient[A] =
    new NifClient(config.copy(signatureMethod = p))

  def signatureVersion(p: Int): NifClient[A] =
    new NifClient(config.copy(signatureVersion = p))

  def credential(p: Credential): NifClient[A with HasCredential] =
    new NifClient(config.copy(credential = p))

  def httpMethod(p: HttpMethod): NifClient[A] =
    new NifClient(config.copy(httpMethod = p))

  def params(p: Map[String, String]): NifClient[A] =
    new NifClient(config.copy(params = config.params ++ p))

  def param(k: String, v: String): NifClient[A] =
    new NifClient(config.copy(params = config.params + (k -> v)))

  def proxy(p: Proxy): NifClient[A] =
    new NifClient(config.copy(proxy = Some(p)))

  def request(implicit evidence: A =:= CompleteClient): HttpResponse[String] = {
    import java.util.Date

    import nifcloud.auth.Signer

    val baseParams = config.params ++ Map(
      "AccessKeyId" -> config.credential.accessKeyId,
      "SignatureVersion" -> config.signatureVersion.toString,
      "TimeStamp" -> "%tY-%<tm-%<tdT%<tH:%<tM:%<tS".format(new Date),
      "SignatureMethod" -> config.signatureMethod
    )
    val params = baseParams + ("Signature" ->
      Signer.sign(stringToSign(baseParams), config.credential.secretAccessKey, config.signatureMethod))
    val client = Http(config.url())
    val clientHasMethod = config.httpMethod match {
      case GET => client.method(GET.name).params(params)
      case POST => client.method(POST.name).postForm(params.toSeq)
    }
    val clientWithProxy = config.proxy match {
      case Some(p) => clientHasMethod.proxy(p.host, p.port)
      case None => clientHasMethod
    }
    clientWithProxy.asString
  }

  private def stringToSign(params: Map[String, String]): String = {
    val requestString = params.toSeq.sortBy(_._1)
      .map(t => urlEncode(t._1, utf8) + "=" + urlEncode(t._2, utf8))
      .reduce((q1, q2) => q1 + "&" + q2)

    Seq(config.httpMethod.name, config.endpoint(), config.path(), requestString)
      .reduce((a, b) => a + "\n" + b)
  }
}
