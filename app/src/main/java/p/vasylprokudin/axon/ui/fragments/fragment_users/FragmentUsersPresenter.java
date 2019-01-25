package p.vasylprokudin.axon.ui.fragments.fragment_users;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import butterknife.BindString;
import p.vasylprokudin.axon.R;
import p.vasylprokudin.axon.data.network.RandomApiServiceBuilder;
import p.vasylprokudin.axon.data.network.RandomUserApiService;
import p.vasylprokudin.axon.data.network.model.RandomUserServiceResponse;
import p.vasylprokudin.axon.data.network.model.Results;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@InjectViewState
public class FragmentUsersPresenter extends MvpPresenter<FragmentUsersView> {

    @BindString(R.string.seed)
    String seed;

    private RandomUserApiService userApiService;

    public static int FIRST_LOAD = 0;
    public static int LOAD_MORE = 1;
    private static int PAGE_SIZE = 20;

    //Variables for pagination
    private int previousTotal = 0;
    private boolean isLoading = true;
    private int page_number = 1;

    public FragmentUsersPresenter() { onLoadDataFromApi(FIRST_LOAD);
    }

    public void onAttachToView() {
        getViewState().initViews();
        getViewState().setupToolbar();
    }

    public void onLoadDataFromApi(final int type) {
        if (type == FIRST_LOAD){
            userApiService = RandomApiServiceBuilder.create();
        }

        int page_size = PAGE_SIZE;
        Call<RandomUserServiceResponse> call = userApiService.getRandomUserResults(page_number, page_size, seed);
        call.enqueue(new Callback<RandomUserServiceResponse>() {
            @Override
            public void onResponse(Call<RandomUserServiceResponse> call, Response<RandomUserServiceResponse> response) {
                if (response.isSuccessful()){
                    try {
                        List<Results> resultsList = response.body().getResults();
                        if (type == FIRST_LOAD){
                            getViewState().showUsersList(resultsList);
                        }
                        if (type == LOAD_MORE){
                            getViewState().loadMore(resultsList);
                        }
                    }catch (NullPointerException e){}
                }
                else {

                }
                if (type == LOAD_MORE){
                    getViewState().hideProgressBar();
                }
            }

            @Override
            public void onFailure(Call<RandomUserServiceResponse> call, Throwable t) {

            }
        });
    }

    public void onClickShowFragmentUserDetails(Results result) {
        getViewState().showFragmentUserDetails(result);
    }

    public void onScroll(int dy, int visibleItemCount, int totalItemCount, int pastVisibleItems) {
        //user scrolls up
        if (dy>0){
            if (isLoading){
                if (totalItemCount > previousTotal){
                    isLoading = false;
                    previousTotal = totalItemCount;
                }
            }
            int viewThreshold = PAGE_SIZE;
            if (!isLoading && (totalItemCount-visibleItemCount)<=
                    pastVisibleItems+ viewThreshold){
                page_number++;
                getViewState().showProgressBar();
                isLoading = true;
            }
        }
    }
}
