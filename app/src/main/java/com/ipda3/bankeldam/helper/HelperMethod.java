package com.ipda3.bankeldam.helper;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.ipda3.bankeldam.R;
import com.ipda3.bankeldam.adapter.NotSetGroupAdapter;
import com.ipda3.bankeldam.data.local.SharedPrefManager;
import com.ipda3.bankeldam.data.model.NotificationSettingsModel;
import com.ipda3.bankeldam.data.model.cities.CitiesResponse;
import com.ipda3.bankeldam.data.model.cities.Datum;
import com.ipda3.bankeldam.data.model.governorates.DatumG;
import com.ipda3.bankeldam.data.model.governorates.GovernoratesResponse;
import com.ipda3.bankeldam.data.rest.ApiClient;
import com.ipda3.bankeldam.data.rest.ApiInterface;
import com.ipda3.bankeldam.ui.activities.AfterSplashActivity;
import com.ipda3.bankeldam.ui.fragments.SignFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ipda3.bankeldam.helper.Constants.BLOOD_TYPES;
import static com.ipda3.bankeldam.helper.Constants.DATE_PICKER;

public class HelperMethod {
    private static HelperMethod mInstance;
    private static Context mCtx;

    ProgressDialog pDialog;

    private HelperMethod(Context context) {
        mCtx = context;
    }

    public static synchronized HelperMethod getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new HelperMethod(context);
        }
        return mInstance;
    }

    public void loadSplashFragments(Context context,String currentTag){
        AfterSplashActivity.CURRENT_TAG = currentTag;
        ((AfterSplashActivity)context).loadFragment();
    }

    public void callToast(Context context,String msg){
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public void initialProgressDialog(Context context,String msg){
        pDialog = new ProgressDialog(context);
        pDialog.setMessage(msg);
        pDialog.show();
    }

    public void hideProgressDialog(){
        pDialog.hide();
    }
    public void openDateDialog(Context context,String value){
        try {
            FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
            SharedPrefManager.getInstance(context).setKEY_DIALOG(value);
            DialogFragment newFragment = new DatePickerFragment();
            newFragment.show(fragmentManager, DATE_PICKER);
        } catch (ClassCastException e) {
            callToast(context,e.toString());
        }

    }

    public String[] bloodTypesDialog(Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.SignBloodTypeDialogTitle);
        //list of items
        final String[] bloodTypes = BLOOD_TYPES;
        final String[] bloodType = new String[1];

        builder.setSingleChoiceItems(bloodTypes, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // item selected logic
                        bloodType[0] = String.valueOf(bloodTypes[which]);
//                        SignTvBloodType.setText(bloodType[0]);
                    }
                });
        builder.setPositiveButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // positive button logic
                    }
                });
        AlertDialog dialog = builder.create();
        // display dialog
        dialog.show();

    return bloodType;
    }

    public void getGovernments(final Context context, final List<String> governmentList,
                               final HashMap<String, String> hashMapGovernment,
                               final Spinner SignSpGovernment) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<GovernoratesResponse> call = apiInterface.getGovernoratesList();
        call.enqueue(new Callback<GovernoratesResponse>() {
            @Override
            public void onResponse(Call<GovernoratesResponse> call, Response<GovernoratesResponse> response) {
                //get response values

                if (response.isSuccessful()) {
                    List<DatumG> listDatum = response.body().getData();
                    for (int i = 0; i < listDatum.size(); i++) {
                        String name = listDatum.get(i).getName();
                        String id = String.valueOf(listDatum.get(i).getId());
                        governmentList.add(name);
                        hashMapGovernment.put(name, id);
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, governmentList);
                    adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                    SignSpGovernment.setAdapter(adapter);

                } else {

                }
            }

            @Override
            public void onFailure(Call<GovernoratesResponse> call, Throwable t) {
            }
        });
    }

    public void getCities(final Context context, String governorate_id, final List<String> cityList,
                          final HashMap<String, String> hashMapCity,final Spinner SignSpCity) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<CitiesResponse> call = apiInterface.getCitiesList(governorate_id);
        call.enqueue(new Callback<CitiesResponse>() {
            @Override
            public void onResponse(Call<CitiesResponse> call, Response<CitiesResponse> response) {
                //get response values

                if (response.isSuccessful()) {

                    List<Datum> listDatum = response.body().getData();
                    for (int i = 0; i < listDatum.size(); i++) {
                        String name = listDatum.get(i).getName();
                        String id = String.valueOf(listDatum.get(i).getId());
                        cityList.add(name);
                        hashMapCity.put(name, id);
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, cityList);
                    adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                    SignSpCity.setAdapter(adapter);


                } else {

                }
            }

            @Override
            public void onFailure(Call<CitiesResponse> call, Throwable t) {
            }
        });
    }



}
