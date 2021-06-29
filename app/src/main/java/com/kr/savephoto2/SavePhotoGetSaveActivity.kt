package com.kr.savephoto2

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.kr.savephoto2.databinding.ActivitySavePhotoGetSaveBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedOutputStream

class SavePhotoGetSaveActivity : AppCompatActivity() {
    var bitmap: Bitmap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySavePhotoGetSaveBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent = intent
        if (intent == null || intent.type?.length!! < 5) {
            showFalseAlertDialog()
        } else if (intent.type!!.substring(0, 5) != "image") {
            showFalseAlertDialog()
        } else {
            bitmap = BitmapFactory.decodeStream(
                contentResolver.openInputStream(
                    intent.getParcelableExtra(
                        Intent.EXTRA_STREAM
                    )!!
                )
            )
            Glide.with(this).load(bitmap).diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(binding.imageView)
        }
        binding.buttonSavePhoto.setOnClickListener {
            val saveIntent = Intent(Intent.ACTION_CREATE_DOCUMENT)
            saveIntent.type = "image/jpeg"
            saveIntent.addCategory(Intent.CATEGORY_OPENABLE)
            saveIntent.putExtra(Intent.EXTRA_TITLE, getString(R.string.app_name))
            startActivityForResult(saveIntent, 1)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val alertDialog = AlertDialog.Builder(this).setView(R.layout.alertdialog_processing)
            .setTitle(getString(R.string.photo_saving_title))
            .setCancelable(false)
            .create()
        alertDialog.show()
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                val fileOutputStream = contentResolver.openOutputStream(data?.data!!)
                val fileStream = BufferedOutputStream(fileOutputStream)
                bitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, fileStream)
                fileStream.flush()
                fileStream.close()
                MainScope().launch {
                    alertDialog.dismiss()
                    AlertDialog.Builder(this@SavePhotoGetSaveActivity)
                        .setTitle(getString(R.string.save_photo_success_title))
                        .setMessage(getString(R.string.save_photo_success_content))
                        .setCancelable(false)
                        .setPositiveButton(getString(R.string.button_ok)) { _, _ -> finish() }
                        .create()
                        .show()
                }
            }
        }
    }
    private fun showFalseAlertDialog() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.get_photo_error_title))
            .setMessage(getString(R.string.get_photo_error_content))
            .setCancelable(false)
            .setPositiveButton(getString(R.string.button_ok)) { _, _ -> finish() }
            .create()
            .show()
    }
}