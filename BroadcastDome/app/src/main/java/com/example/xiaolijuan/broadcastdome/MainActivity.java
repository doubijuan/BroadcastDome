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
