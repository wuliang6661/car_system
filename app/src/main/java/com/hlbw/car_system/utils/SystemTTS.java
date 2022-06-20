package com.hlbw.car_system.utils;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.hlbw.car_system.bean.SettingChildBean;
import com.hlbw.car_system.bean.SettingParentBean;
import com.hlbw.car_system.constans.ConstansConfig;

import java.util.List;
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
        textToSpeech = new TextToSpeech(mContext, i -> {
            LogUtils.d("开始初始化语音播放控件");
            //textToSpeech的配置
            init(i);
            isFirstPlay = false;
        });
    }


    public static SystemTTS getInstance(Context context) {
        if (singleton == null) {
            synchronized (SystemTTS.class) {
                if (singleton == null) {
                    singleton = new SystemTTS(context);
                }
            }
        }
        return singleton;
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
                textToSpeech.setSpeechRate(getRate());
            }
        } else {
            LogUtils.e("初始化失败！");
        }
    }


    /**
     * 获取设置的播放速度
     */
    private float getRate() {
        float rate = 1.0f;
        List<SettingParentBean> list = new ConstansConfig().getSettingData();
        for (SettingParentBean item : list) {
            if (item.getChildSettings() == null) {
                return 1.0f;
            }
            for (SettingChildBean childBean : item.getChildSettings()) {
                if (childBean.getType() == 1) {
                    rate = childBean.getOpenNum() == null ? 0 : childBean.getOpenNum() / 100;
                    return rate;
                }
            }
        }
        return rate;
    }


    public boolean play(String text) {
        boolean ret = false;
        text = syncNum(text);
        if (!isSupport) {
            Toast.makeText(mContext, "TTS不支持", Toast.LENGTH_SHORT).show();
            ret = true;
        }
        if (textToSpeech != null) {
            if (!isFirstPlay) {
                speak(text);
                isFirstPlay = true;
                ret = true;
            } else {
                if (!textToSpeech.isSpeaking()) {
                    speak(text);
                    ret = true;
                }
            }
        }
        return ret;
    }


    private String syncNum(String text) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            String str = isEnOrNum(String.valueOf(text.charAt(i))) ? text.charAt(i) + " " : String.valueOf(text.charAt(i));
            builder.append(str);
        }
        return builder.toString();
    }


    private boolean isEnOrNum(String str){
        return str.matches("^[0-9a-zA-Z]+$");
    }


    private boolean isNumber(String word) {
        boolean isNumber;
        try {
            Integer.parseInt(word);
            isNumber = true;
        } catch (NumberFormatException e) {
            isNumber = false;
        }
        return isNumber;
    }


    private void speak(String text) {
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
            singleton = null;
        }
    }
}

