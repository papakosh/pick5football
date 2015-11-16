package com.bnavarro.pick5football.pager;

import android.view.LayoutInflater;
import android.view.View;

//TODO put header comments
public interface MatchItem {
    int getViewType();
    View getView(LayoutInflater inflater, View convertView);
}
