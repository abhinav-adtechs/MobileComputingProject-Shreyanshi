package shreyanshi.mobilecomputingproject.io;

import android.content.Intent;
import android.graphics.Color;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gelitenight.waveview.library.WaveView;
import com.skyfishjy.library.RippleBackground;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "TAG";
    private ImageView btnSpeak;
    private final int REQ_CODE_SPEECH_INPUT = 100;

    private TextView tvResult ;
    private boolean micState = false ;
    SpeechRecognizer recognizer ;
    Intent intent ;

    private WaveHelper waveHelper ;
    private WaveView waveView ;

    long millis ;

    private MainActivity mainActivity ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RippleBackground rippleBackground=(RippleBackground) findViewById(R.id.content);

        waveView = (WaveView) findViewById(R.id.activity_main_waveview) ;
        waveHelper = new WaveHelper(waveView) ;

        waveView.setShapeType(WaveView.ShapeType.SQUARE);

        mainActivity = (MainActivity) this ;

        tvResult = (TextView) findViewById(R.id.activity_main_tv_result) ;
        btnSpeak = (ImageView) findViewById(R.id.activity_main_btn_mic) ;

        initializeSpeechRecognition();

        btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rippleBackground.startRippleAnimation();
                if (!micState){
                    micState = true ;
                    recognizer.startListening(intent);
                    waveHelper.start();
                }
                else{
                    micState = false ;
                    recognizer.stopListening();
                    waveHelper.cancel();
                }

            }
        });


    }

    private void initializeSpeechRecognition() {
        intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
                "shreyanshi.mobilecomputingproject.io");
        intent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS,true);


        recognizer = SpeechRecognizer
                .createSpeechRecognizer(this.getApplicationContext());


        RecognitionListener recognitionListener = new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {
                Log.d(TAG, "onReadyForSpeech() called with: params = [" + params + "]");
            }

            @Override
            public void onBeginningOfSpeech() {
                Log.d(TAG, "onBeginningOfSpeech() called");
            }

            @Override
            public void onRmsChanged(float rmsdB) {

            }

            @Override
            public void onBufferReceived(byte[] buffer) {

            }

            @Override
            public void onEndOfSpeech() {
                waveHelper.cancel();
            }

            @Override
            public void onError(int error) {
                Log.e(TAG, "onError() called with: error = [" + error + "]");
                micState = false ;
                waveHelper.cancel();
            }

            @Override
            public void onResults(Bundle results) {
                Log.d(TAG, "onResults() called with: results = [" + results.toString() + "]");
                tvResult.setText(results.getStringArrayList("results_recognition").get(0));
                millis = System.currentTimeMillis()  - millis;

                Toast.makeText(mainActivity, "Duration of synthesis is : " + (millis%1000) + "secs", Toast.LENGTH_LONG).show();

                micState = false ;
                waveHelper.cancel();
            }

            @Override
            public void onPartialResults(Bundle partialResults) {
                Log.i(TAG, "onPartialResults: " + partialResults.getStringArrayList("results_recognition"));
                tvResult.setText(partialResults.getStringArrayList("results_recognition").get(0));

            }

            @Override
            public void onEvent(int eventType, Bundle params) {

            }
        };

        recognizer.setRecognitionListener(recognitionListener);


        /*try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    "Your device doesn't support speech",
                    Toast.LENGTH_SHORT).show();
        }*/
    }

}
