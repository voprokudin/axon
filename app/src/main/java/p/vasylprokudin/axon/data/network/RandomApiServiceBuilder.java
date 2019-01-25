package p.vasylprokudin.axon.data.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RandomApiServiceBuilder {

    public static final String BASE_URL = "https://randomuser.me/api/";
    public static RandomUserApiService apiService = null;

    public static RandomUserApiService create(){
        if (apiService == null){
            apiService = new Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(RandomUserApiService.class);
        }
        return apiService;
    }
}
