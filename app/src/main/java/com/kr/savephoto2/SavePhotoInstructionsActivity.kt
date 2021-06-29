package com.kr.savephoto2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kr.savephoto2.databinding.ActivitySavePhotoInstructionsBinding

class SavePhotoInstructionsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySavePhotoInstructionsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar2.setNavigationOnClickListener {
            finish()
        }
    }
}