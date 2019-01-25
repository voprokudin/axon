package p.vasylprokudin.axon.ui.fragments.fragment_user_details;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.bumptech.glide.Glide;

import java.util.Objects;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import p.vasylprokudin.axon.R;
import p.vasylprokudin.axon.data.network.model.Results;
import p.vasylprokudin.axon.ui.MainActivity;

public class FragmentUserDetails extends MvpAppCompatFragment implements FragmentUserDetailsView {

    @InjectPresenter
    FragmentUserDetailsPresenter mPresenter;

    Unbinder unbinder;
    @BindView(R.id.civUserImage)
    CircleImageView civUserImage;
    @BindView(R.id.tvUserDetailsFullName)
    TextView tvUserDetailsFullName;
    @BindView(R.id.tvUserDetailsGender)
    TextView tvUserDetailsGender;
    @BindView(R.id.tvUserDetailsAge)
    TextView tvUserDetailsAge;
    @BindView(R.id.tvUserDetailsBirthDate)
    TextView tvUserDetailsBirthDate;
    @BindView(R.id.editTextNickName)
    EditText editTextNickname;
    @BindView(R.id.editTextPhone)
    EditText editTextPhone;
    @BindView(R.id.editEmail)
    EditText editEmail;
    @BindView(R.id.editTextCity)
    EditText editTextCity;
    @BindString(R.string.years_old)
    String old;
    @BindString(R.string.profile)
    String profile;
    @BindString(R.string.registeredFrom)
    String registeredFrom;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.tvUserDetailsRegisteredDate)
    TextView tvUserDetailsRegisteredDate;

    private FragmentManager fragmentManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_user_details, container, false);
        Results user_details = getArguments().getParcelable("user_details");
        mPresenter.onAttachToView(user_details);

        unbinder = ButterKnife.bind(this, mView);
        return mView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void initView() {
        fragmentManager = getActivity().getSupportFragmentManager();
    }

    @Override
    public void setupToolbar() {
        LinearLayout ll = (LinearLayout) getActivity().findViewById(R.id.linearlayouttoolbar);
        ll.removeAllViews(); // remove previous view, add 2nd layout
        View toolbar_view = LayoutInflater.from(getActivity()).inflate(R.layout.toolbar_title_center, ll, false);
        ll.addView(toolbar_view);

        ImageView ivBack = (ImageView) toolbar_view.findViewById(R.id.ivToolbarBack);
        TextView tvToolbarTitleCenter = (TextView) toolbar_view.findViewById(R.id.tvToolbarTitleCenter);
        Toolbar toolbar = (Toolbar) toolbar_view.findViewById(R.id.toolbarTitleCenter);

        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
        tvToolbarTitleCenter.setText(profile);
        View.OnClickListener listenerBack = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.popBackStack();
            }
        };
        ivBack.setOnClickListener(listenerBack);
    }

    @Override
    public void setData(Results results, String birthDate, String registeredDate) {
        String avatar = results.getPicture().getLarge();
        String firstname = results.getName().getFirst();
        String lastname = results.getName().getLast();
        String gender = results.getGender();
        int age = results.getDob().getAge();

        String nickname = results.getLogin().getUsername();
        String phone = results.getCell();
        String email = results.getEmail();
        String city = results.getLocation().getCity();

        Glide.with(Objects.requireNonNull(getActivity())).load(avatar).into(civUserImage);
        tvUserDetailsFullName.setText(String.format("%s %s", firstname, lastname));
        tvUserDetailsGender.setText(gender);
        tvUserDetailsAge.setText(String.format("%s %s", String.valueOf(age), old));
        tvUserDetailsBirthDate.setText(birthDate);
        tvUserDetailsRegisteredDate.setText(String.format("%s %s", registeredFrom, registeredDate));
        editTextNickname.setText(nickname);
        editTextPhone.setText(phone);
        editEmail.setText(email);
        editTextCity.setText(city);
    }

    @Override
    public void startCall(Intent surf) {
        startActivity(surf);
    }

    @OnClick(R.id.fab)
    public void onViewClicked() {
        String phone = editTextPhone.getText().toString();
        mPresenter.callUser(phone);
    }
}
