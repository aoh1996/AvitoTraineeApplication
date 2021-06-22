package com.learnproject.aoh1996.avitotraineeapplication.data

import android.os.Parcelable
import androidx.annotation.Nullable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Service (val name: String, var isActive: Boolean,
                    var servicePins: ArrayList<Pin>) : Parcelable {
}