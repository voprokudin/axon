package p.vasylprokudin.axon.ui.fragments.fragment_user_details;

import android.content.Intent;

import com.arellomobile.mvp.MvpView;

import p.vasylprokudin.axon.data.network.model.Results;


public interface FragmentUserDetailsView extends MvpView {

    void initView();
    void setupToolbar();
    void setData(Results results, String birthDate, String registeredDate);
    void startCall(Intent surf);
}
