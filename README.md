# 探索广播接收器的使用
BroadcastReceiver（广播接收器）是Android中的四大组件之一，专注于接收广播通知信息，并做出对应处理的组件。

广播接收主要有两种，一种是用户发送的广播，另外一种是系统的广播消息的接收，包括用户来电、用户短信和拦截黑名单电话等。这里我们所要说的广播接收是监听我们自己注册的普通广播的一个接收。

使用广播接收需完成：
        （1）注册广播：注册方式有两种，一种是在AndroidManifest.xml文件中定义，指定该BroadcastReceiver所响应的Intent的Action；另一种是使用代码进行指定，调用BroadcastReceiver的Context的registerReceiver(BroadcastReceiver receiver, IntentFilter filter)方法指定；这两种注册方式都需要IntentFIlter。
        （2）发送广播：调用Context的sendBroadcast来发发送指定的BroadcastReceiver，也就是使用Intent来传递注册时用到的Action。
        （3）接收广播：当发送的广播被广播接收器监听到之后，BroadcastReceiver的onReceive()方法将会被触发，从而在该方法中显示广播所携带的信息或者进行一些简单的操作。

![ABC](http://img.blog.csdn.net/20160121005240767?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center)

MainActivity.class
```Java
package com.example.xiaolijuan.broadcastdome;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
/**
 * @author: xiaolijuan
 * @description:
 * @projectName: BroadcastDome
 * @date: 2016-01-20
 * @time: 23:28
 */
public class MainActivity extends Activity implements View.OnClickListener {
    private Button btn;
    private TextView textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button) findViewById(R.id.btn);
        textview = (TextView) findViewById(R.id.code);
        btn.setOnClickListener(this);

        RegisterBroadCase();
    }

    /**
     * 广播注册
     */
    private void RegisterBroadCase() {
        submitReceiver receiver = new submitReceiver();
        LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(getApplicationContext());
        IntentFilter filter = new IntentFilter();
        filter.addAction(SecondActivity.INTEGRAL_BROADCAST);
        lbm.registerReceiver(receiver, filter);
    }

    public class submitReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String intentAction = intent.getAction();
            if (intentAction.equals(SecondActivity.INTEGRAL_BROADCAST)) {
                textview.setText(intent.getExtras().getString("afterCode"));
            }
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        intent.putExtra("code", textview.getText());    //获取积分，将他保存在intent里面
        intent.setClass(MainActivity.this, SecondActivity.class);
        startActivity(intent);
    }
}
```
SecondActivity.class
```Java
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
 * @author: xiaolijuan
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
```

在使用的时候，需要注意：使用了LocalBroadcastManager注册广播之后，在发送广播的时候要使用LocalBroadcastManager.sendBroadcast(intent)，否则会接收不到广播。

