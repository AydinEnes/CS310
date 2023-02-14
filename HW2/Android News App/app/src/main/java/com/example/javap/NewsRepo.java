package com.example.javap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

public class NewsRepo {
    public void getNews(ExecutorService srv, Handler uiHandler, int id) {
        srv.execute(() -> {

                    List<NewsModel> newsList = new ArrayList<>();

                    try {
                        URL url = new URL("http://10.3.0.14:8080/newsapp/getbycategoryid/" + id);
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("GET");

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
                                NewsModel currentNew = new NewsModel(current.getInt("id"),
                                        current.getString("title"),
                                        current.getString("text"),
                                        current.getString("categoryName"),
                                        current.getString("image"),
                                        current.getString("date"));

                                newsList.add(currentNew);
                            }

                            // Send message to the handler with the new data
                            Message message = uiHandler.obtainMessage();
                            message.obj = newsList;
                            uiHandler.sendMessage(message);
                        } else {
                            Log.d("get","REQUEST PATLADI");
                            // Handle error case
                        }
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
        );
    }

    public void getNewsById(ExecutorService srv, Handler uiHandler, int id) {
        srv.execute(() -> {

                    try {
                        URL url = new URL("http://10.3.0.14:8080/newsapp/getnewsbyid/" + id);
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("GET");

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

                            JSONObject current = jsonArr.getJSONObject(0);
                            NewsModel currentNew = new NewsModel(current.getInt("id"),
                                    current.getString("title"),
                                    current.getString("text"),
                                    current.getString("categoryName"),
                                    current.getString("image"),
                                    current.getString("date"));

                            Message message = uiHandler.obtainMessage();
                            message.obj = currentNew;
                            uiHandler.sendMessage(message);
                        } else {
                            Log.d("get","REQUEST PATLADI");
                            // Handle error case
                        }
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
        );
    }

    public void downloadImage(ExecutorService srv, Handler uiHandler,String path){
        srv.execute(()->{
            try {
                URL url = new URL(path);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();

                Bitmap bitmap =  BitmapFactory.decodeStream(conn.getInputStream());

                Message msg = new Message();
                msg.obj = bitmap;
                uiHandler.sendMessage(msg);


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        });


    }
}
