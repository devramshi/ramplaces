package com.ramapps.ramplaces.app.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.MenuItem;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ramapps.ramplaces.R;
import com.ramapps.ramplaces.app.common.BaseActivity;
import com.ramapps.ramplaces.app.data.SelectedPlace;
import com.ramapps.ramplaces.app.data.response.Venue;
import com.ramapps.ramplaces.app.presenter.DetailsPresenter;
import com.ramapps.ramplaces.app.ui.view.IDetailsView;
import com.ramapps.ramplaces.common.Navigator;
import com.ramapps.ramplaces.databinding.ActivityDetailsBinding;

public class DetailsActivity extends BaseActivity implements IDetailsView {
    private ActivityDetailsBinding binding;
    private double lat = 0, longit = 0;
    private Venue venue;
    private StringBuilder addRess, text;
    private RequestOptions requestOptions;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details);
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        venue = SelectedPlace.getInstance().getSelectedPlace();
        updateLocalDatas();
        DetailsPresenter presenter = new DetailsPresenter();
        presenter.setContext(this);
        presenter.setView(this);
        setPresenter(presenter);
        requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.placeholder_back);
        requestOptions.error(R.drawable.placeholder_back);
        requestOptions.fitCenter();
        binding.setHandler(this);
        presenter.loadImages(venue.getId());
    }

    private void updateLocalDatas() {
        text = new StringBuilder();
        text.append(venue.getName());
        binding.txttitle.setText(venue.getName());
        if (venue.getCategories() != null && !venue.getCategories().isEmpty()) {
            binding.txtDesc.setText(venue.getCategories().get(0).getShortName());
            text.append("\n").append(venue.getCategories().get(0).getShortName());
        }
        if (venue.getLocation() != null) {
            lat = venue.getLocation().getLat();
            longit = venue.getLocation().getLng();
            addRess = new StringBuilder();
            if (!TextUtils.isEmpty(venue.getLocation().getAddress()))
                addRess.append(venue.getLocation().getAddress()).append("\n");
            if (!TextUtils.isEmpty(venue.getLocation().getCity()))
                addRess.append(venue.getLocation().getCity()).append("\n");
            if (!TextUtils.isEmpty(venue.getLocation().getCountry()))
                addRess.append(venue.getLocation().getCountry()).append("\n");
            binding.txtAddress.setText(addRess);
        }
    }

    @Override
    public void updateImage(String location) {
        try {
            Glide.with(this)
                    .setDefaultRequestOptions(requestOptions)
                    .load(location)
                    .into(binding.topImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void startDirection() {
        if (lat != 0 && longit != 0)
            Navigator.getInstance().showDirections(this, lat, longit);
    }

    @Override
    public void startSharing() {
        try {
            if (!TextUtils.isEmpty(addRess.toString())) {
                text.append("\n").append(addRess.toString());
            }
            Navigator.getInstance().shareText(this, text.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startCall() {
        showAlert(getString(R.string.phone_unavailable), 3000);
    }
}
