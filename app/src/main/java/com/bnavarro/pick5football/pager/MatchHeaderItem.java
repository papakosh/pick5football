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

//TODO - put header comments
public class MatchHeaderItem implements MatchItem {
    private final String matchHeaderDetails;

    /**
     *
     * @param match A container for details on a football match, including match date and time
     */
    public MatchHeaderItem(Match match) {
        CommonUtils.validateNotNull(match, null);

        String date = match.getMatchDate();
        String time = match.getMatchTime();
        matchHeaderDetails = CommonUtils.concat(date," ",time);
    }

    /**
     *
     * @return
     */
    @Override
    public int getViewType() {
        return MatchItemAdapter.MATCH_HEADER_ITEM;
    }

    /**
     *
     * @param inflater
     * @param convertView
     * @return
     */
    @Override
    public View getView(LayoutInflater inflater, View convertView) {
        View view;
        if (convertView == null)
            view = inflater.inflate(R.layout.match_header_item, null);
         else
            view = convertView;

        TextView text = (TextView) view.findViewById(R.id.headerDetails);
        text.setText(matchHeaderDetails);

        return view;
    }
}

