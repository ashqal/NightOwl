package com.asha.nightowl.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.asha.nightowl.DetailActivity;
import com.asha.nightowl.R;

import static com.asha.nightowl.DemoUtil.fetchFakeImage;


/**
 * Created by hzqiujiadi on 15/11/9.
 * hzqiujiadi ashqalcn@gmail.com
 */
public class RecyclerViewFragment extends Fragment {

    public static RecyclerViewFragment newInstance() {
        
        Bundle args = new Bundle();
        
        RecyclerViewFragment fragment = new RecyclerViewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public RecyclerViewFragment() {
    }
    RecyclerView recyclerView;
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(new DemoAdapter());
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recycler_view, container, false);
    }
    public static class VH extends RecyclerView.ViewHolder{
        ImageView mImageView;
        public VH(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DetailActivity.launch(v.getContext());
                }
            });
            mImageView = (ImageView) itemView.findViewById(R.id.image);
        }
        public void bind(int position){
            mImageView.setImageResource(fetchFakeImage(position));
        }
    }
    public static class DemoAdapter extends RecyclerView.Adapter<VH>{
        LayoutInflater mLayoutInflater;
        @Override
        public VH onCreateViewHolder(ViewGroup parent, int viewType) {
            if ( mLayoutInflater == null )
                mLayoutInflater = LayoutInflater.from(parent.getContext());
            View v = mLayoutInflater.inflate(R.layout.item_view2,parent,false);
            VH vh = new VH(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(VH holder, int position) {
            holder.bind(position);
        }

        @Override
        public int getItemCount() {
            return 40;
        }
    }

}
