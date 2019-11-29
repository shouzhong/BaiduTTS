# BaiduTTS
## 说明
百度语音合成资源包
## 使用
### 依赖
```
implementation 'com.shouzhong:BaiduTTS:1.0.3'
```
### 配置
在AndroidManifest的application标签
```
<meta-data
    android:name="BAIDU_TTS_APP_ID"
    android:value="\你申请的"/>
<meta-data
    android:name="BAIDU_TTS_APP_KEY"
    android:value="你申请的"/>
<meta-data
    android:name="BAIDU_TTS_APP_SECRET"
    android:value="你申请的"/>
```
## 混淆
```
-keep class com.baidu.tts.**{*;}
-keep class com.baidu.speechsynthesizer.**{*;}
```