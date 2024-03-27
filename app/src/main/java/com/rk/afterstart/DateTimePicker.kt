package com.rk.afterstart

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.emreesen.sntoast.SnToast
import com.emreesen.sntoast.Type
import com.rk.afterstart.databinding.ActivityDataBundlePassBinding
import com.rk.afterstart.databinding.ActivityDateTimePickerBinding
import nl.joery.timerangepicker.TimeRangePicker

class DateTimePicker : AppCompatActivity() {
    private lateinit var binding:ActivityDateTimePickerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityDateTimePickerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.picker.setOnTimeChangeListener(object : TimeRangePicker.OnTimeChangeListener {
            @SuppressLint("SetTextI18n")
            override fun onStartTimeChange(startTime: TimeRangePicker.Time) {
                binding.textView6.text="Start time: " + startTime
            }
            @SuppressLint("SetTextI18n")
            override fun onEndTimeChange(endTime: TimeRangePicker.Time) {
                binding.textView7.text="End time: " + endTime.hour
            }
            @SuppressLint("SetTextI18n")
            override fun onDurationChange(duration: TimeRangePicker.TimeDuration) {
                binding.textView8.text="Duration: " + duration.hour
            }
        })
    }
}