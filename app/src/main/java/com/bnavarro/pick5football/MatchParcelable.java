package com.bnavarro.pick5football;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by navman on 9/8/2015.
 */
public class MatchParcelable implements Parcelable {
    private Matchup[] matchups;

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeArray(matchups);
    }


    public static final Parcelable.Creator<MatchParcelable> CREATOR
            = new Parcelable.Creator<MatchParcelable>(){
        public MatchParcelable createFromParcel (Parcel in){
            return new MatchParcelable(in);

        }

        public MatchParcelable[] newArray (int size){
            return new MatchParcelable[size];
        }
    };

    private MatchParcelable (Parcel in){
        matchups = (Matchup[]) in.readArray(MatchParcelable.class.getClassLoader());
    }

}
