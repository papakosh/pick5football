package com.bnavarro.pick5football.listeners;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bnavarro.pick5football.CommonUtils;
import com.bnavarro.pick5football.Match;
import com.bnavarro.pick5football.MatchDataManagementService;
import com.bnavarro.pick5football.pager.MatchListItem;

//TODO - Put header comments
public class MatchOnItemClickListener implements AdapterView.OnItemClickListener {
    private Match match;
    private ListView lv;

    /**
     *
     * @param lv
     * @param match
     */
    public MatchOnItemClickListener (ListView lv, Match match){
        CommonUtils.validateNotNull(lv, null);
        this.lv=lv;

        CommonUtils.validateNotNull(match, null);
        this.match=match;
    }

    /**
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (lv.getItemAtPosition(position) instanceof MatchListItem) {
            String teamSelected = ((MatchListItem) lv.getItemAtPosition(position)).getSelectedValue();
            String key = MatchDataManagementService.createKey(match);
            if (match.getSelectedTeam() == null)
                lv.setItemChecked(position, true);
            else if (teamSelected.equals(match.getSelectedTeam())){
                lv.setItemChecked(position, false);
                teamSelected = null;
            }else{
                lv.setItemChecked(position, true);
                if (position == 1)
                    lv.setItemChecked(position +1, false);
                else
                    lv.setItemChecked(position-1, false);
            }
            MatchDataManagementService.makePickSelection(key,teamSelected);
        }
    }
}
