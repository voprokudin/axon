package p.vasylprokudin.axon.data.network;

import java.util.List;

import p.vasylprokudin.axon.data.network.model.RandomUserServiceResponse;
import p.vasylprokudin.axon.data.network.model.Results;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RandomUserApiService {
    @GET(".")
    Call<RandomUserServiceResponse> getRandomUserResults(
            @Query("page") int page,
            @Query("results") int size,
            @Query("seed") String seed);
}
