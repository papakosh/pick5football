package com.bnavarro.pick5football.pager;

import android.view.LayoutInflater;
import android.view.View;

import android.widget.ImageView;
import android.widget.TextView;

import com.bnavarro.pick5football.R;

//TODO put header comments
public class MatchListItem implements MatchItem {
    private final String teamDetails;
    private final Integer imageId;
    private String teamName = "";

    /** Initializes the screen details for a team
     *
     * @param text1 the match specifics for the team, such as at home, favored, etc.
     * @param imageId the resource id for the team's logo
     * @param teamName the name of the team
     */
    public MatchListItem(String text1, Integer imageId, String teamName) {
        this.teamDetails = text1;
        this.imageId = imageId;
        this.teamName=teamName;
    }

    /**
     *
     * @return a view type of 1
     */
    @Override
    public int getViewType() {
        return MatchItemAdapter.MATCH_LIST_ITEM;
    }

    /** Returns a View with its fields populated. Two fields exist. A TextView and an ImageView.
     * The TextView is populated from the attribute teamDetails, while ImageView uses the imageId.
     *
     * @param inflater TBD
     * @param convertView TBD
     * @return a View with its field populated.
     */
    @Override
    public View getView(LayoutInflater inflater, View convertView) {
        View matchView;
        if (convertView == null)
            matchView = inflater.inflate(R.layout.match_list_item, null);
         else
            matchView = convertView;

        TextView textView = (TextView) matchView.findViewById(R.id.teamDetails);
        textView.setText(teamDetails);
        ImageView imageView = (ImageView) matchView.findViewById(R.id.teamLogo);
        imageView.setImageResource(imageId);

        return matchView;
    }

    /**
     *
     * @return the name of the team clicked on
     */
    public String getSelectedValue (){
        return teamName;
    }
}
