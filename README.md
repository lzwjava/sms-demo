AVOS Cloud中的短信验证示例
[短信验证码服务](https://cn.avoscloud.com/docs/android_guide.html#短信验证码服务)

##安装
导入AVOSCloud SDK，
同时注意App.java
```java
public class App extends Application{
  public void onCreate() {
    //请用你的AppId，AppKey。并在管理台启用手机号码短信验证
    AVOSCloud.initialize(this, "",
        "");
  }
}
```

##发送验证短信，

```java
AVOSCloud.requestSMSCode(phone, "SmsDemo", "注册", 10);
```

##判别验证码，

```java
 AVOSCloud.verifySMSCodeInBackground(code,new AVMobilePhoneVerifyCallback() {
      @Override
      public void done(AVException e) {
        if(e==null){
          toast(R.string.verifySucceed);
        }else{
          e.printStackTrace();
          toast(R.string.verifyFailed);
        }
      }
    });
```
