package com.gyula.kepek

import android.os.AsyncTask
import android.util.Log
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL

enum class LetoltesAllapot {
    OK, IDLE, NOT_INITIALISED, FAILED_OR_EMPTY, PERMISSIONS_ERROR, ERROR
}
class JsonLetoltes(private val listener: OnDownloadComplete) : AsyncTask<String, Void, String>() {
//    private val TAG = "Jsonletolto"
    private var letoltesAllapot = LetoltesAllapot.IDLE

    interface OnDownloadComplete {
        fun onDownloadComplete(data: String, status: LetoltesAllapot)
    }

    override fun onPostExecute(result: String) {
        listener?.onDownloadComplete(result, letoltesAllapot)
    }

    override fun doInBackground(vararg params: String?): String {
        if (params[0] == null) {
            letoltesAllapot = LetoltesAllapot.NOT_INITIALISED
            return "Nincs URL!"
        }

        try {
            letoltesAllapot = LetoltesAllapot.OK
            return URL(params[0]).readText()
        } catch (e: Exception) {
            val errorMessage = when (e) {
                is MalformedURLException -> {
                    letoltesAllapot = LetoltesAllapot.NOT_INITIALISED
                    "doInBackground: Invalid URL ${e.message}"
                }
                is IOException -> {
                    letoltesAllapot = LetoltesAllapot.FAILED_OR_EMPTY
                    "doInBackground: IO Exception reading data: ${e.message}"
                }
                is SecurityException -> {
                    letoltesAllapot = LetoltesAllapot.PERMISSIONS_ERROR
                    "doInBackground: Security exception: Needs permission? ${e.message}"
                } else -> {
                    letoltesAllapot = LetoltesAllapot.ERROR
                    "Unknown error: ${e.message}"
                }
            }

//            Log.e(TAG, errorMessage)
            return errorMessage
        }
    }
}