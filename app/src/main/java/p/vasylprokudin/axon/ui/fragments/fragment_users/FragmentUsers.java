package p.vasylprokudin.axon.ui.fragments.fragment_users;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.airbnb.lottie.LottieAnimationView;
import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;
import java.util.Objects;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import p.vasylprokudin.axon.R;
import p.vasylprokudin.axon.data.network.model.Results;
import p.vasylprokudin.axon.ui.MainActivity;
import p.vasylprokudin.axon.ui.fragments.fragment_user_details.FragmentUserDetails;

public class FragmentUsers extends MvpAppCompatFragment implements FragmentUsersView, AdapterUsers.OnItemClicked {

    @InjectPresenter
    FragmentUsersPresenter mPresenter;
    @BindView(R.id.recyclerViewUsers)
    RecyclerView recyclerView;
    Unbinder unbinder;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindString(R.string.random_users)
    String random_users;
    @BindView(R.id.lottie_loading)
    LottieAnimationView lottieLoading;

    private LinearLayoutManager layoutManager;
    private AdapterUsers adapter;
    private FragmentManager fragmentManager;
    //Variables for pagination
    private int pastVisibleItems, visibleItemCount, totalItemCount;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_users, container, false);
        mPresenter.onAttachToView();
        unbinder = ButterKnife.bind(this, mView);
        return mView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void initViews() {
        fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public void showUsersList(List<Results> usersList) {
        adapter = new AdapterUsers(usersList, this, getActivity());
        recyclerView.setAdapter(adapter);
        hideProgressBar();
        hideLottieAnimation();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visibleItemCount = layoutManager.getChildCount();
                totalItemCount = layoutManager.getItemCount();
                pastVisibleItems = layoutManager.findFirstVisibleItemPosition();
                mPresenter.onScroll(dy, visibleItemCount, totalItemCount, pastVisibleItems);
            }
        });
    }

    private void hideLottieAnimation() {
        if (lottieLoading.isAnimating()){
            lottieLoading.cancelAnimation();
        }
        lottieLoading.setVisibility(View.GONE);
    }

    @Override
    public void onItemClick(Results result) {
        mPresenter.onClickShowFragmentUserDetails(result);
    }

    @Override
    public void showFragmentUserDetails(Results result) {
        FragmentUserDetails fragment_user_details = new FragmentUserDetails();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.setCustomAnimations(R.anim.slide_left_enter, R.anim.slide_left_exit, R.anim.slide_right_enter, R.anim.slide_right_exit);
        Bundle bundle = new Bundle();
        bundle.putParcelable("user_details", result);
        fragment_user_details.setArguments(bundle);
        ft.replace(R.id.screen_area, fragment_user_details);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        mPresenter.onLoadDataFromApi(FragmentUsersPresenter.LOAD_MORE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void loadMore(List<Results> resultsList) {
        adapter.loadMoreData(resultsList);
    }

    @Override
    public void setupToolbar() {
        LinearLayout ll = (LinearLayout) getActivity().findViewById(R.id.linearlayouttoolbar);
        ll.removeAllViews(); // remove previous view, add 2nd layout
        View toolbar_view = LayoutInflater.from(getActivity()).inflate(R.layout.toolbar_main, ll, false);
        ll.addView(toolbar_view);
        Toolbar toolbar = (Toolbar) toolbar_view.findViewById(R.id.toolbar);
        toolbar.setTitle(random_users);
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
    }

}
