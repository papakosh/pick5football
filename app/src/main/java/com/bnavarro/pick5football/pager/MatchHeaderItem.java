package com.bnavarro.pick5football.pager;

/**
 * Created by navman on 9/1/2015.
 */
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.bnavarro.pick5football.CommonUtils;
import com.bnavarro.pick5football.Match;
import com.bnavarro.pick5football.R;

public class MatchHeaderItem implements MatchItem {
    private final String matchHeaderDetails;

    public MatchHeaderItem(Match match) {
        CommonUtils.validateNotNull(match, null);

        String date = match.getMatchDate();
        String time = match.getMatchTime();
        matchHeaderDetails = CommonUtils.concat(date," ",time);
    }

    @Override
    public int getViewType() {
        return MatchItemAdapter.MATCH_HEADER_ITEM;
    }

    @Override
    public View getView(LayoutInflater inflater, View convertView) {
        View view;
        if (convertView == null)
            view = inflater.inflate(R.layout.match_header_item, null);
         else
            view = convertView;

        TextView text = (TextView) view.findViewById(R.id.separator);
        text.setText(matchHeaderDetails);

        return view;
    }
}

