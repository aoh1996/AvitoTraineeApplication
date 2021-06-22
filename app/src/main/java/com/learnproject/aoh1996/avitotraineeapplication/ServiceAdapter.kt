package com.learnproject.aoh1996.avitotraineeapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.TextView
import com.learnproject.aoh1996.avitotraineeapplication.data.DataHolder
import com.learnproject.aoh1996.avitotraineeapplication.data.Service

class ViewHolder(v: View) {
    val tvServiceName: TextView = v.findViewById(R.id.servieNameTextView)
    val cbIsActive: CheckBox = v.findViewById(R.id.serviceIsActiveCheckBox)
    val tvTotalServices: TextView = v.findViewById(R.id.totalServicesTextView)
}

class ServiceAdapter (context: Context, private val resource: Int, var services: List<Service>)
    : ArrayAdapter<List<Service>>(context, resource){

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getCount(): Int {
        return  services.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val viewHolder: ViewHolder

        if (convertView == null) {
            view = inflater.inflate(resource, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        val currentService = services[position]

        viewHolder.tvServiceName.text = String.format(context.getString(R.string.item_service_name), currentService.name.uppercase())
        viewHolder.cbIsActive.isChecked = currentService.isActive
        viewHolder.tvTotalServices.text = String.format(context.getString(R.string.item_total_pins), currentService.servicePins.size)
        viewHolder.cbIsActive.setOnCheckedChangeListener { _, isChecked ->
            DataHolder.getInstance().toggleService(currentService.name, isChecked)
        }


        return view
    }

    fun setServicesList(servicesList: List<Service>) {
        services = servicesList
        notifyDataSetChanged()
    }
}

