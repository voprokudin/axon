package p.vasylprokudin.axon.ui.fragments.fragment_users;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

import p.vasylprokudin.axon.data.network.model.Results;

public interface FragmentUsersView extends MvpView {

    void initViews();
    void showUsersList(List<Results> usersList);
    void showProgressBar();
    void hideProgressBar();
    void loadMore(List<Results> resultsList);
    void setupToolbar();
    @StateStrategyType(SkipStrategy.class)
    void showFragmentUserDetails(Results result);
}
