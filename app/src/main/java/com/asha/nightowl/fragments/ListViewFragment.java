package com.asha.nightowl.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.asha.nightowl.R;

import static com.asha.nightowl.DemoUtil.fetchFakeImage;


/**
 * Created by hzqiujiadi on 15/11/9.
 * hzqiujiadi ashqalcn@gmail.com
 */
public class ListViewFragment extends Fragment {

    public static ListViewFragment newInstance() {

        Bundle args = new Bundle();

        ListViewFragment fragment = new ListViewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ListViewFragment() {
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        ListView listView = (ListView) view.findViewById(R.id.listview);
        listView.setAdapter(new DemoAdapter());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_view, container, false);
    }

    public static class DemoAdapter extends BaseAdapter{

        LayoutInflater mLayoutInflater;
        @Override
        public int getCount() {
            return 40;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if ( convertView == null ){
                if ( mLayoutInflater == null )
                    mLayoutInflater = LayoutInflater.from(parent.getContext());
                convertView = mLayoutInflater.inflate(R.layout.item_view1,parent,false);
            }
            VH vh = (VH) convertView.getTag();
            if( vh == null ){
                vh = new VH(convertView);
                convertView.setTag(vh);
            }
            vh.bind(position);
            return convertView;
        }
    }

    public static class VH {
        ImageView mImageView;

        public VH(View mView) {
            mImageView = (ImageView) mView.findViewById(R.id.image);
        }
        public void bind(int position){
            mImageView.setImageResource(fetchFakeImage(position));
        }
    }

}
