package p.vasylprokudin.axon.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewStub;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import p.vasylprokudin.axon.R;
import p.vasylprokudin.axon.ui.fragments.fragment_users.FragmentUsers;

public class MainActivity extends MvpAppCompatActivity implements MainActivityView {

    @InjectPresenter
    MainActivityPresenter mainActivityPresenter;
    @BindView(R.id.stub_layout)
    ViewStub stubLayout;
    @BindString(R.string.random_users)
    String random_users;

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mainActivityPresenter.onAttachToView();

    }

    @Override
    public void showFragmentUsers() {
        FragmentUsers fragmentUsers = new FragmentUsers();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.screen_area, fragmentUsers);
        ft.commit();
    }

    @Override
    public void initView() {
        fragmentManager = getSupportFragmentManager();
    }

    @Override
    public void setupToolbar() {
        stubLayout.setLayoutResource(R.layout.toolbar_main);
        View inflated = stubLayout.inflate();
        Toolbar toolbar = (Toolbar) inflated.findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.random_users);
        setSupportActionBar(toolbar);
    }
}
