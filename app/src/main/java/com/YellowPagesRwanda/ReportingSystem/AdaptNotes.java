package com.YellowPagesRwanda.ReportingSystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class AdaptNotes extends BaseAdapter {
    private Context myContext;
    private ArrayList<String> rowItem;
    AdaptNotes(Context mycontext, ArrayList<String> rowItem) {
        this.myContext = mycontext;
        this.rowItem = rowItem;
    }

    public int getCount() {
        return rowItem.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int pos, View child, ViewGroup parent) {
        Holder mHolder;
        LayoutInflater layoutInflater;
        if (child == null) {
            layoutInflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            child = layoutInflater.inflate(R.layout.notes, null);
            mHolder = new Holder();
            mHolder.txtrowItem = child.findViewById(R.id.note);
            child.setTag(mHolder);
        } else {
            mHolder = (Holder) child.getTag();
        }
        mHolder.txtrowItem.setText(rowItem.get(pos));
        return child;
    }

    public class Holder {
        TextView txtrowItem;
    }

}

