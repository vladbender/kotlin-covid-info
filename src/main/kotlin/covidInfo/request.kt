package covidInfo

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

fun request(url: String): String {
    val connection = URL(url).openConnection() as HttpsURLConnection

    connection.requestMethod = "GET"
    // without user-agent response code is 403
    connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.149 Safari/537.36")

    val code = connection.responseCode

    val responseStream = if (code in 200..299) connection.inputStream else connection.errorStream

    val input = BufferedReader(InputStreamReader(responseStream))

    val stringBuilder = StringBuilder()

    while (true) {
        stringBuilder.append(input.readLine() ?: break)
    }

    connection.disconnect()

    return stringBuilder.toString()
}
