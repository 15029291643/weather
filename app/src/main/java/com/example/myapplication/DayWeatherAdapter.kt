package com.example.myapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.bean.DayWeather
import com.example.myapplication.databinding.AdapterDayWeatherBinding

class DayWeatherAdapter(val list: MutableList<DayWeather.Daily>) :
    RecyclerView.Adapter<DayWeatherAdapter.ViewHolder>() {
    class ViewHolder(binding: AdapterDayWeatherBinding) : RecyclerView.ViewHolder(binding.root) {
        val text = binding.textView5
        val img = binding.imageView
        val text2 = binding.textView6
        val text3 = binding.textView7
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            AdapterDayWeatherBinding.inflate(
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
            // 解析时间，"2023-07-30"
            val data = try {
                fxDate.split('-').let {
                    "${it[1]}-${it[2]}"
                }
            } catch (_: Exception) {
                ""
            }
            holder.text.text = data
            holder.text2.text = textDay
            holder.text3.text = "${tempMax}°/${tempMin}°"
        }
    }
}