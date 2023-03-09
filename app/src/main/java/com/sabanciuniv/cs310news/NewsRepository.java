package com.sabanciuniv.cs310news;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class NewsRepository {

    public void getNewsByCategory(ExecutorService srv, Handler uiHandler, int id){
        srv.execute(()->{
            try{
                URL url = new URL("http://10.3.0.14:8080/newsapp/getbycategoryid/" + String.valueOf(id));
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder buffer = new StringBuilder();
                String line = "";

                while((line = reader.readLine()) != null){
                    buffer.append(line);
                }

                JSONObject root = new JSONObject(buffer.toString());
                JSONArray arr = root.getJSONArray("items");
                List<News> data = new ArrayList<>();

                for (int i = 0; i < arr.length(); i++){
                    JSONObject current = arr.getJSONObject(i);

                    News news = new News(current.getInt("id"), current.getString("title"), current.getString("text"),
                            current.getString("date"), current.getString("image"), current.getString("categoryName"));

                    data.add(news);
                }

                Message msg = new Message();
                msg.obj = data;
                uiHandler.sendMessage(msg);

            }
            catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    } //GetNewsByCategory

    public void downloadImage(ExecutorService srv, Handler uiHandler, String path){
        srv.execute(()->{
            try{
                URL url = new URL(path);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                Bitmap bitmap = BitmapFactory.decodeStream(conn.getInputStream());

                Message msg = new Message();
                msg.obj = bitmap;
                uiHandler.sendMessage(msg);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }//download image


    public void getNewsById(ExecutorService srv, Handler uiHandler, int id){
        srv.execute(()->{
            try{
                URL url = new URL("http://10.3.0.14:8080/newsapp/getnewsbyid/" + String.valueOf(id));
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder buffer = new StringBuilder();
                String line = "";

                while((line = reader.readLine()) != null){
                    buffer.append(line);
                }

                JSONObject root = new JSONObject(buffer.toString());
                JSONArray arr = root.getJSONArray("items");
                JSONObject current = arr.getJSONObject(0);

                News news = new News(current.getInt("id"), current.getString("title"), current.getString("text"),
                        current.getString("date"), current.getString("image"), current.getString("categoryName"));



                Message msg = new Message();
                msg.obj = news;
                uiHandler.sendMessage(msg);

            }
            catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    public void getCommentsByNewsId(ExecutorService srv, Handler uiHandler, int id){
        srv.execute(()->{
            try{
                URL url = new URL("http://10.3.0.14:8080/newsapp/getcommentsbynewsid/" + String.valueOf(id));
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder buffer = new StringBuilder();
                String line = "";

                while((line = reader.readLine()) != null){
                    buffer.append(line);
                }

                JSONObject root = new JSONObject(buffer.toString());
                JSONArray arr = root.getJSONArray("items");
                List<NewsComment> data = new ArrayList<>();

                for (int i = 0; i < arr.length(); i++){
                    JSONObject current = arr.getJSONObject(i);

                    NewsComment newsComments = new NewsComment(current.getInt("id"),current.getInt("news_id"),
                            current.getString("text"),current.getString("name"));

                    data.add(newsComments);
                }

                Message msg = new Message();
                msg.obj = data;
                uiHandler.sendMessage(msg);

            }
            catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }


    public void postComment(ExecutorService srv, Handler uiHandler, String name, String comment, String newsid){
        srv.execute(()->{

            try {
                URL url = new URL("http://10.3.0.14:8080/newsapp/savecomment");

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setDoInput(true);
                conn.setDoOutput(true);

                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type","application/JSON");


                JSONObject outputData  = new JSONObject();

                outputData.put("name",name);
                outputData.put("text",comment);
                outputData.put("news_id",newsid);

                BufferedOutputStream writer =
                        new BufferedOutputStream(conn.getOutputStream());


                writer.write(outputData.toString().getBytes(StandardCharsets.UTF_8));
                writer.flush();

                BufferedReader reader
                        = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder buffer = new StringBuilder();

                String line ="";

                while((line=reader.readLine())!=null){
                    buffer.append(line);
                }

                conn.disconnect();

                Message msg = new Message();
                msg.obj = name + " " + comment + " " + newsid;

                uiHandler.sendMessage(msg);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


        });
    }



}
