package com.example.secondapk.view;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListViewAdapter implements ListAdapter {
    private List<View> viewList = new ArrayList<View>();

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return viewList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        if (viewList.size() > position) {
            return viewList.get( position );
        }

        return null;
    }

    public void addView(View view) {
        viewList.add( view );
    }

    public void removeView(View view) {
        viewList.remove( view );
    }

    public void removeAllView() {
        viewList.clear();
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        // TODO Auto-generated method stub
        return 1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (viewList.size() > position) {
            return viewList.get( position );
        }

        return null;
    }

    @Override
    public int getViewTypeCount() {
        // TODO Auto-generated method stub
        return 1;
    }

    @Override
    public boolean hasStableIds() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isEmpty() {
        // TODO Auto-generated method stub
        return viewList.size() > 0;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        // TODO Auto-generated method stub

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean areAllItemsEnabled() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isEnabled(int position) {
        // TODO Auto-generated method stub
        return true;
    }

}
