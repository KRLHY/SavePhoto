package com.kr.savephoto2

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.kr.savephoto2.databinding.ActivitySavePhotoMainBinding

class SavePhotoMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySavePhotoMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySavePhotoMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.buttonInstructions.setOnClickListener {
            val intent = Intent(this, SavePhotoInstructionsActivity::class.java)
            startActivity(intent)
        }
        binding.buttonAbout.setOnClickListener {
            AlertDialog.Builder(this).setTitle(getString(R.string.about_title))
                .setMessage("${getString(R.string.about_content_copyright)}\n\n${getString(R.string.about_content_designer)}")
                .setPositiveButton(R.string.button_ok) { _, _ -> }
                .setNeutralButton(getString(R.string.download_title)) { _, _ ->
                    val uri: Uri = Uri.parse(getString(R.string.download_content))
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    startActivity(intent)
                }
                .create()
                .show()
        }
    }
}