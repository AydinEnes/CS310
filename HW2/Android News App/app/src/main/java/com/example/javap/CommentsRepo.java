package com.example.javap;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class CommentsRepo {
    public void getCommentById(ExecutorService srv, Handler uiHandler, int id) {
        srv.execute(() -> {
                List<CommentModel> commentList = new ArrayList<>();
                    try {
                        URL url = new URL("http://10.3.0.14:8080/newsapp/getcommentsbynewsid/" + id);
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
                            JSONArray arr = json.getJSONArray("items");

                            for (int i = 0; i < arr.length(); i++) {
                                JSONObject current = arr.getJSONObject(i);
                                CommentModel commentObj = new CommentModel(current.getInt("id"),
                                        current.getInt("news_id"), current.getString("name"),
                                        current.getString("text")
                                );

                                commentList.add(commentObj);

                            }

                            Message message = uiHandler.obtainMessage();
                            message.obj = commentList;
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

    public void postComment(ExecutorService srv, Handler uiHandler,String name,
                                 String comment, int newsId){

        srv.execute(()->{

            try {
                URL url = new URL("http://10.3.0.14:8080/newsapp/savecomment");

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.setDoInput(true);

                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type","application/JSON");

                JSONObject reqBody  = new JSONObject();
                reqBody.put("name",name);
                reqBody.put("text",comment);
                reqBody.put("news_id",newsId);

                OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                writer.write(reqBody.toString());
                writer.flush();

                StringBuilder builder = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                String line;

                while((line=reader.readLine()) != null){
                    builder.append(line);
                }

                JSONObject response = new JSONObject(builder.toString());

                connection.disconnect();

                int responseInt = response.getInt("serviceMessageCode");

                Message msg = new Message();
                msg.obj = responseInt;

                uiHandler.sendMessage(msg);

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
