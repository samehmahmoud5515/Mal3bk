package com.logicdesigns.mohammed.mal3bklast.common;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by logicDesigns on 10/4/2017.
 */


// SampleRecycler.java
public class SampleRecyclerAdapter extends RecyclerView.Adapter<SampleRecyclerAdapter.SampleHolder> {
    @Override
    public SampleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(SampleHolder holder, int position) {

    }


    @Override
    public int getItemCount() {
        return 0;
    }
    public class SampleHolder extends RecyclerView.ViewHolder {
        public SampleHolder(View itemView) {
            super(itemView);
        }
    }
}
