package com.bnavarro.pick5football.pager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.bnavarro.pick5football.R;

import java.util.List;

public class MatchItemAdapter extends ArrayAdapter<MatchItem> {
    private LayoutInflater mInflater;
    public static final int MATCH_HEADER_ITEM = 0;
    public static final int MATCH_LIST_ITEM = 1;

    /** Initializes the adapter class with a list of MatchItem objects
     *
     * @param context TBD
     * @param items a list of <code>MatchItem</code> objects
     */
    public MatchItemAdapter(Context context, List<MatchItem> items) {
        super(context, 0, items);
        mInflater = LayoutInflater.from(context);
    }

    /**
     *
     * @return the number of view types, which is always two.
     */
    @Override
    public int getViewTypeCount() {
        return 2;
    }

    /**
     *
     * @param position the position of the view clicked from the list
     * @return the view type, which is 0 for match header item and 1 for match list item
     */
    @Override
    public int getItemViewType(int position) {
        return getItem(position).getViewType();
    }

    /**
     *
     * @param position TBD
     * @param convertView TBD
     * @param parent TBD
     * @return TBD
     */
    public View getView(int position, View convertView, ViewGroup parent)  {
        ViewHolder holder;
        int rowType = getItemViewType(position);
        if (convertView == null) {
            holder = new ViewHolder();
            switch (rowType) {
                case MATCH_HEADER_ITEM:
                    convertView = mInflater.inflate(R.layout.match_header_item, null);
                    holder.View=getItem(position).getView(mInflater, convertView);
                    break;
                case MATCH_LIST_ITEM:
                    convertView = mInflater.inflate(R.layout.match_list_item, null);
                    holder.View=getItem(position).getView(mInflater, convertView);
                    break;
            }
            convertView.setTag(holder);
        }
        else // ???
            holder = (ViewHolder) convertView.getTag();
        return convertView; }

    public static class ViewHolder {
        public  View View; {
        }
    }


}