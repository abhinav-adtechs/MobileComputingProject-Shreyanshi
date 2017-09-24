package shreyanshi.mobilecomputingproject.io

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.speech.RecognizerIntent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast

import com.skyfishjy.library.RippleBackground

import java.util.ArrayList
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private var btnSpeak: ImageView? = null
    private val REQ_CODE_SPEECH_INPUT = 100

    private val micState = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rippleBackground = findViewById(R.id.content) as RippleBackground


        btnSpeak = findViewById(R.id.activity_main_btn_mic) as ImageView
        btnSpeak!!.setOnClickListener {
            rippleBackground.startRippleAnimation()
            promptSpeechInput()
        }
    }

    private fun promptSpeechInput() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "SAY SOMETHING")
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT)
        } catch (a: ActivityNotFoundException) {
            Toast.makeText(applicationContext,
                    "Your device doesn't support speech",
                    Toast.LENGTH_SHORT).show()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            REQ_CODE_SPEECH_INPUT -> {
                if (resultCode == Activity.RESULT_OK && null != data) {

                    val result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    for (i in result) {
                        Log.i(TAG, "onActivityResult: TEXT: " + i)
                    }

                }
            }
        }
    }

    companion object {

        private val TAG = "TAG"
    }
}
