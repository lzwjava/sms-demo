package com.avos.sms;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVMobilePhoneVerifyCallback;
import com.avos.avoscloud.AVOSCloud;

public class MyActivity extends Activity implements View.OnClickListener {
  /**
   * Called when the activity is first created.
   */
  View send,verify;
  EditText phoneEdit,codeEdit;
  private Activity cxt;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    cxt=this;
    findView();
  }

  private void findView() {
    send=findViewById(R.id.sendCode);
    verify=findViewById(R.id.verify);
    phoneEdit= (EditText) findViewById(R.id.phoneEdit);
    codeEdit= (EditText) findViewById(R.id.codeEdit);
    send.setOnClickListener(this);
    verify.setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    int id=v.getId();
    if(id==R.id.sendCode){
      final String phone=phoneEdit.getText().toString();
      sendCode(phone);
    }else{
      final String code=codeEdit.getText().toString();
      verifyCode(code);
    }
  }

  private void verifyCode(String code) {
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
  }

  public void sendCode(final String phone) {
    new AsyncTask<Void, Void, Void>() {
      boolean res;
      @Override
      protected Void doInBackground(Void... params) {
        try {
          AVOSCloud.requestSMSCode(phone, "SmsDemo", "注册", 10);
          res=true;
        } catch (AVException e) {
          e.printStackTrace();
          res=false;
        }
        return null;
      }

      @Override
      protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if(res){
          toast(R.string.sendSucceed);
        }else{
          toast(R.string.sendFailed);
        }
      }
    }.execute();
  }

  private void toast(int id) {
    Toast.makeText(cxt, id, Toast.LENGTH_SHORT).show();
  }
}
