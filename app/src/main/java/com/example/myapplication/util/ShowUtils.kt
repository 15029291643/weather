package com.example.myapplication.util

import android.util.Log
import android.widget.Toast
import com.example.myapplication.MainActivity
import com.example.myapplication.MyApp

fun Any?.loge() = Log.e("haojinhui", this.toString())

fun Any?.println() = println(this.toString())

fun Any?.toast() = MainActivity.activity.runOnUiThread {
    Toast.makeText(MyApp.context, this.toString(), Toast.LENGTH_SHORT)
}

fun Any?.show() {
    try {
        loge()
    } catch (e: Exception) {
        try {
            e.loge()
        } catch (_: Exception) {
        }
    }
    try {
        println()
    } catch (e: Exception) {
        try {
            e.loge()
        } catch (_: Exception) {
        }
    }
    try {
        toast()
    } catch (e: Exception) {
        try {
            e.loge()
        } catch (_: Exception) {
        }
    }
}

fun main() {
    "测试".loge()
}
