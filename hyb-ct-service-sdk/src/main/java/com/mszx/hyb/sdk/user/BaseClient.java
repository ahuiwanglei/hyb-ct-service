package com.mszx.hyb.sdk.user;

import com.mszx.hyb.sdk.net.HybCtHttpRequest;

//import okhttp3.OkHttpClient;


public abstract class BaseClient {

    static HybCtHttpRequest request = new HybCtHttpRequest();
//    static OkHttpClient okHttpClient;

    public BaseClient(){
//        okHttpClient = new OkHttpClient.Builder().
//                readTimeout(30, TimeUnit.SECONDS).
//                retryOnConnectionFailure(true).
//                build();
//        request.setOkHttpClizent(okHttpClient);
    }

    public abstract String host();
}
