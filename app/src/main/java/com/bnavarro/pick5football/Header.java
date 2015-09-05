package com.bnavarro.pick5football;

/**
 * Created by navman on 9/1/2015.
 */
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.bnavarro.pick5football.Item;

public class Header implements Item {
    private final String name;

    public Header(String name) {
        this.name = name;
    }

    @Override
    public int getViewType() {
        return MatchupAdapter.RowType.HEADER_ITEM.ordinal();
    }

    @Override
    public View getView(LayoutInflater inflater, View convertView) {
        View view;
        if (convertView == null) {
            view = (View) inflater.inflate(R.layout.header_item, null);
            // Do some initialization
        } else {
            view = convertView;
        }

        TextView text = (TextView) view.findViewById(R.id.separator);
        text.setText(name);

        return view;
    }
}

