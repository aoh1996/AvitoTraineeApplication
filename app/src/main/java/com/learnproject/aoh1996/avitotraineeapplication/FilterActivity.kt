package com.learnproject.aoh1996.avitotraineeapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import androidx.activity.viewModels
import com.learnproject.aoh1996.avitotraineeapplication.data.DataHolder
import java.util.*

private const val TAG = "FilterActivity"

class FilterActivity : AppCompatActivity() {

    private lateinit var dataHolder: DataHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)

        Log.d(TAG, "onCreate: ")

        dataHolder = DataHolder.getInstance()

        val adapter = ServiceAdapter(applicationContext, R.layout.list_item, Collections.emptyList())
        findViewById<ListView>(R.id.servicesListView).adapter = adapter


        dataHolder.servicesWithPins.value?.values!!.toList().let {
            for (service in it) {
                Log.d(TAG, "is active: ${service.isActive}")
            }
            adapter.setServicesList(it) }
    }


}