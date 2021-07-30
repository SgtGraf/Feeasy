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
import java.net.MalformedURLException;
import java.net.URL;

public class Connection {

    String URLstring = "https://feeazy-server.herokuapp.com/";
    int statusCode = -1;
    JSONObject responseObject;


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
                        if(isValidResponse(doRequest("register", "POST", msg))){
                            signUp(responseObject);
                        }
                        break;

                    case SIGN_IN:
                        if(isValidResponse(doRequest("login", "POST", msg))){
                            signIn(responseObject);
                        }
                        break;

                    case CREATE_FEE:
                        if(isValidResponse(doRequest("fee", "POST", msg))){

                        }
                        break;

                    case SAVE_PRESET:
                        if(isValidResponse(doRequest("preset", "POST", msg))){

                        }
                        break;

                    case CREATE_GROUP:
                        if(isValidResponse(doRequest("group", "POST", msg))){

                        }
                        break;
                    case ADD_TO_GROUP:
                        if(isValidResponse(doRequest("group", "PUT", msg))){

                        }
                        break;

                    case SET_FEE_STATUS:
                        if(isValidResponse(doRequest("fee", "PUT", msg))){

                        }
                        break;

                    case ALL_GROUPS:
                        if(isValidResponse(doRequest("group", "GET", msg))){

                        }
                        break;

                    case ALL_MEMBERS:
                        if(isValidResponse(doRequest("user", "GET", msg))){

                        }
                        break;

                    case ALL_FEES_OF_GROUP:
                        if(isValidResponse(doRequest("fee", "GET", msg))){
                            signIn(responseObject);
                        }
                        break;

                    default:
                        Log.wtf("error:", "How did you get here?");
                }

            }catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        public int doRequest(String route, String type, String json) throws IOException, JSONException {

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

            int responseCode = connection.getResponseCode();
            String responseString;
            try(BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                Log.i("RESPONSE", response.toString());
                responseString = response.toString();
            }
            responseObject = new JSONObject(responseString);

            return responseCode;
        }

        // outdated
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

    public boolean isValidResponse(int responseCode){
        return responseCode >= 200 && responseCode <= 299;
    }

}
