package com.example.chenglongbao.helloworld.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

import com.example.chenglongbao.helloworld.R;
import com.example.chenglongbao.helloworld.util.CommonUtility;
import com.google.android.gcm.GCMRegistrar;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONObject;

public class SaveRegIdThread extends AsyncTask<String, String, String> {

    private static final Random random = new Random();

    private Context mContext;
    private JSONObject jsonObject;

    public SaveRegIdThread(Context context) {
        this.mContext = context;
    }

    @Override
    protected String doInBackground(String... regId) {
        Log.v(Constants.TAG_THEAD, "doInBackground " + regId[0]);

        String result = "";
        String serverUrl = CommonUtility.getInstance().getServiceUrl();

        try {
            jsonObject = new JSONObject();

            jsonObject.put(Constants.TOKEN, regId[0]);
            jsonObject.put(Constants.DEVICE, "1");
            jsonObject.put(Constants.PUSHTYPE, "1");

        } catch (Exception e) {
            e.printStackTrace();
        }

        publishProgress("Preparation network......");
        publishProgress("Token:" + regId[0]);
        long backoff = Constants.BACKOFF_MILLI_SECONDS + random.nextInt(1000);

        for (int i = 1; i <= Constants.MAX_ATTEMPTS_CONNECTION; i++) {

            Log.v(Constants.TAG_THEAD, "Sleeping for" + backoff + " ms before retry");
            try {
                publishProgress(mContext.getString(R.string.server_registering, i, Constants.MAX_ATTEMPTS_CONNECTION - i));

                publishProgress("On connection.......");
                result = doPost(serverUrl, jsonObject);

                if (Constants.RESULT.equals(result)) {
                    GCMRegistrar.setRegisteredOnServer(mContext, true);
                    publishProgress("Successfully added device!");
                }
            } catch (IOException e) {

                Log.e(Constants.TAG_THEAD, "Failed to register on attempt " + i, e);
                if (i == Constants.MAX_ATTEMPTS_CONNECTION) {
                    publishProgress("Failed to register on attempt.");
                    break;
                }
                try {
                    Log.d(Constants.TAG_THEAD, "Sleeping for " + backoff + " ms before retry");
                    Thread.sleep(backoff);
                } catch (InterruptedException interrupted) {
                    Thread.currentThread().interrupt();
                }
                backoff *= 2;
            }
        }

        return result;
    }

    @Override
    protected void onProgressUpdate(String... values) {

        Log.v(Constants.TAG_THEAD, "onProgressUpdate " + values[0]);
        CommonUtility.getInstance().SendMessageToMessageText(mContext, values[0] + "\n");
    }

    @Override
    protected void onPostExecute(String result) {

        if (result != null && result.equals("")) {
            CommonUtility.getInstance().SendMessageToMessageText(mContext, "You got message!" + "\n");
        }
    }

    /**
     *  サーバ向け、POST請求
     *
     * @param serverUrl POST address.
     * @param params    request parameters.
     * @throws IOException propagated from POST.
     */
    private String doPost(String serverUrl, JSONObject params) throws IOException {
        URL url;
        String result = "";
        try {
            url = new URL(serverUrl);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("invalid url: " + serverUrl);
        }
        byte[] bytes = params.toString().getBytes();

        HttpURLConnection conn = null;
        Log.v(Constants.TAG_THEAD, "Posting '" + bytes.toString() + "' to " + url);
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setFixedLengthStreamingMode(bytes.length);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            OutputStream out = conn.getOutputStream();
            out.write(bytes);
            out.flush();
            out.close();

            BufferedReader ResponseReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder str_ResponseReader = new StringBuilder();

            while ((ResponseReader.readLine()) != null) {
                str_ResponseReader.append(ResponseReader.readLine());
            }
            ResponseReader.close();

            Log.v(Constants.TAG_THEAD, "Response:" + str_ResponseReader.toString());
            result = str_ResponseReader.toString();

            int status = conn.getResponseCode();
            if (status != 200) {

                throw new IOException("request fail ,error is" + status);
            }
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return result;
    }

}
