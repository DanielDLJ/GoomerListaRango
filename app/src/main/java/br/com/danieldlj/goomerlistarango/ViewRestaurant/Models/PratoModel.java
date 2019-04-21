package br.com.danieldlj.goomerlistarango.ViewRestaurant.Models;

import android.os.Parcel;
import android.os.Parcelable;

import br.com.danieldlj.goomerlistarango.Model.RestaurantMenuModel;

public class PratoModel extends RestaurantMenuModel implements Parcelable {

    public PratoModel(RestaurantMenuModel prato) {
        super(prato.getRestaurantId(),prato.getName(),prato.getImage(),prato.getPrice(),prato.getGroup(),prato.getSales());
    }

    protected PratoModel(Parcel in) {
        setRestaurantId(in.readInt());
        setName( in.readString());
        setImage(in.readString());
        if (in.readByte() == 0) {
            setPrice(null);
        } else {
            setPrice(in.readFloat());
        }
        setGroup(in.readString());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getRestaurantId());
        dest.writeString(getName());
        dest.writeString(getImage());
        dest.writeFloat(getPrice());
        dest.writeString(getGroup());
    }



    public static final Creator<PratoModel> CREATOR = new Creator<PratoModel>() {
        @Override
        public PratoModel createFromParcel(Parcel in) {
            return new PratoModel(in);
        }

        @Override
        public PratoModel[] newArray(int size) {
            return new PratoModel[size];
        }
    };
}
