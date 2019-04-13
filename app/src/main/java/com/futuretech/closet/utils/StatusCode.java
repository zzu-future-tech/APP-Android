package com.futuretech.closet.utils;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Response;

import static android.support.constraint.Constraints.TAG;

public class StatusCode {

    private String status;

    public StatusCode(Response response) {
        try {
            String json = response.body().string();
            JSONObject jsonObject = null;
            jsonObject = new JSONObject(json);
            status = jsonObject.getString("status");
            Log.d(TAG, "json: " + json);
            Log.d(TAG, "StatusCode: " + status);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getStatus() {
        return status;
    }
}
