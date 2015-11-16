package com.bnavarro.pick5football.pager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.bnavarro.pick5football.CommonUtils;
import com.bnavarro.pick5football.Match;
import com.bnavarro.pick5football.MatchDataManagementService;
import com.bnavarro.pick5football.R;
import com.bnavarro.pick5football.listeners.MatchOnItemClickListener;

import java.util.ArrayList;

//TODO put header comments
public class ViewMatchesFragment extends Fragment {
    private static Boolean[] previousSelectionsExist;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.matchup_fragment_layout, container, false);

        Bundle args = getArguments();
        int page_number = args.getInt("page_number");

        // get an array of matches
        Match[ ] matches = MatchDataManagementService.matchMapToArray();

        // calculate the index of the match to be displayed for each list
        Integer list_one_index = page_number * 3; // page 0 = 0, 1 = 3, 2 = 6
        Integer list_two_index = page_number * 3 + 1; // page 0 = 1, 1 = 4, 2 = 7
        Integer list_three_index = page_number * 3 + 2; // page 0 = 2, 1 = 5, 1 = 8

        ArrayList<MatchItem> listOneItems = new ArrayList<>();
        ArrayList<MatchItem> listTwoItems = new ArrayList<>();
        ArrayList<MatchItem> listThreeItems = new ArrayList<>();
        ListView lv1 = (ListView) rootView.findViewById(R.id.listView);
        ListView lv2 = (ListView) rootView.findViewById(R.id.listView2);
        ListView lv3 = (ListView) rootView.findViewById(R.id.listView3);

        // Set match to display for list 1
        if (list_one_index < matches.length) {
            Match match1 = matches[list_one_index];
            lv1.setOnItemClickListener(new MatchOnItemClickListener(lv1, match1));
            MatchHeaderItem headerItem = new MatchHeaderItem (match1);

            MatchListItem listItem1 = new MatchListItem(
                    match1.getTeamOneHeaderDetails(),
                    match1.getTeam1().getLogo(),
                    match1.getTeam1().getName());

            MatchListItem listItem2 = new MatchListItem(
                    match1.getTeamTwoHeaderDetails(),
                    match1.getTeam2().getLogo(),
                    match1.getTeam2().getName());

            listOneItems.add (headerItem);
            listOneItems.add (listItem1);
            listOneItems.add (listItem2);
        }

        // Set match to display for list 2
        if (list_two_index < matches.length) {
            Match match2 = matches[list_two_index];
            lv2.setOnItemClickListener(new MatchOnItemClickListener(lv2, match2));
            MatchHeaderItem headerItem = new MatchHeaderItem (match2);

            MatchListItem listItem1 = new MatchListItem(
                    match2.getTeamOneHeaderDetails(),
                    match2.getTeam1().getLogo(),
                    match2.getTeam1().getName());

            MatchListItem listItem2 = new MatchListItem(
                    match2.getTeamTwoHeaderDetails(),
                    match2.getTeam2().getLogo(),
                    match2.getTeam2().getName());
            listTwoItems.add (headerItem);
            listTwoItems.add (listItem1);
            listTwoItems.add (listItem2);
        }

        // Set match to display for list 3
        if (list_three_index < matches.length) {
            Match match3 = matches[list_three_index];
            lv3.setOnItemClickListener(new MatchOnItemClickListener(lv3, match3));
            MatchHeaderItem headerItem = new MatchHeaderItem (match3);

            MatchListItem listItem1 = new MatchListItem(
                    match3.getTeamOneHeaderDetails(),
                    match3.getTeam1().getLogo(),
                    match3.getTeam1().getName());

            MatchListItem listItem2 = new MatchListItem(
                    match3.getTeamTwoHeaderDetails(),
                    match3.getTeam2().getLogo(),
                    match3.getTeam2().getName());
            listThreeItems.add (headerItem);
            listThreeItems.add (listItem1);
            listThreeItems.add (listItem2);
        }

        final MatchItemAdapter listadapter = createAdapter (listOneItems);
        lv1.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lv1.setAdapter(listadapter);

        final MatchItemAdapter listadapter2 = createAdapter(listTwoItems);
        lv2.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lv2.setAdapter(listadapter2);

        final MatchItemAdapter listadapter3 = createAdapter(listThreeItems);
        lv3.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lv3.setAdapter(listadapter3);

        if (previousSelectionsExist == null)
            previousSelectionsExist = new Boolean[]{false, false, false, false, false, false};

        // Set selection if previous exists
        if (previousSelectionsExist [page_number]) {
            Match match1 = getMatch(matches, list_one_index);
            Match match2 = getMatch(matches, list_two_index);
            Match match3 = getMatch(matches, list_three_index);

            markSelectionIfPreviousExists(match1, lv1);
            markSelectionIfPreviousExists(match2, lv2);
            markSelectionIfPreviousExists(match3, lv3);

            previousSelectionsExist[page_number] = false;
        }

        return rootView;
    }

    /**
     *
     */
    public static void notifyPreviousSelectionsExist ( ) {
        previousSelectionsExist  = new Boolean[]{false, false, false, false, false, false};
        int c_page = 0;
        Match [ ] matches = MatchDataManagementService.matchMapToArray ( );
        for (int i = 0; i < matches.length; i++){
            c_page = calculateCurrentPageNumber(i, c_page);
            if (matches[i].getSelectedTeam() != null && !previousSelectionsExist[c_page])
                previousSelectionsExist[c_page] = true;
        }
    }

    private static int calculateCurrentPageNumber (int i, int c_page) {
        if ((i / 3) < (c_page + 1))
            return c_page;
        else
            return c_page + 1;
    }

    private MatchItemAdapter createAdapter (ArrayList<MatchItem> listItems){
        return new MatchItemAdapter(this.getActivity().getBaseContext(), listItems);
    }

    private boolean aTeamIsSelected(Match match){
       return match != null && CommonUtils.isStringNotEmpty(match.getSelectedTeam());
    }

    private boolean teamOneIsSelected(Match match) {
        return match.getSelectedTeam().contains(match.getTeam1().getName());
    }

    private Match getMatch (Match[] matches, int index){
        if (index > matches.length)
            return null;
        return matches[index];

    }

    private void markSelectionIfPreviousExists(Match match, ListView listView){
        if (aTeamIsSelected(match)) {
            if (teamOneIsSelected(match))
                listView.setItemChecked(1, true);
            else
                listView.setItemChecked(2, true);
        }
    }
}
