import android.os.AsyncTask
import java.net.HttpURLConnection
import java.net.URL

class WeatherConnect(private val listener: WeatherCallback) : AsyncTask<String, Void, String>() {

    override fun doInBackground(vararg params: String?): String? {

        val urlString = params[0]
        return try {
            val url = URL(urlString)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connect()
            connection.inputStream.bufferedReader().use { it.readText() }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        if (result != null) {
            listener.onWeatherResult(result)
        } else {
            listener.onWeatherError("Error fetching data")
        }
    }

    interface WeatherCallback {
        fun onWeatherResult(result: String)
        fun onWeatherError(errorMessage: String)
    }
}
