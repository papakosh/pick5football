package com.bnavarro.pick5football;

import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by navman on 9/1/2015.
 */

public class ListItem implements Item {
    private final String str1;
    private final Integer imageId;
    private String teamName = "";

    public ListItem(String text1, Integer imageId) {
        this.str1 = text1;
        this.imageId = imageId;
    }
    public ListItem(String teamName, String text1, Integer imageId) {
        this.str1 = text1;
        this.imageId = imageId;
        this.teamName=teamName;
    }

    @Override
    public int getViewType() {
        return MatchupAdapter.RowType.LIST_ITEM.ordinal();
    }

    @Override
    public View getView(LayoutInflater inflater, View convertView) {

        View rowView;
        if (convertView == null) {
            rowView = (View) inflater.inflate(R.layout.list_item, null);
            // Do some initialization
        } else {
            rowView = convertView;
        }


        // LayoutInflater inflater = (LayoutInflater) context
        //        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //View rowView = inflater.inflate(R.layout.list, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.label1);
       //ArrayList<String> teamValuesList = parseText (str1);
//        String team = teamValuesList.get(0);
//        String home = teamValuesList.get(1);
//        String favored = teamValuesList.get(2);
//        if (home.equals("Away"))
//            home = "";
//        if (favored.equalsIgnoreCase("Underdog"))
//            favored = "";
//        else{
//            if (home.isEmpty()){
//                favored = "favored by ".concat(favored);
//            }
//            else
//                favored = ", favored by ".concat(favored);
//        }

        //textView.setText(team.concat("\n"+ home).concat(favored));
        textView.setText(str1);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        imageView.setImageResource(imageId);


        //return getItem(position).getView(mInflater, convertView);
        return rowView;
    }

    private ArrayList<String> parseText (String evalText){
        Integer delimiterPos = evalText.indexOf(";");
        Integer startPos = 0;
        ArrayList<String> teamItemsSorted = new ArrayList<String>();
        while (delimiterPos != -1){
            String value = evalText.substring(startPos, delimiterPos);
            //System.out.println ("value found is " + value);
            teamItemsSorted.add(value);
            startPos = delimiterPos+1;
            delimiterPos = evalText.indexOf(";", startPos);
        }
        return teamItemsSorted;
    }

    public String getSelectedValue (){
        return teamName;
    }

}
