package nifcloud.auth

import scalaj.http.HttpConstants.{base64, utf8}

object Signer {
  def sign(stringToSign: String, secretKey: String, signatureMethod: String): String = {
    import javax.crypto.Mac
    import javax.crypto.spec.SecretKeySpec
    val mac = Mac.getInstance(signatureMethod)
    val keySpec = new SecretKeySpec(secretKey.getBytes(utf8), signatureMethod)
    mac.init(keySpec)
    base64(mac.doFinal(stringToSign.getBytes))
  }
}
