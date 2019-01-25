package p.vasylprokudin.axon.ui;

import com.arellomobile.mvp.MvpView;

public interface MainActivityView extends MvpView {
    void showFragmentUsers();
    void initView();
    void setupToolbar();
}
