package shreyanshi.mobilecomputingproject.io;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivityPrevious extends AppCompatActivity{

    private static final String TAG = "TAG";
    private ImageView btnSpeak;
    private final int REQ_CODE_SPEECH_INPUT = 100;

    private final boolean micState = false ;

    long millis ;

    @BindView(R.id.tv_result)
    TextView tvResult ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_previous);



        ButterKnife.bind(this) ;

        btnSpeak = (ImageView) findViewById(R.id.activity_main_btn_mic) ;
        btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                promptSpeechInput();
            }
        });
    }

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "SAY SOMETHING");
        try {
            millis = System.currentTimeMillis() ;
            Log.i(TAG, "promptSpeechInput: " + millis);
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    "Your device doesn't support speech",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    Log.i(TAG, "onActivityResult: TEXT: " + result.get(0));

                    millis = System.currentTimeMillis() - millis;
                    Log.i(TAG, "onActivityResult: " + (System.currentTimeMillis()));
                    tvResult.setText(result.get(0));
                    Toast.makeText(this, "Duration of synthesis is : " + (millis%1000) + "secs", Toast.LENGTH_LONG).show();

                }
                break;
            }

        }
    }
}
