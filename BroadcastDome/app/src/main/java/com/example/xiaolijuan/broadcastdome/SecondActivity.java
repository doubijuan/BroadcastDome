package com.example.xiaolijuan.broadcastdome;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author: adan
 * @description:
 * @projectName: BroadcastDome
 * @date: 2016-01-20
 * @time: 23:28
 */
public class SecondActivity extends Activity implements View.OnClickListener{
    public static final String INTEGRAL_BROADCAST = "INTEGRAL_BROADCAST";
    TextView currentCode;
    EditText editText;
    Button sureBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        currentCode = (TextView) findViewById(R.id.currentCode);    //当前的积分，把传过来的数据绑定在textview控件
        currentCode.setText(getIntent().getStringExtra("code")); //设置当前的积分
        editText = (EditText) findViewById(R.id.editText);
        sureBtn = (Button) findViewById(R.id.sure);
        sureBtn.setOnClickListener(this);
    }

    /**
     * 发送广播信息
     */
    public void sendMessage(String afterCode) {
        Intent intent = new Intent();
        intent.putExtra("afterCode", afterCode);
        intent.setAction(INTEGRAL_BROADCAST);
        LocalBroadcastManager l = LocalBroadcastManager
                .getInstance(SecondActivity.this);
        l.sendBroadcast(intent);
    }

    @Override
    public void onClick(View v) {
        String afterCode = editText.getText().toString();
        if(TextUtils.isEmpty(afterCode)) {
            Toast.makeText(SecondActivity.this, "更改积分不能为空！", Toast.LENGTH_SHORT).show();
        } else {
            currentCode.setText(afterCode);
            sendMessage(afterCode);
        }
    }
}
