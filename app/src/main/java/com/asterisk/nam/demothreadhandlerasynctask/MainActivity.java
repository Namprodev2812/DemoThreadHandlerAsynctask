package com.asterisk.nam.demothreadhandlerasynctask;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button mButtonSend;
    TextView mTextMessenger, mTextLooper, mTextMessenger2, mTextLooper2;
    Handler mHandler,mHandler2,mHandler3;
    android.os.Looper mLooperThread2;
    public  static final int MESSAGER_COUNT_DOWN = 69;
    public  static final int MESSAGER_COUNT_DOWN_FINISH = 00;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        click();
        initHandler();
    }

    private void initHandler() {
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){

                    case MESSAGER_COUNT_DOWN:
                        updateUITextView(mTextMessenger,msg.arg1+"");
                        break;
                    case MESSAGER_COUNT_DOWN_FINISH:
                        mButtonSend.setText("OUT");
                        break;
                }
            }
        };
    }

    public void init(){
        mButtonSend = (Button) findViewById(R.id.btn_main);mTextMessenger = (TextView) findViewById(R.id.tv_main);
        mTextLooper = (TextView) findViewById(R.id.tv_main_1);mTextMessenger2 = (TextView) findViewById(R.id.tv_main_2);
        mTextLooper2 = (TextView) findViewById(R.id.tv_main_3);
    }

    public void click(){
        mButtonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doCountDown();
            }
        });
    }

    public void doCountDown(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                int time  = 1000;
                do {
                    time --;

                    Message msg = new Message();
                    msg.what = MESSAGER_COUNT_DOWN    ;
                    msg.arg1 = time;
                    mHandler.sendMessage(msg);

                    updateUITextViewWithHandlerPost(time+"");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }while (time > 0);
                mHandler.sendEmptyMessage(MESSAGER_COUNT_DOWN_FINISH);
            }
        });

        thread.start();
    }

    public void updateUITextView(TextView tv_mymessenger,String message){
        tv_mymessenger.setText(""+message);
    }
    public void updateUITextViewWithHandlerPost(final String time){
        mHandler = new Handler(android.os.Looper.getMainLooper());
        mHandler3.post(new Runnable() {
            @Override
            public void run() {
                mTextMessenger.setText(""+ time);
            }
        });
    }
}

