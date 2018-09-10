package com.example.user.beatz_final.youtubetry;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class JsonParser {

    private static  StringBuilder sb;
    public JsonParser(){
        // TODO: Auto-generated constructor stub
    }

//    public JSONObject getJsonFromYoutube(String url, Context context){
//         return "iso-8859-1";
//    }

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
