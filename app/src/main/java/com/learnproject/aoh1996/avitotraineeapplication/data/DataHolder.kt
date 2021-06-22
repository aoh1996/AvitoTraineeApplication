package com.learnproject.aoh1996.avitotraineeapplication.data


import android.content.Context
import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.learnproject.aoh1996.avitotraineeapplication.SingletonHolder

private const val TAG = "DataHolder"

class DataHolder : ViewModel() {

    private val _servicesWithPins = MutableLiveData<Map<String, Service>>()
    val servicesWithPins: LiveData<Map<String, Service>> = _servicesWithPins

    init {
        getData()
    }


    private fun getData() {
        val rawData = JsonParser.getRawData("pins.json")
        val jsonServices = JsonParser.getJsonServices(rawData)

        val pins = JsonParser.getJsonPins(rawData)

        for (pin in pins) {
            if (jsonServices.containsKey(pin.service)) {
                jsonServices[pin.service]?.apply {
                    servicePins.add(pin)
                }
            }
        }

        _servicesWithPins.value = jsonServices
    }

    fun toggleService(serviceName: String, newStatus: Boolean) {
        _servicesWithPins.value?.get(serviceName)?.isActive  = newStatus
        Log.d(TAG, "toggleService: $serviceName -> $newStatus")
    }


//    companion object : SingletonHolder<DataHolder, Context>(::DataHolder)

    companion object {
        private lateinit var instance: DataHolder

        fun getInstance(): DataHolder {
            instance = if (::instance.isInitialized) instance else DataHolder()
            return instance
        }
    }
}