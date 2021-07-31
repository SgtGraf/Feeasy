package com.example.feeasy.Threads;

import android.os.Build;
import android.os.Message;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.feeasy.dataManagement.AppDataManager;
import com.example.feeasy.dataManagement.DataManager;
import com.example.feeasy.entities.ActionNames;
import com.example.feeasy.entities.Fee;
import com.example.feeasy.entities.FeePreset;
import com.example.feeasy.entities.Group;
import com.example.feeasy.entities.GroupMember;
import com.example.feeasy.entities.LoggedInUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Vector;

public class Connection {

    String URLString = "https://feeazy-server.herokuapp.com/";

    public void handleAction(ActionNames action, JSONObject jsonObject){
        ActionThreads thread = new ActionThreads(action, jsonObject);
        new Thread(thread).start();
    }

    class ActionThreads implements Runnable{

        ActionNames action;
        JSONObject jsonObject;
        String response;

        public ActionThreads(ActionNames newAction, JSONObject jsonObject){
            action = newAction;
            this.jsonObject = jsonObject;
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void run() {
            String requestBody = jsonObject.toString();
            try {
                switch (action) {
                    case SIGN_UP:
                        if(isValidResponse(executePOSTRequest("register", requestBody))){
                            signUp(response);
                        }
                        break;

                    case SIGN_IN:
                        if(isValidResponse(executePOSTRequest("login", requestBody))){
                            signIn(response);
                        }
                        break;

                    case CREATE_FEE:
                        if(isValidResponse(executePOSTRequest("fee", requestBody))){
                            addFee(response);
                        }
                        break;

                    case SAVE_PRESET:
                        if(isValidResponse(executePOSTRequest("preset", requestBody))){
                            savePreset(response);
                        }
                        break;

                    case CREATE_GROUP:
                        if(isValidResponse(executePOSTRequest("group", requestBody))){
                            loadGroup(response);
                        }
                        break;
                    case ADD_TO_GROUP:
                        if(isValidResponse(executePOSTRequest("invite", requestBody))){
                            loadMember(response);
                        }
                        break;

                    case SET_FEE_STATUS:
                        if(isValidResponse(executePUTRequest("fee", requestBody))){
                            updateFeeStatus(response);
                        }
                        break;

                    case ALL_GROUPS:
                        if(isValidResponse(executeGETRequest("group"))){
                            loadGroups(response);
                        }
                        break;

                    case ALL_MEMBERS:
                        if(isValidResponse(executeGETRequest("user?group_id=" + jsonObject.getInt("group_id")))){
                            loadMembers(response);
                        }
                        break;

                    case ALL_FEES_OF_GROUP:
                        if(isValidResponse(executeGETRequest("fee?group_id=" + jsonObject.getInt("group_id")))){
                            loadFees(response);
                        }
                        break;
                    case ALL_PRESETS_OF_GROUP:
                        if(isValidResponse(executeGETRequest("preset?group_id=" + jsonObject.getInt("group_id")))){
                            loadPresets(response);
                        }
                        break;
                    case FEES_OF_USER_IN_GROUP:
                        if(isValidResponse(executeGETRequest("fee?group_id=" + jsonObject.getInt("group_id") + "&user_id=" + jsonObject.getInt("user_id")))){
                            loadFeesOfUser(response);
                        }
                        break;

                    case UPDATE_MEMBER:
                        if(isValidResponse(executePUTRequest("user", requestBody))){
                            updateUser(jsonObject.getString("name"));
                        }
                    default:
                        Log.wtf("error:", "How did you even get here?");
                }

            }catch (IOException | JSONException e) {
                notifyHandler(action,-1);
                e.printStackTrace();
            }
        }

        private boolean isValidResponse(int responseCode){
            return responseCode >= 200 && responseCode <= 299;
        }

        private void notifyHandler(ActionNames action, int status){
            Message msg = Message.obtain();
            msg.obj = action;
            msg.arg1 = status;
            AppDataManager.getAppDataManager().getCurrentHandler().dispatchMessage(msg);
        }

        // REQUEST HANDLING
        //------------------------------------------------------------------------------------------

        private int executeGETRequest(String route) throws IOException, JSONException {
            URL url = new URL(URLString + route);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setRequestProperty("Accept", "application/json");
            if (DataManager.getDataManager().getLoggedInUser() != null){
                connection.setRequestProperty("Authorization", DataManager.getDataManager().getLoggedInUser().token);
            }

            return handleResult(connection);
        }

        private int executePOSTRequest(String route, String json) throws IOException, JSONException {
            URL url = new URL(URLString + route);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setRequestProperty("Accept", "application/json");
            if (DataManager.getDataManager().getLoggedInUser() != null){
                connection.setRequestProperty("Authorization", DataManager.getDataManager().getLoggedInUser().token);
            }
            connection.setDoOutput(true);

            sendData(connection,json);
            return handleResult(connection);
        }

        private int executePUTRequest(String route, String json) throws IOException, JSONException {
            URL url = new URL(URLString + route);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setRequestProperty("Accept", "application/json");
            if (DataManager.getDataManager().getLoggedInUser() != null){
                connection.setRequestProperty("Authorization", DataManager.getDataManager().getLoggedInUser().token);
            }

            sendData(connection,json);
            return handleResult(connection);
        }

        private int executeDELETERequest(String route, String json) throws IOException, JSONException {
            URL url = new URL(URLString + route);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setRequestProperty("Accept", "application/json");
            if (DataManager.getDataManager().getLoggedInUser() != null){
                connection.setRequestProperty("Authorization", DataManager.getDataManager().getLoggedInUser().token);
            }

            sendData(connection,json);
            return handleResult(connection);
        }

        private void sendData(HttpURLConnection connection, String json) throws IOException{
            OutputStream os = connection.getOutputStream();
            byte[] input = json.getBytes();
            os.write(input, 0, input.length);
            os.flush();
        }

        private int handleResult(HttpURLConnection connection) throws IOException,JSONException{
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            Log.i("RESPONSE", response.toString());
            this.response = response.toString();
            return connection.getResponseCode();
        }

        // RESPONSE HANDLING (OK)
        //------------------------------------------------------------------------------------------

        private void signUp(String response) throws JSONException {
            JSONObject responseObject = new JSONObject(response);
            DataManager.getDataManager().setLoggedInUser(null);
            DataManager.getDataManager().setLoggedInUser(new LoggedInUser(responseObject.getInt("id"), responseObject.getString("name"), responseObject.getString("token")));
            notifyHandler(action,0);
        }

        private void signIn(String response) throws JSONException {
            JSONObject responseObject = new JSONObject(response);
            DataManager.getDataManager().setLoggedInUser(null);
            DataManager.getDataManager().setLoggedInUser(new LoggedInUser(responseObject.getInt("id"), responseObject.getString("name"), responseObject.getString("token")));
            notifyHandler(action,0);
        }

        private void loadGroups(String response) throws JSONException{
            JSONArray responseArray = new JSONArray(response);
            DataManager.getDataManager().loadGroupList(buildGroupListFromResponse(responseArray));
            notifyHandler(action,0);
        }

        private void loadGroup(String response)throws JSONException{
            JSONObject responseObject = new JSONObject(response);
            DataManager.getDataManager().addGroupToGroupList(new Group(responseObject.getInt("id"),responseObject.getString("name")));
            notifyHandler(action,0);
        }

        private void loadMembers(String response)throws JSONException{
            JSONArray responseArray = new JSONArray(response);
            DataManager.getDataManager().loadMembers(jsonObject.getInt("group_id"),buildMemberListFromResponse(responseArray));
            notifyHandler(action,0);
        }

        private void loadMember(String response)throws  JSONException{
            JSONObject responseObject = new JSONObject(response);
            DataManager.getDataManager().addMemberToGroup(jsonObject.getInt("group_id"),new GroupMember(responseObject.getString("name"),false,responseObject.getInt("id")));
            notifyHandler(action,0);
        }

        private void loadFees(String response)throws JSONException{
            JSONArray responseArray = new JSONArray(response);
            DataManager.getDataManager().loadFees(jsonObject.getInt("group_id"), buildFeeListFromResponse(responseArray));
            notifyHandler(action,0);
        }

        private void addFee(String response) throws JSONException{
            // No immediate data transfer needed
            notifyHandler(action,0);
        }

        private void updateFeeStatus(String response) throws JSONException{
            // Data transfer in seperate call in Handler
            notifyHandler(action,0);
        }

        private void savePreset(String response) throws JSONException{
            // Data transfer in seperate call in Handler
            notifyHandler(action,0);
        }

        private void loadPresets(String response) throws JSONException{
            JSONArray responseArray = new JSONArray(response);
            DataManager.getDataManager().loadPresets(jsonObject.getInt("group_id"), buildPresetListFromResponse(responseArray));
            notifyHandler(action,0);
        }

        private void loadFeesOfUser(String response) throws JSONException {
            JSONArray responseArray = new JSONArray(response);
            DataManager.getDataManager().addFeesToMember(jsonObject.getInt("group_id"), jsonObject.getInt("user_id"), buildFeeListFromResponse(responseArray));
            notifyHandler(action, 0);
        }

        public void updateUser(String displayname) throws JSONException{

            DataManager.getDataManager().setDisplayName(displayname);

        }

        // OBJECT BUILDER (RESPONSE)
        //------------------------------------------------------------------------------------------

        private List<Group> buildGroupListFromResponse(JSONArray array) throws JSONException{
            Vector<Group> groups = new Vector<>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                groups.add(new Group(object.getInt("id"), object.getString("name")));
            }
            return groups;
        }

        private List<GroupMember> buildMemberListFromResponse(JSONArray array)throws JSONException{
            Vector<GroupMember> members = new Vector<>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                members.add(new GroupMember(object.getString("name"),false,object.getInt("id")));
            }
            return members;
        }

        private List<Fee> buildFeeListFromResponse(JSONArray array) throws JSONException{
            Vector<Fee> fees = new Vector<>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                fees.add(new Fee(object.getInt("id"), object.getString("name"),
                        DataManager.getDataManager().getGroup(jsonObject.getInt("group_id")),
                        DataManager.getDataManager().getMemberOfGroup(jsonObject.getInt("group_id"),object.getInt("user_id")),
                        (float)object.getDouble("amount"), object.getString("status")));
            }
            return fees;
        }

        private List<FeePreset> buildPresetListFromResponse(JSONArray array) throws JSONException{
            Vector<FeePreset> presets = new Vector<>();
            for (int i = 0; i < array.length(); i++){
                JSONObject object = array.getJSONObject(i);
                presets.add(new FeePreset(object.getString("name"),DataManager.getDataManager().getGroup(jsonObject.getInt("group_id")), (float)object.getDouble("amount")));
            }
            return presets;
        }
    }
}
