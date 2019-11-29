package com.shouzhong.baidutts;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.TtsMode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class BaiduTTSUtils {

    private static final String SPEECH_FEMALE_MODEL_NAME = "bd_etts_common_speech_f7_mand_eng_high_am-mix_v3.0.0_20170512.dat";
    private static final String TEXT_MODEL_NAME = "bd_etts_text.dat";

    public static SpeechSynthesizer getSpeechSynthesizer(Application app, boolean isMix) {
        String appId = getValue(app, "BAIDU_TTS_AAP_ID");
        String appKey = getValue(app, "BAIDU_TTS_APP_KEY");
        String appSecret = getValue(app, "BAIDU_TTS_APP_SECRET");
        SpeechSynthesizer speechSynthesizer = SpeechSynthesizer.getInstance();
        speechSynthesizer.setContext(app);
        int result = speechSynthesizer.setAppId(appId);
        if (result != 0) return null;
        result = speechSynthesizer.setApiKey(appKey, appSecret);
        if (result != 0) return null;
        if (isMix) {
            String path1 = app.getExternalFilesDir("baidutts").getAbsolutePath() + "/" + SPEECH_FEMALE_MODEL_NAME;
            String path2 = app.getExternalFilesDir("baidutts").getAbsolutePath() + "/" + TEXT_MODEL_NAME;
            copyAssetsFile2SDCard(app, SPEECH_FEMALE_MODEL_NAME, path1);
            copyAssetsFile2SDCard(app, TEXT_MODEL_NAME, path2);
            // 设置语音合成声音模型文件
            speechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_SPEECH_MODEL_FILE, path1);
            // 设置语音合成文本模型文件
            speechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_TEXT_MODEL_FILE, path2);
        }
        result = speechSynthesizer.initTts(isMix ? TtsMode.MIX : TtsMode.ONLINE);
        if (result != 0) return null;
        return speechSynthesizer;
    }

    private static String getValue(Application app, String key) {
        try {
            ApplicationInfo appInfo = app.getPackageManager().getApplicationInfo(app.getPackageName(), PackageManager.GET_META_DATA);
            return appInfo.metaData.getString(key);
        } catch (Exception e) {}
        return null;
    }

    private static void copyAssetsFile2SDCard(Application app, String fileName, String path) {
        try {
            InputStream is = app.getAssets().open(fileName);
            File file = new File(path);
            if (file.exists() && file.isFile() && file.length() == is.available()) {
                is.close();
                return;
            }
            file.delete();
            FileOutputStream fos = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int byteCount = 0;
            while ((byteCount = is.read(buffer)) != -1) {// 循环从输入流读取buffer字节
                fos.write(buffer, 0, byteCount);// 将读取的输入流写入到输出流
            }
            is.close();
            fos.close();
        } catch (Exception e) { }
    }
}
