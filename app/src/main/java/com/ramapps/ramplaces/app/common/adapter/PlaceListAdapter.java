package com.ramapps.ramplaces.app.common.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ramapps.ramplaces.R;
import com.ramapps.ramplaces.app.data.response.Venue;
import com.ramapps.ramplaces.app.ui.view.IHomeView;
import com.ramapps.ramplaces.databinding.RowPlaceBinding;

import java.util.List;

/**
 * Created by Ramsheed on 11/03/2018.
 * Place List Adapter
 */
public class PlaceListAdapter extends RecyclerView.Adapter<PlaceListAdapter.ViewHolder> {


    private List<Venue> mOriginalList;
    private IHomeView callback;

    public PlaceListAdapter(IHomeView callback) {
        this.callback = callback;
    }

    public void setItems(List<Venue> list) {
        mOriginalList = list;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RowPlaceBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.row_place,
                        parent, false);
        binding.setCallback(callback);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Venue venue = mOriginalList.get(position);
        holder.binding.setCallback(callback);
        holder.binding.setData(venue);
        holder.binding.title.setText(venue.getName());
        if (venue.getCategories() != null && !venue.getCategories().isEmpty())
            holder.binding.desc.setText(venue.getCategories().get(0).getShortName());
    }

    @Override
    public int getItemCount() {
        return mOriginalList == null ? 0 : mOriginalList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        final RowPlaceBinding binding;

        public ViewHolder(RowPlaceBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }
}
