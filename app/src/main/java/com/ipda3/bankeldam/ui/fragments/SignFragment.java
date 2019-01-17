package com.ipda3.bankeldam.ui.fragments;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.ipda3.bankeldam.R;
import com.ipda3.bankeldam.data.local.SharedPrefManager;
import com.ipda3.bankeldam.data.local.SharedPrefManagerUser;
import com.ipda3.bankeldam.data.model.cities.CitiesResponse;
import com.ipda3.bankeldam.data.model.cities.Datum;
import com.ipda3.bankeldam.data.model.governorates.DatumG;
import com.ipda3.bankeldam.data.model.governorates.GovernoratesResponse;
import com.ipda3.bankeldam.data.model.register.Client;
import com.ipda3.bankeldam.data.model.register.RegisterResponse;
import com.ipda3.bankeldam.data.rest.ApiClient;
import com.ipda3.bankeldam.data.rest.ApiInterface;
import com.ipda3.bankeldam.helper.DatePickerFragment;
import com.ipda3.bankeldam.helper.HelperMethod;
import com.ipda3.bankeldam.ui.activities.AfterSplashActivity;
import com.ipda3.bankeldam.ui.activities.MainActivity;
import com.ipda3.bankeldam.ui.activities.SplashActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ipda3.bankeldam.helper.Constants.BLOOD_TYPES;
import static com.ipda3.bankeldam.helper.Constants.DATE_PICKER;
import static com.ipda3.bankeldam.helper.Constants.SIGN_BIRTH_DATE;
import static com.ipda3.bankeldam.helper.Constants.SIGN_LAST_DONATE;


public class SignFragment extends Fragment {


    @BindView(R.id.SignEtName)
    EditText SignEtName;
    @BindView(R.id.SignEtEmail)
    EditText SignEtEmail;
    @BindView(R.id.SignTvBirthDate)
    TextView SignTvBirthDate;
    @BindView(R.id.SignTvBloodType)
    TextView SignTvBloodType;
    @BindView(R.id.SignSpGovernment)
    Spinner SignSpGovernment;
    @BindView(R.id.SignTvGovernment)
    TextView SignTvGovernment;
    @BindView(R.id.SignSpCity)
    Spinner SignSpCity;
    @BindView(R.id.SignTvCity)
    TextView SignTvCity;
    @BindView(R.id.SignEtPhone)
    EditText SignEtPhone;
    @BindView(R.id.SignEtPassword)
    EditText SignEtPassword;
    @BindView(R.id.SignEtPasswordConfirm)
    EditText SignEtPasswordConfirm;
    @BindView(R.id.SignTvLasDonate)
    TextView SignTvLasDonate;
    @BindView(R.id.SignBtRegister)
    Button SignBtRegister;
    @BindView(R.id.SignIvGovernment)
    ImageView SignIvGovernment;
    @BindView(R.id.SignIvCity)
    ImageView SignIvCity;

    Unbinder unbinder;

    List<String> cityList;
    HashMap<String, String> hashMapCity;
    List<String> governmentList;
    HashMap<String, String> hashMapGovernment;
    String bloodType;
    String cityId;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_sign, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        cityList = new ArrayList<String>();
        hashMapCity = new HashMap<String, String>();
        governmentList = new ArrayList<String>();
        hashMapGovernment = new HashMap<String, String>();
        spinnerOnItemSelectedListener();

        getGovernments();
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.SignTvBirthDate, R.id.SignTvBloodType, R.id.SignTvLasDonate, R.id.SignBtRegister,R.id.SignIvCity,R.id.SignIvGovernment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.SignTvBirthDate:
                openDateDialog(SIGN_BIRTH_DATE);
                break;
            case R.id.SignTvBloodType:
                bloodTypesDialog();
                break;
            case R.id.SignTvLasDonate:
                openDateDialog(SIGN_LAST_DONATE);
                break;
            case R.id.SignBtRegister:
                Register();
                break;
            case R.id.SignIvCity:
                SignSpCity.performClick();
                break;
            case R.id.SignIvGovernment:
                SignSpGovernment.performClick();
                break;
        }
    }


    private void openDateDialog(String value) {
        SharedPrefManager.getInstance(getActivity()).setKEY_DIALOG(value);
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), DATE_PICKER);
    }

    private void bloodTypesDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.SignBloodTypeDialogTitle);

        //list of items
        final String[] bloodTypes = BLOOD_TYPES;
        builder.setSingleChoiceItems(bloodTypes, 0,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // item selected logic
                        bloodType = String.valueOf(bloodTypes[which]);
                        SignTvBloodType.setText(bloodType);
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
    }

    private void getCities(String governorate_id) {
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
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, cityList);
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

    private void getGovernments() {
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
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, governmentList);
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

    private void spinnerOnItemSelectedListener() {
        SignSpGovernment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // On selecting a spinner item
                String item = parent.getItemAtPosition(position).toString();
                //get government id from hashMap
                String governmentId = (String) hashMapGovernment.get(item);
                //set government name to the textView
                SignTvGovernment.setText(item);
                //get cities
                getCities(governmentId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        SignSpCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // On selecting a spinner item
                String item = parent.getItemAtPosition(position).toString();
                //set city name to the textView
                SignTvCity.setText(item);
                //get city id from hashMap
                cityId = (String) hashMapCity.get(item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void Register(){
        String name =SignEtName.getText().toString();
        String email =SignEtEmail.getText().toString();
        String birth_date =SignTvBirthDate.getText().toString();
        String phone =SignEtPhone.getText().toString();
        String donation_last_date =SignTvLasDonate.getText().toString();
        String password =SignEtPassword.getText().toString();
        String password_confirm =SignEtPasswordConfirm.getText().toString();

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        //declare a progress dialog
        final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Register...");
        pDialog.show();
        Call<RegisterResponse> call = apiInterface.Register(name,email,birth_date,cityId,phone,donation_last_date,password,
                password_confirm,bloodType);
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                //get response values
                pDialog.hide();
                if (response.isSuccessful()) {
                    storeUserData(response);
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                } else {

                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                pDialog.hide();
                HelperMethod.getInstance(getActivity()).callToast(getActivity(),t.toString());

            }
        });
    }
    public void storeUserData(Response<RegisterResponse> response){
        String msg = response.body().getMsg();
        HelperMethod.getInstance(getActivity()).callToast(getActivity(),msg);
        String api_token =response.body().getData().getApiToken();

        String name = response.body().getData().getClient().getName();
        String email = response.body().getData().getClient().getEmail();
        String birth_date = response.body().getData().getClient().getBirthDate();
        String city_id = response.body().getData().getClient().getCityId();
        String phone = response.body().getData().getClient().getPhone();
        String last_donate = response.body().getData().getClient().getDonationLastDate();
        String blood_type = response.body().getData().getClient().getBloodType();

        //storing the api_token in shared preferences
        SharedPrefManagerUser.getInstance(getActivity()).setKeyApiToken(api_token);
        Client user = new Client(name,email,birth_date,city_id,phone,last_donate,blood_type);
        //storing the user in shared preferences
        SharedPrefManagerUser.getInstance(getActivity()).setUser(user);
    }

}
