package com.example.feeasy.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.SocketHandler;

public class ServerService extends Service {

    public static final String START_SERVER = "startserver";
    public static final String STOP_SERVER = "stopserver";
    public static final int SERVERPORT = 80;
    public static final String SERVERIP = "feeazy-server.herokuapp.com";

    Socket clientSocket;
    Thread serverThread;

    public ServerService() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();

        if(action.equals(START_SERVER)) {
            this.serverThread = new Thread(new ServerThread());
            this.serverThread.start();
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    class ServerThread implements Runnable {

        @Override
        public void run() {
            Log.i("Thread", "works!");
            try {
                Socket socket = new Socket(SERVERIP, SERVERPORT);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
//                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

                /*PrintWriter writer = new PrintWriter(objectOutputStream, true);
                writer.println("Message to Fabi");*/
                Log.i("Socket", socket.isConnected()+"");
                objectOutputStream.writeUTF("hallo");
                objectOutputStream.flush();

                objectOutputStream.writeUTF("hallo2");
                objectOutputStream.flush();
                Log.i("Socket", socket.isConnected()+"");

                Log.i("CONNECTION", "works!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
