package com.example.myapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.bean.HourWeather
import com.example.myapplication.databinding.AdapterHourWeatherBinding

class HourWeatherAdapter(var list: MutableList<HourWeather.Hourly>) :
    RecyclerView.Adapter<HourWeatherAdapter.ViewHolder>() {
    class ViewHolder(binding: AdapterHourWeatherBinding) : RecyclerView.ViewHolder(binding.root) {
        val text = binding.textView8
        val img = binding.imageView2
        val text2 = binding.textView9
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            AdapterHourWeatherBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        list[position].run {
            // 解析出时间，"2023-07-30T14:00+00:00"
            val time = fxTime.split('T')[1].split('+')[0]
            holder.text.text = time
            holder.text2.text = "${temp}°"
        }
    }
}