package com.ipda3.bankeldam.ui.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.ipda3.bankeldam.R;
import com.ipda3.bankeldam.data.local.SharedPrefManagerUser;
import com.ipda3.bankeldam.data.model.register.Client;
import com.ipda3.bankeldam.helper.HelperMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.ipda3.bankeldam.helper.Constants.SIGN_BIRTH_DATE;
import static com.ipda3.bankeldam.helper.Constants.SIGN_LAST_DONATE;


public class MyInformationFragment extends Fragment {


    @BindView(R.id.SignEtName)
    EditText SignEtName;
    @BindView(R.id.SignEtEmail)
    EditText SignEtEmail;
    @BindView(R.id.SignTvBirthDate)
    TextView SignTvBirthDate;
    @BindView(R.id.SignTvBloodType)
    TextView SignTvBloodType;
    @BindView(R.id.SignIvGovernment)
    ImageView SignIvGovernment;
    @BindView(R.id.SignSpGovernment)
    Spinner SignSpGovernment;
    @BindView(R.id.SignTvGovernment)
    TextView SignTvGovernment;
    @BindView(R.id.SignIvCity)
    ImageView SignIvCity;
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
    Unbinder unbinder;

    List<String> cityList;
    HashMap<String, String> hashMapCity;
    List<String> governmentList;
    HashMap<String, String> hashMapGovernment;
    String cityId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_sign, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        SignBtRegister.setText(R.string.MyInfoBtEditText);
        //getting the current user
        Client user = SharedPrefManagerUser.getInstance(getActivity()).getUser();
        SignEtName.setText(user.getName());
        SignEtEmail.setText(user.getEmail());
        SignTvBirthDate.setText(user.getBirthDate());
        SignEtPhone.setText(user.getPhone());
        SignTvLasDonate.setText(user.getDonationLastDate());

        cityList = new ArrayList<String>();
        hashMapCity = new HashMap<String, String>();
        governmentList = new ArrayList<String>();
        hashMapGovernment = new HashMap<String, String>();
        spinnerOnItemSelectedListener();

//        getGovernments();
        HelperMethod.getInstance(getActivity()).getGovernments(getActivity(),governmentList,hashMapGovernment,SignSpGovernment);

        return rootView;

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle
            savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("تعديل البيانات");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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
//                getCities(governmentId);
                HelperMethod.getInstance(getActivity()).getCities(getActivity(),governmentId,cityList,hashMapCity,SignSpCity);

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

    @OnClick({R.id.SignTvBirthDate, R.id.SignTvBloodType, R.id.SignTvLasDonate, R.id.SignBtRegister,R.id.SignIvCity,R.id.SignIvGovernment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.SignTvBirthDate:
//                openDateDialog(SIGN_BIRTH_DATE);
                HelperMethod.getInstance(getActivity()).openDateDialog(getActivity(),SIGN_BIRTH_DATE);

                break;
            case R.id.SignTvBloodType:
//                bloodTypesDialog();
                String[] bloodType = HelperMethod.getInstance(getActivity()).bloodTypesDialog(getActivity());
                SignTvBloodType.setText(bloodType[0]);
                break;
            case R.id.SignTvLasDonate:
//                openDateDialog(SIGN_LAST_DONATE);
                HelperMethod.getInstance(getActivity()).openDateDialog(getActivity(),SIGN_LAST_DONATE);
                break;
            case R.id.SignBtRegister:
//                Register();
                break;
            case R.id.SignIvCity:
                SignSpCity.performClick();
                break;
            case R.id.SignIvGovernment:
                SignSpGovernment.performClick();
                break;
        }
    }
}
