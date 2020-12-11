package com.kr.savephoto

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_savephotobutton.*
import kotlinx.android.synthetic.main.activity_savephotopreviewimage.*
import java.io.BufferedOutputStream
import kotlin.system.exitProcess

class GetShareActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_getshare)
        val intent = intent
        if (intent == null || intent.type?.length!! < 5) {
            AlertDialog.Builder(this).apply {
                setTitle(getString(R.string.error))
                setMessage(getString(R.string.getShareFailed))
                setCancelable(false)
                setPositiveButton(getString(R.string.ok)) { _, _ -> finish() }
                show()
            }
        } else if (intent.type!!.substring(0, 5) != "image") {
            AlertDialog.Builder(this).apply {
                setTitle(getString(R.string.error))
                setMessage(getString(R.string.getShareFailed))
                setCancelable(false)
                setPositiveButton(getString(R.string.ok)) { _, _ -> finish() }
                show()
            }
        } else {
            val uri = intent.getParcelableExtra<Uri>(Intent.EXTRA_STREAM)
            val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri!!))
            Glide.with(this).load(bitmap).into(previewImageView)
            savePhoto.setOnClickListener {
                val saveIntent = Intent(Intent.ACTION_CREATE_DOCUMENT)
                saveIntent.type = "image/jpeg"
                saveIntent.addCategory(Intent.CATEGORY_OPENABLE)
                saveIntent.putExtra(Intent.EXTRA_TITLE, getString(R.string.appName))
                startActivityForResult(saveIntent, 1)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            val intent = intent
            val uri = intent.getParcelableExtra<Uri>(Intent.EXTRA_STREAM)
            val urireturn = data.data
            val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri!!))
            val fileOutupStream =
                contentResolver.openOutputStream(urireturn!!)
            val filestream = BufferedOutputStream(fileOutupStream)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, filestream)
            filestream.flush()
            filestream.close()
            bitmap.recycle()
            Toast.makeText(
                this,
                getString(R.string.saveSuccess, getString(R.string.appName)),
                Toast.LENGTH_SHORT
            ).show()
            finish()
        }
    }
}