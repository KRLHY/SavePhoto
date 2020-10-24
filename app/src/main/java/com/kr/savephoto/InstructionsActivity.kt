package com.kr.savephoto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_instructions.*

class InstructionsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_instructions)
        title = "${getString(R.string.appName)} ${getString(R.string.instructions)}"
        instructionsHead.text =
            "\"${getString(R.string.appName)}\" ${getString(R.string.instructions)}"
        instructionsBody.text =
            "${getString(R.string.instructionsBody, getString(R.string.appName))}"
        instructionsCopyright.text = getString(R.string.copyright)
        instructionsDesigner.text = getString(R.string.designer)
    }
}