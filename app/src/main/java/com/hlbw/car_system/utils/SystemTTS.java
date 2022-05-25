package com.hlbw.car_system.utils;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.widget.Toast;

import java.util.Locale;

public class SystemTTS {

    private static SystemTTS singleton;
    private Context mContext;
    //核心播放对象
    private TextToSpeech textToSpeech;
    private boolean isSupport = true;
    private static final String TAG = "SystemTTS";
    private boolean isFirstPlay = false;

    private SystemTTS(Context context) {
        this.mContext = context.getApplicationContext();
        textToSpeech = new TextToSpeech(mContext, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                //textToSpeech的配置
                init(i);
                isFirstPlay = false;
            }
        });
    }


    public static SystemTTS getInstance(Context context) {
//        if (singleton == null) {
//            synchronized (SystemTTS.class) {
//                if (singleton == null) {
//                    singleton = new SystemTTS(context);
//                }
//            }
//        }
        return singleton = new SystemTTS(context);
    }

    //textToSpeech的配置
    private void init(int i) {
        if (i == TextToSpeech.SUCCESS) {
            int result = textToSpeech.setLanguage(Locale.CHINESE);
            // 设置音调，值越大声音越尖（女生），值越小则变成男声,1.0是常规
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                //系统不支持中文播报
                isSupport = false;
            } else {
                textToSpeech.setPitch(1.0f);
                textToSpeech.setSpeechRate(1.0f);
            }
        }
    }

    public boolean play(String text) {
        boolean ret=false;
        if (!isSupport) {
            Toast.makeText(mContext, "TTS不支持", Toast.LENGTH_SHORT).show();
            ret=true;
        }
        if (textToSpeech != null) {
            if(!isFirstPlay){
                speak(text);
                isFirstPlay = true;
                ret=true;
            }else{
                if(!textToSpeech.isSpeaking()){
                    speak(text);
                    ret=true;
                }
            }
        }
        return  ret;
    }

    private void speak(String text){
        textToSpeech.speak(text, TextToSpeech.QUEUE_ADD, null, null);
        textToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
            @Override
            public void onStart(String utteranceId) {
                Log.d(TAG, "播放开始");
            }

            @Override
            public void onDone(String utteranceId) {
                Log.d(TAG, "播放结束");
            }

            @Override
            public void onError(String utteranceId) {
                Log.d(TAG, "播放出错");
            }
        });
    }

    public void stop() {
        if (textToSpeech != null) {
            textToSpeech.stop();
        }
    }

    public boolean TTsIsSpeeking() {
        return textToSpeech.isSpeaking();
    }

    public void destroy() {
        stop();
        if (textToSpeech != null) {
            textToSpeech.shutdown();
        }
    }
}

