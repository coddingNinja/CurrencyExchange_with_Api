package com.example.currencyexchange;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
public interface CurrencyApiService {
    @GET("latest?apikey=fca_live_3OufIm7Z4crlSXImozUY9YphnwslbFBOTi923cP1")
    Call<modelclass> getLatestExchangeRates();
}
