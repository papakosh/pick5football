package com.bnavarro.pick5football;

/**
 * Created by navman on 9/1/2015.
 */
import android.view.LayoutInflater;
import android.view.View;

public interface Item {
    public int getViewType();
    public View getView(LayoutInflater inflater, View convertView);
}
