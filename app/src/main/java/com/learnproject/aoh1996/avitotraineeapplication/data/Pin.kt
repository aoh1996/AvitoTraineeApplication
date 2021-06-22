package com.learnproject.aoh1996.avitotraineeapplication.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pin (val id: Int, val service: String, val lat: Double, val lng: Double) : Parcelable {

}