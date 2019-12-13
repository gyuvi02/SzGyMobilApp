package com.gyula.kepek

import android.os.AsyncTask
import android.util.Log
import org.json.JSONArray
import org.json.JSONException


class KepParser(private val listener: OnDataAvailable) : AsyncTask<String, Void, ArrayList<Kep>>() {

//    private val TAG = "FotoAdatLetoltes"


    interface OnDataAvailable {
        fun onDataAvailable(data: List<Kep>)
        fun onError(exception: Exception)
    }

    override fun doInBackground(vararg params: String?): ArrayList<Kep> {
        val kepLista = ArrayList<Kep>()


//Kísérlet a Moshival
//        @JsonClass(generateAdapter = true)
//        data class Kep2 (
//                @Json(name = "albumId") val albumId: String,
//                val id :String,
//                val title : String,
//                val  url : String,
//                val thumbnailUrl : String
//        )
//        val moshi: Moshi = Moshi.Builder().build()
//        val adapter: JsonAdapter<Kep2> = moshi.adapter(Kep2::class.java)
//        val movie = adapter.fromJson(kepek)


        try {
            val kepArray = JSONArray(params[0])

            for (i in 0 until kepArray.length()) {
                val jsonPhoto = kepArray.getJSONObject(i)
                val albumId = jsonPhoto.getString("albumId")
                val id = jsonPhoto.getString("id")
                val title = jsonPhoto.getString("title")
                val url = jsonPhoto.getString("url")
                val thumbnailUrl = jsonPhoto.getString("thumbnailUrl")


                val kepObject = Kep(albumId, id, title, url, thumbnailUrl)

                kepLista.add(kepObject)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
            cancel(true)
            listener.onError(e)
        }

        return kepLista
    }

    override fun onPostExecute(result: ArrayList<Kep>) {

        super.onPostExecute(result)
        listener.onDataAvailable(result)
    }
}