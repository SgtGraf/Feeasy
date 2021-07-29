package com.example.feeasy.Threads;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;


public class ServerHandler extends Thread {

    public Looper looper;
    public Handler handler;

    @Override
    public void run(){
        Looper.prepare();

        looper = Looper.myLooper();

        handler = new Handler();

        Looper.loop();

        Log.i("Handler", "END");
    }
}
