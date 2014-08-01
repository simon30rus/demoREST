package com.example.tektraining21.demorest;

import android.content.Entity;
import android.net.http.AndroidHttpClient;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

/**
 * Created by tektraining21 on 7/21/2014.
 */
public class HttpManager {

    public static String getData (String uri) throws IOException {
//        AndroidHttpClient httpClient = AndroidHttpClient.newInstance("AndroidAgent");
//        HttpGet request = new HttpGet(uri);
//        HttpResponse response;
//        try {
//            response = httpClient.execute(request);
//           return EntityUtils.toString(response.getEntity());
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        } finally {
//            httpClient.close();
//        }

        BufferedReader reader = null;

        try {
            URL url = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            StringBuilder stringBuilder = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line;
            while ((line= reader.readLine()) != null) {
                stringBuilder.append(line + '\n');
            }

            return stringBuilder.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (reader != null){
                reader.close();
            }
        }


    }

}
