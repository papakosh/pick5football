package com.bnavarro.pick5football.pager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ViewMatchesFragmentPagerAdapter extends FragmentStatePagerAdapter {

    public ViewMatchesFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    /** returns the fragment associated with the specified position.
     * It is called when the Adapter needs a fragment and it does not exists.
     *
     * @param position the current page position in the ViewPager. Values range
     *                 from 0 to 5 and is passed to the fragment.
     * @return the fragment for the specified page
     * @see ViewMatchesFragment
     */
    @Override
    public Fragment getItem(int position) {
        ViewMatchesFragment fragment = new ViewMatchesFragment();

        Bundle args = new Bundle();
        args.putInt("page_number", position);
        fragment.setArguments(args);

        return fragment;
    }

    /**
     * @return total number of pages
     */
    @Override
    public int getCount() {
        return 6;
    }
}
