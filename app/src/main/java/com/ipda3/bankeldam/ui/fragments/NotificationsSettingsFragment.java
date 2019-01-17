package com.ipda3.bankeldam.ui.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ipda3.bankeldam.R;
import com.ipda3.bankeldam.adapter.NotSetGroupAdapter;
import com.ipda3.bankeldam.data.model.NotificationSettingsModel;
import com.ipda3.bankeldam.data.model.cities.CitiesResponse;
import com.ipda3.bankeldam.data.model.cities.Datum;
import com.ipda3.bankeldam.data.rest.ApiClient;
import com.ipda3.bankeldam.data.rest.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NotificationsSettingsFragment extends Fragment {


    Unbinder unbinder;
    @BindView(R.id.NotSettingsRvGroup)
    RecyclerView NotSettingsRvGroup;
    @BindView(R.id.NotSettingsBtSave)
    Button NotSettingsBtSave;

    ArrayList<NotificationSettingsModel> sensorList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_notifications_settings, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        NotSettingsRvGroup.setHasFixedSize(true);
        NotSettingsRvGroup.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        sensorList = new ArrayList<NotificationSettingsModel>();
        loadBloodTypes();



        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle
            savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("إعدادات الإشعارات");
    }

    private void loadBloodTypes(){
        NotificationSettingsModel dm = new NotificationSettingsModel();
        dm.setHeaderTitle(getString(R.string.NotSetTvBloodTypeText));
        ArrayList<Datum> singleItem = new ArrayList<Datum>();
        singleItem.add( new Datum("A+"));
        singleItem.add( new Datum("A-"));
        singleItem.add( new Datum("B+"));
        singleItem.add( new Datum("B-"));
        singleItem.add( new Datum("O+"));
        singleItem.add( new Datum("O-"));
        singleItem.add( new Datum("AB+"));
        singleItem.add( new Datum("AB-"));
        dm.setAllItemsInSection(singleItem);
        sensorList.add(dm);
        getCities("1");
    }


    public void getCities(String governorate_id) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<CitiesResponse> call = apiInterface.getCitiesList(governorate_id);
        call.enqueue(new Callback<CitiesResponse>() {
            @Override
            public void onResponse(Call<CitiesResponse> call, Response<CitiesResponse> response) {
                //get response values

                if (response.isSuccessful()) {
                    NotificationSettingsModel dm1 = new NotificationSettingsModel();
                    dm1.setHeaderTitle(getString(R.string.NotSetTvCityText));
                    ArrayList<Datum> singleItem1 = new ArrayList<Datum>();
                    List<Datum> listDatum = response.body().getData();
                    for (int i = 0; i < listDatum.size(); i++) {
                        String name = listDatum.get(i).getName();
                        String id = String.valueOf(listDatum.get(i).getId());
                        singleItem1.add( new Datum(name));
                    }
                    dm1.setAllItemsInSection(singleItem1);
                    sensorList.add(dm1);
                    NotSetGroupAdapter adapter = new NotSetGroupAdapter(getActivity(), sensorList);
                    NotSettingsRvGroup.setAdapter(adapter);

                } else {

                }
            }

            @Override
            public void onFailure(Call<CitiesResponse> call, Throwable t) {
            }
        });
    }


    @OnClick(R.id.NotSettingsBtSave)
    public void onViewClicked() {
    }
}
