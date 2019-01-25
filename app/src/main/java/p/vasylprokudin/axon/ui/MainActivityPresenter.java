package p.vasylprokudin.axon.ui;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

@InjectViewState
public class MainActivityPresenter extends MvpPresenter<MainActivityView> {

    void onAttachToView() {
        getViewState().initView();
        getViewState().setupToolbar();
        getViewState().showFragmentUsers();
    }
}
