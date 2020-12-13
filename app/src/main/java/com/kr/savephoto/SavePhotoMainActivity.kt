package com.kr.savephoto

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class SavePhotoMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        instructions.setOnClickListener {
            val intent = Intent(this, InstructionsActivity::class.java)
            startActivity(intent)
        }

        about.setOnClickListener {
            AlertDialog.Builder(this).apply {
                setTitle("${getString(R.string.about)} ${getString(R.string.appName)}")
                setMessage("${getString(R.string.copyright)}\n\n${getString(R.string.designer)}")
                setCancelable(true)
                setPositiveButton(getString(R.string.ok)) { _, _ -> }
                setNeutralButton(getString(R.string.websiteHead)) { _, _ ->
                    val uri: Uri = Uri.parse(getString(R.string.websiteBody))
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    startActivity(intent)
                }
                show()
            }
        }
    }
}