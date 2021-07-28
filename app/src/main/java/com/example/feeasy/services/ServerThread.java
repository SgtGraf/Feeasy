package com.example.feeasy.services;

import android.util.Log;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerThread implements Runnable {

    public static final int SERVERPORT = 80;
    public static final String SERVERIP = "feeazy-server.herokuapp.com";
    Thread serverThread;




    @Override
    public void run() {
        Log.i("Thread", "works!");
        try {
            Socket socket = new Socket(SERVERIP, SERVERPORT);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
//               ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

                /*PrintWriter writer = new PrintWriter(objectOutputStream, true);
                writer.println("Message to Fabi");*/
            Log.i("Socket", socket.isConnected()+"");
            objectOutputStream.writeUTF("hallo");
            objectOutputStream.flush();

            objectOutputStream.writeUTF("hallo2");
            objectOutputStream.flush();
            Log.i("Socket", socket.isConnected()+"");

            Log.i("CONNECTION", "works!");
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
