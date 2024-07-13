package com.example.currencyexchange;

import static com.example.currencyexchange.MainActivity.url;
import static java.sql.Types.NULL;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    public static RetrofitInstance instance;
    CurrencyApiService currencyApiService;
    public  RetrofitInstance()
    {
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
       currencyApiService= retrofit.create(CurrencyApiService.class);
    }
    public static RetrofitInstance getInstance()
    {
        if(instance==null)
        {
            instance=new RetrofitInstance();
        }
        return instance;
    }
}
