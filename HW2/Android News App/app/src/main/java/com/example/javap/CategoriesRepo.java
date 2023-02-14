package com.example.javap;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class CategoriesRepo {
    public void getCategories(ExecutorService srv, Handler uiHandler) {
        srv.execute(() -> {

            List<CategoryModel> categoryList = new ArrayList<>();

            try {
                URL url = new URL("http://10.3.0.14:8080/newsapp/getallnewscategories");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line;
                    StringBuilder builder = new StringBuilder();
                    while ((line = reader.readLine()) != null) {
                        builder.append(line);
                    }
                    reader.close();

                    JSONObject json = new JSONObject(builder.toString());
                    JSONArray jsonArr = json.getJSONArray("items");

                    for (int i = 0; i < jsonArr.length(); i++) {

                        JSONObject current = jsonArr.getJSONObject(i);
                        CategoryModel catObj = new CategoryModel(current.getInt("id"),
                                current.getString("name"));

                        categoryList.add(catObj);
                    }

                    connection.disconnect();

                    Message message = uiHandler.obtainMessage();
                    message.obj = categoryList;
                    uiHandler.sendMessage(message);
                } else {
                    Log.d("get","REQUEST PATLADI");
                }
            }  catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        });
    }
}
