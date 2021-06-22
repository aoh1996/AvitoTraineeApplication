package com.learnproject.aoh1996.avitotraineeapplication.data

import com.learnproject.aoh1996.AvitoTraineeApplication
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.lang.Exception
import kotlin.jvm.Throws

object JsonParser {

    @Throws(IOException::class, Exception::class)
    fun getRawData(fileName: String): String {
        val asset = AvitoTraineeApplication.getAppContext().assets.open(fileName)
        return asset.bufferedReader().use { it.readText() }
    }

    @Throws(JSONException::class, Exception::class)
    fun getJsonServices(rawData: String): HashMap<String, Service> {

        val servicesMap = HashMap<String, Service>()

        val jsonData = JSONObject(rawData)
        val jsonServiceItems = jsonData.getJSONArray("services")

        for (i in 0 until jsonServiceItems.length()) {
            val sName = jsonServiceItems[i] as String
            servicesMap[sName] = Service(sName, true, arrayListOf())

        }
        return servicesMap
    }

    @Throws(JSONException::class, Exception::class)
    fun getJsonPins(rawData: String): ArrayList<Pin> {

        val pinsArray = ArrayList<Pin>()

        val jsonData = JSONObject(rawData)
        val jsonPinItems = jsonData.getJSONArray("pins")

        for (i in 0 until jsonPinItems.length()) {
            val jsonPin = jsonPinItems.getJSONObject(i)

            val id = jsonPin.getInt("id")
            val service = jsonPin.getString("service")
            val coords = jsonPin.getJSONObject("coordinates")
            val lat = coords.getDouble("lat")
            val lng = coords.getDouble("lng")

            pinsArray.add(Pin(id, service, lat, lng))
        }
        return pinsArray
    }
}