package p.vasylprokudin.axon.ui.fragments.fragment_user_details;

import android.content.Intent;
import android.net.Uri;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import p.vasylprokudin.axon.data.network.model.Results;

@InjectViewState
public class FragmentUserDetailsPresenter extends MvpPresenter<FragmentUserDetailsView> {

    private String birthDate, registeredDate;

    public FragmentUserDetailsPresenter() {

    }

    public void onAttachToView(Results results) {
        birthDate = results.getDob().getDate();
        registeredDate = results.getRegistered().getRegisteredDate();

        getViewState().initView();
        getViewState().setupToolbar();
        getViewState().setData(results, getConvertedBirthDate(), getConvertedRegisteredDate());
    }

    private String getConvertedBirthDate() {
        String finalDateString = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(birthDate);
            SimpleDateFormat sdfnewformat = new SimpleDateFormat("yyyy - MM - dd");
            finalDateString = sdfnewformat.format(convertedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return finalDateString;
    }

    private String getConvertedRegisteredDate() {
        String date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy ");
        try {
            date = sdf.format(new SimpleDateFormat("yyyy-M-dd").parse(registeredDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public void callUser(String phone) {
        Uri call = Uri.parse("tel:" + phone);
        Intent surf = new Intent(Intent.ACTION_DIAL, call);
        getViewState().startCall(surf);
    }
}
