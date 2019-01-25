package p.vasylprokudin.axon.data.network.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Results implements Parcelable {

    private String gender;
    private Name name;
    private String email;
    private Login login;
    private Dob dob;
    private String cell;
    private Picture picture;
    private Location location;
    private Registered registered;

    protected Results(Parcel in) {
        gender = in.readString();
        email = in.readString();
        cell = in.readString();
    }

    public static final Creator<Results> CREATOR = new Creator<Results>() {
        @Override
        public Results createFromParcel(Parcel in) {
            return new Results(in);
        }

        @Override
        public Results[] newArray(int size) {
            return new Results[size];
        }
    };

    public String getGender() {
        return gender;
    }

    public Name getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Login getLogin() {
        return login;
    }

    public Dob getDob() {
        return dob;
    }

    public String getCell() {
        return cell;
    }

    public Picture getPicture() {
        return picture;
    }

    public Location getLocation() {
        return location;
    }

    public Registered getRegistered() {
        return registered;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(gender);
        parcel.writeString(email);
        parcel.writeString(cell);
    }
}
