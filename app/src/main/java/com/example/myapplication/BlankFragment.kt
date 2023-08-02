package com.example.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.FragmentBlankBinding

class BlankFragment : Fragment() {
    private lateinit var binding: FragmentBlankBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var dayWeatherAdapter: DayWeatherAdapter
    private lateinit var hourWeatherAdapter: HourWeatherAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBlankBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onResume() {
        super.onResume()
        binding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        hourWeatherAdapter = HourWeatherAdapter(mutableListOf())
        binding.recyclerView.adapter = hourWeatherAdapter
        binding.recyclerView2.layoutManager = LinearLayoutManager(requireContext())
        dayWeatherAdapter = DayWeatherAdapter(mutableListOf())
        binding.recyclerView2.adapter = dayWeatherAdapter
        // 更新数据
        viewModel.nowWeather.observe(requireActivity()) { nowWeather ->
            // 停止刷新
            if (binding.swipeRefreshLayout.isRefreshing) {
                binding.swipeRefreshLayout.isRefreshing = false
            }
            viewModel.nearestCity()?.run {
                binding.textView.text = locationNameZh

            }
            nowWeather.now.run {
                binding.textView2.text = "${temp}°"
                binding.textView3.text = text
                binding.textView4.text = windDir
            }
        }
        viewModel.hour24Weather.observe(requireActivity()) {
            // 停止刷新
            if (binding.swipeRefreshLayout.isRefreshing) {
                binding.swipeRefreshLayout.isRefreshing = false
            }
            hourWeatherAdapter.list.clear()
            hourWeatherAdapter.list.addAll(it.hourly)
            hourWeatherAdapter.notifyDataSetChanged()
        }
        viewModel.day7Weather.observe(requireActivity()) {
            // 停止刷新
            if (binding.swipeRefreshLayout.isRefreshing) {
                binding.swipeRefreshLayout.isRefreshing = false
            }
            dayWeatherAdapter.list.clear()
            dayWeatherAdapter.list.addAll(it.daily)
            dayWeatherAdapter.notifyDataSetChanged()
        }
        // 下拉刷新
        binding.swipeRefreshLayout.setOnRefreshListener {
            refreshWeather()
        }
        // 刚开始，先展示数据
        refreshWeather()
    }

    private fun refreshWeather() {
        val location = (requireActivity() as MainActivity).location()
        // 第一次需要请求权限
        if (location == null) {
            if (binding.swipeRefreshLayout.isRefreshing) {
                binding.swipeRefreshLayout.isRefreshing = false
            }
            return
        }
        viewModel.nearestCity(location)?.let {
            viewModel.refreshWeather(it)
        }
    }
}