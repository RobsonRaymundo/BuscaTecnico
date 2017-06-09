/**
 * Copyright 2015 Google Inc. All Rights Reserved.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.droid.ray.buscatecnico.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.droid.ray.buscatecnico.activitys.Registro;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class RegistrationIntentService extends IntentService {

    private static final String TAG = "RegIntentService";
    private static final String[] TOPICS = {"global"};
    private static String token_to = null;

    public RegistrationIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        try {
            sendRegistrationToServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendRegistrationToServer() throws Exception {










        // Prepare JSON containing the GCM message content. What to send and where to send.
        JSONObject jGcmData = new JSONObject();
        JSONObject jData = new JSONObject();
        jData.put("message", "Teste BuscaTecnico");

        token_to = "fFOsuoeDUoI:APA91bGjyN4b0SRhTPW-AKHA2a3_fAH_n-NJ3BTeG-o5IkbMW-44EqokJkTfc0R5QrQaY4_Gd0u6tE1lMTBUBjv_Va_4SCb9Q79YbJGYDaBUDIclDNPOKYjJtlN2zBy5Fv60TWoJ8t9n"; //BuscaTecnico

        if (token_to == null || token_to.isEmpty()) {
            jGcmData.put("to", "/topics/global"); // para todos que tem o aplicativo
        } else jGcmData.put("to", token_to); // para um aparelho especifico

        // What to send in GCM message.
        jGcmData.put("data", jData);

        // Create connection to send GCM Message request.
        URL url = new URL("https://fcm.googleapis.com/fcm/send"); //fcm
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Authorization", "key=" + Registro.API_KEY);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);

        // Send GCM message content.
        OutputStream outputStream = conn.getOutputStream();
        outputStream.write(jGcmData.toString().getBytes());

        // Read GCM response.
        InputStream inputStream = conn.getInputStream();
        String resp = IOUtils.toString(inputStream);
        System.out.println(resp);
        System.out.println("Check your device/emulator for notification or logcat for " +
                "confirmation of the receipt of the GCM message.");

    }



}
