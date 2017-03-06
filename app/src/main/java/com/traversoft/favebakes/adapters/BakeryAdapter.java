package com.traversoft.favebakes.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import com.traversoft.favebakes.models.Bakery;
import com.traversoft.favebakes.R;
import com.traversoft.favebakes.recyclerview.itemholders.BakeryHolder;

public class BakeryAdapter extends RecyclerView.Adapter<BakeryHolder> {

    private final List<Bakery> bakeries;
    private Context context;
    private int itemResource;

    public BakeryAdapter(Context context, int itemResource, List<Bakery> bakeries) {

        // 1. Initialize our adapter
        this.bakeries = bakeries;
        this.context = context;
        this.itemResource = itemResource;
    }

    @Override
    public BakeryHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                                  .inflate(this.itemResource, parent, false);
        return new BakeryHolder(this.context, view);
    }

    @Override
    public void onBindViewHolder(BakeryHolder holder, int position) {

        Bakery bakery = this.bakeries.get(position);
        holder.bindBakery(bakery);
    }

    @Override
    public int getItemCount() {

        return this.bakeries.size();
    }
}
