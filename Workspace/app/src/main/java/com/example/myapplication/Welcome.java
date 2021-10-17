package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import android.widget.TextView;

import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechUtility;

import org.w3c.dom.Text;

public class Welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        SpeechUtility.createUtility(this, SpeechConstant.APPID +"=b8e23876");
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String nickname = intent.getStringExtra("nickname");
        String email = intent.getStringExtra("email");

        Button btn_settings = findViewById(R.id.Btn_Settings);
        Button btn_recording = (Button) findViewById(R.id.Btn_Recording);
        TextView welcome_msg = (TextView) findViewById(R.id.welcome_msg);

        class MyRecoListener implements RecognizerListener {
            @Override
            public void onVolumeChanged(int i, byte[] bytes) {

            }

            @Override
            public void onBeginOfSpeech() {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onResult(RecognizerResult recognizerResult, boolean b) {
                TextView result = (TextView) findViewById(R.id.result) ;
                String result_string = recognizerResult.getResultString();
                result.append(result_string);
            }

            @Override
            public void onError(SpeechError speechError) {

            }

            @Override
            public void onEvent(int i, int i1, int i2, Bundle bundle) {

            }

        }

        SpeechRecognizer mIat = SpeechRecognizer.createRecognizer(this, null);
        mIat.setParameter(SpeechConstant.CLOUD_GRAMMAR, null);
        mIat.setParameter(SpeechConstant.SUBJECT, null);
        mIat.setParameter(SpeechConstant.RESULT_TYPE,"plain");
        mIat.setParameter(SpeechConstant.DOMAIN, "iat");
        mIat.setParameter(SpeechConstant.ENGINE_TYPE,"cloud");
        mIat.setParameter(SpeechConstant.LANGUAGE, "en_us");
        mIat.setParameter(SpeechConstant.VAD_BOS,"4000");
        mIat.setParameter(SpeechConstant.VAD_EOS,"1000");
        mIat.setParameter(SpeechConstant.ASR_PTT,"0");

        welcome_msg.setText(nickname);

        btn_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Welcome.this, Settings.class);
                intent.putExtra("Username", username);
                startActivity(intent);
            }
        });

        btn_recording.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mIat.startListening(new MyRecoListener());
                        break;
                    case MotionEvent.ACTION_UP:
                        TextView result = (TextView) findViewById(R.id.result) ;
                        result.setText("");
                        break;
                }
                return true;
            }
        });



        String id = intent.getStringExtra("id");
        welcome_msg.setText(id);

    }
}