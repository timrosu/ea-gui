package com.timrosu.ea_gui.api;

import com.timrosu.ea_gui.api.client.ApiClient;
import com.timrosu.ea_gui.api.service.ApiService;

public class Api {

    public Api() {
        ApiClient.getRetrofit().create(ApiService.class);
    }
}
