package com.bnavarro.pick5football;

/**
 * Created by navman on 9/1/2015.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MatchupAdapter extends ArrayAdapter<Item> {
    private LayoutInflater mInflater;
    private final Context context;
    private  String[] values;
    private  Integer[] imageId;

    public MatchupAdapter(Context context, List<Item> items) {
        super(context, 0, items);
        this.context = context;
        mInflater = LayoutInflater.from(context);
        // this.values = values;
        // this.imageId=imageId;
    }

    public enum RowType {
        LIST_ITEM, HEADER_ITEM
    }


    @Override
    public int getViewTypeCount() {
        return RowType.values().length;
    }
    @Override
    public int getItemViewType(int position) {
        return getItem(position).getViewType();
    }


    private static final int TYPE_ITEM = 0; private static final int TYPE_SEPARATOR = 1;

    public View getView(int position, View convertView, ViewGroup parent)  {
        ViewHolder holder = null;
        int rowType = getItemViewType(position);
        View View;
        if (convertView == null) {
            holder = new ViewHolder();
            switch (rowType) {
                case TYPE_ITEM:
                    convertView = mInflater.inflate(R.layout.list_item, null);
                    holder.View=getItem(position).getView(mInflater, convertView);
                    break;
                case TYPE_SEPARATOR:
                    convertView = mInflater.inflate(R.layout.header_item, null);
                    holder.View=getItem(position).getView(mInflater, convertView);
                    break;
            }
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        return convertView; }

    public static class ViewHolder {
        public  View View; {
        }
    }


}