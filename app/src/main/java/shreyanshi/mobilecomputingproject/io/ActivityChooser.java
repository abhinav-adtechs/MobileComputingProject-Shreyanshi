package shreyanshi.mobilecomputingproject.io;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ActivityChooser extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.btn_synthesized)
    Button btnSynthesizedSpeech ;

    @BindView(R.id.btn_realtime)
    Button btnRealTimeSpeech;

    Intent intent ;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chooser);

        ButterKnife.bind(this) ;


        btnRealTimeSpeech.setOnClickListener(this);
        btnSynthesizedSpeech.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {


        switch (v.getId()){

            case R.id.btn_realtime :
                intent = new Intent(this, MainActivity.class) ;

                break;

            case R.id.btn_synthesized:
                intent = new Intent(this, MainActivityPrevious.class) ;
                break;
        }


        startActivity(intent);
    }
}
