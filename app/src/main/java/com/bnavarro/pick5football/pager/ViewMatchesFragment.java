package com.bnavarro.pick5football.pager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.bnavarro.pick5football.Match;
import com.bnavarro.pick5football.MatchDataManagementService;
import com.bnavarro.pick5football.R;
import com.bnavarro.pick5football.listeners.MatchOnItemClickListener;

import java.util.ArrayList;

/**
 * Created by navman on 9/1/2015.
 */
public class ViewMatchesFragment extends Fragment {

    private ListView lv1 = null;
    private ListView lv2 = null;
    private ListView lv3 = null;
    private static Boolean[] previousSelectionsExist;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout resource that'll be returned
        View rootView = inflater.inflate(R.layout.matchup_fragment_layout, container, false);

        Bundle args = getArguments();
        int page_number = args.getInt("page_number");

        // get an array of matches
        Match[ ] matches = MatchDataManagementService.matchMapToArray();

        // calculate for each list the index of the match it will display
        Integer list_one_index = page_number * 3; // page 0 = 0, 1 = 3, 2 = 6
        Integer list_two_index = page_number * 3 + 1; // page 0 = 1, 1 = 4, 2 = 7
        Integer list_three_index = page_number * 3 + 2; // page 0 = 2, 1 = 5, 1 = 8


        ArrayList<MatchItem> listOneItems = new ArrayList();
        ArrayList<MatchItem> listTwoItems = new ArrayList();
        ArrayList<MatchItem> listThreeItems = new ArrayList();
        lv1 = (ListView) rootView.findViewById(R.id.listView);
        lv2 = (ListView) rootView.findViewById(R.id.listView2);
        lv3 = (ListView) rootView.findViewById(R.id.listView3);

        if (list_one_index < matches.length) {
            lv1.setOnItemClickListener(new MatchOnItemClickListener(lv1, matches[list_one_index]));
            MatchHeaderItem headerItem1 = new MatchHeaderItem (matches[list_one_index]);

            MatchListItem listItem1a = new MatchListItem(matches[list_one_index].getTeamHeaderDetails(matches[list_one_index].getTeam1()),
                    matches[list_one_index].getTeam1().getLogo(),matches[list_one_index].getTeam1().getName());

            MatchListItem listItem1b = new MatchListItem(matches[list_one_index].getTeamHeaderDetails(matches[list_one_index].getTeam2()),
                    matches[list_one_index].getTeam2().getLogo(),matches[list_one_index].getTeam2().getName());
            listOneItems.add (headerItem1);
            listOneItems.add (listItem1a);
            listOneItems.add (listItem1b);
        }

        if (list_two_index < matches.length) {
            lv2.setOnItemClickListener(new MatchOnItemClickListener(lv2, matches[list_two_index]));
            MatchHeaderItem headerItem2 = new MatchHeaderItem (matches[list_two_index]);

            MatchListItem listItem2a = new MatchListItem(matches[list_two_index].getTeamHeaderDetails(matches[list_two_index].getTeam1()),
                    matches[list_two_index].getTeam1().getLogo(),matches[list_two_index].getTeam1().getName());

            MatchListItem listItem2b = new MatchListItem(matches[list_two_index].getTeamHeaderDetails(matches[list_two_index].getTeam2()),
                    matches[list_two_index].getTeam2().getLogo(),matches[list_two_index].getTeam2().getName());
            listTwoItems.add (headerItem2);
            listTwoItems.add (listItem2a);
            listTwoItems.add (listItem2b);
        }

        if (list_three_index < matches.length) {
            lv3.setOnItemClickListener(new MatchOnItemClickListener(lv3, matches[list_three_index]));
            MatchHeaderItem headerItem3 = new MatchHeaderItem (matches[list_three_index]);

            MatchListItem listItem3a = new MatchListItem(matches[list_three_index].getTeamHeaderDetails(matches[list_three_index].getTeam1()),
                    matches[list_three_index].getTeam1().getLogo(),matches[list_three_index].getTeam1().getName());

            MatchListItem listItem3b = new MatchListItem(matches[list_three_index].getTeamHeaderDetails(matches[list_three_index].getTeam2()),
                    matches[list_three_index].getTeam2().getLogo(),matches[list_three_index].getTeam2().getName());
            listThreeItems.add (headerItem3);
            listThreeItems.add (listItem3a);
            listThreeItems.add (listItem3b);
        }


        final MatchItemAdapter listadapter = new MatchItemAdapter(this.getActivity().getBaseContext(), listOneItems);
        lv1.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lv1.setAdapter(listadapter);

        final MatchItemAdapter listadapter2 = new MatchItemAdapter(this.getActivity().getBaseContext(), listTwoItems);
        lv2.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lv2.setAdapter(listadapter2);

        final MatchItemAdapter listadapter3 = new MatchItemAdapter(this.getActivity().getBaseContext(), listThreeItems);
        lv3.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lv3.setAdapter(listadapter3);

        if (previousSelectionsExist == null)
            previousSelectionsExist = new Boolean[]{false, false, false, false, false, false};

        if (previousSelectionsExist [page_number]) {
            if (matches[list_one_index] != null && matches[list_one_index].getSelectedTeam() != null) {
                if (matches[list_one_index].getSelectedTeam().contains(matches[list_one_index].getTeam1().getName()))
                    lv1.setItemChecked(1, true);
                else
                    lv1.setItemChecked(2, true);
            }

            if (matches[list_two_index] != null && matches[list_two_index].getSelectedTeam() != null) {
                if (matches[list_two_index].getSelectedTeam().contains(matches[list_two_index].getTeam1().getName()))
                    lv2.setItemChecked(1, true);
                else
                    lv2.setItemChecked(2, true);
            }

            if (matches[list_three_index] != null && matches[list_three_index].getSelectedTeam() != null) {
                if (matches[list_three_index].getSelectedTeam().contains(matches[list_three_index].getTeam1().getName()))
                    lv3.setItemChecked(1, true);
                else
                    lv3.setItemChecked(2, true);
            }

            previousSelectionsExist[page_number] = false;
        }

        return rootView;
    }

    public static void notifyPreviousSelectionsExist ( ) {
        previousSelectionsExist  = new Boolean[]{false, false, false, false, false, false};
        int index = 0;
        Match [ ] matches = MatchDataManagementService.matchMapToArray ( );
        for (int i = 0; i < matches.length; i++){
            index = calculateIndex (i, index);
            if (matches[i].getSelectedTeam() != null && !previousSelectionsExist[index])
                previousSelectionsExist[index] = true;
        }
    }

    private static int calculateIndex (int i, int c_index) {
        if ((i / 3) < (c_index + 1))
            return c_index;
        else
            return c_index + 1;
    }

}
