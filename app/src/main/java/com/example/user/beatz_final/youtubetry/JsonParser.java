package com.example.user.beatz_final.youtubetry;

import android.util.Log;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.zip.GZIPInputStream;

public class JsonParser {
    public String local="https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=PLVrWVCMVUP2wLcVW4IT4JwD-5XyY2Hu_w&key=AIzaSyBy32LH5Qt6ucVtMMfLDxLgD81aB89N210";

    private static  StringBuilder sb;
    private JSONObject jObj;
    public JsonParser(){
        // TODO: Auto-generated constructor stub
    }

    public JSONObject getJsonFromYoutube(String url){
        DefaultHttpClient httpclient = new DefaultHttpClient();

        Log.e("local", local);

        HttpGet getRequest = new HttpGet(url);
        getRequest.setHeader("Accept", "application/json");

        getRequest.setHeader("Accept-Encoding", "gzip");
        try {
            HttpResponse response = (HttpResponse) httpclient.execute(getRequest);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instream = entity.getContent();
                Header contentEncoding = response.getFirstHeader("Content-Encoding");
                if (contentEncoding != null && contentEncoding.getValue().equalsIgnoreCase("gzip")) {
                    instream = new GZIPInputStream(instream);
                }

                // convert content stream to a string
                String result = readStream(instream);
                Log.i("JSON", result);
                instream.close();
                jObj = new JSONObject(result);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return jObj;

        }

        private static String readStream(InputStream is){
            BufferedReader reader;
            try {
                reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"),8);
                sb = new StringBuilder();
                String line = null;
                try{
                    while((line = reader.readLine()) != null){
                        sb.append(line +"\n");
                    }
                }catch (IOException e){
                    e.printStackTrace();
                } finally {
                    try{
                        is.close();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }catch (UnsupportedEncodingException e1){
                // TO-DO Auto-generated catch block
                e1.printStackTrace();
            }
            return sb.toString();
        }
    }
