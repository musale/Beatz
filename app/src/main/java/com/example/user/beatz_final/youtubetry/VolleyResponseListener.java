package com.example.user.beatz_final.youtubetry;

import org.json.JSONObject;

public interface VolleyResponseListener {
    void onError(String message);

    void onResponse(JSONObject response);
}
