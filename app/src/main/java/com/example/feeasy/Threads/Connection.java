package com.example.feeasy.Threads;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.feeasy.dataManagement.CurrentUser;
import com.example.feeasy.entities.ActionNames;
import com.example.feeasy.entities.LoggedInUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

import java.net.HttpURLConnection;
import java.net.URL;

public class Connection {

    String URLstring = "https://feeazy-server.herokuapp.com/";
    int statusCode = -1;


    public void handleAction(ActionNames action, JSONObject jsonObject){
        ActionThreads thread = new ActionThreads(action, jsonObject);
        new Thread(thread).start();
    }

    public void signUp(JSONObject jsonObject) throws JSONException {
        CurrentUser.setLoggedInUser(null);
        CurrentUser.setLoggedInUser(new LoggedInUser(jsonObject.getInt("id"), jsonObject.getString("name"), jsonObject.getString("token")));
    }

    public void signIn(JSONObject jsonObject) throws JSONException {
        CurrentUser.setLoggedInUser(null);
        Log.i("JSON", jsonObject.getString("token"));
        if(!jsonObject.getString("token").isEmpty()){
            CurrentUser.setLoggedInUser(new LoggedInUser(jsonObject.getInt("id"), "NAME", jsonObject.getString("token")));
        }
    }

    class ActionThreads implements Runnable{
        ActionNames action;
        JSONObject jsonObject;
        public ActionThreads(ActionNames newAction, JSONObject jsonObject){
            action = newAction;
            this.jsonObject = jsonObject;
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void run() {

            String msg = jsonObject.toString();
            JSONObject response = null;
            try {
                switch (action) {
                    case SIGN_UP:
                        Log.i("JSON", jsonObject.toString());
                        response = request("register", "POST", msg);
                        signUp(response);
                        break;

                    case SIGN_IN:
                        Log.i("JSON", jsonObject.toString());
                        response = request("login", "POST", msg);
                        signIn(response);
                        break;

                    case CREATE_FEE:
                        Log.i("JSON", jsonObject.toString());
                        request("fee", "POST", msg);
                        break;

                    case SAVE_PRESET:
                        Log.i("JSON", jsonObject.toString());
                        request("preset", "POST", msg);
                        break;

                    case CREATE_GROUP:
                        Log.i("JSON", jsonObject.toString());
                        request("group", "POST", msg);
                        break;
                    case ADD_TO_GROUP:
                        Log.i("JSON", jsonObject.toString());
                        request("group", "PUT", msg);
                        break;

                    case SET_FEE_STATUS:
                        Log.i("JSON", jsonObject.toString());
                        request("fee", "PUT", msg);
                        break;
                }

            }catch (IOException | JSONException e) {
                e.printStackTrace();
            }

        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        public JSONObject request(String route, String type, String json) throws IOException, JSONException {
            String responseString;

            URL url = new URL(URLstring + route + "/");
            Log.i("URL: ", url.toString());
            Log.i("Type: ", type);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(type);
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setRequestProperty("Accept", "application/json");
            if (action != ActionNames.SIGN_UP && action!= ActionNames.SIGN_IN){
                connection.setRequestProperty("Authorization", CurrentUser.getLoggedInUser().token);
            }
            connection.setDoOutput(true);


            try(OutputStream os = connection.getOutputStream()) {
                byte[] input = json.getBytes();
                os.write(input, 0, input.length);
                os.flush();
            }
            //Log.i("ResponseCode2",  Integer.toString(connection.getResponseCode()));
            Log.i("ResponseMsg:", connection.getResponseMessage());
            Log.i("ResponseCode:", Integer.toString(connection.getResponseCode()));
            try(BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                Log.i("RESPONSE", response.toString());
                responseString = response.toString();
            }
            return new JSONObject(responseString);
        }
    }

}
