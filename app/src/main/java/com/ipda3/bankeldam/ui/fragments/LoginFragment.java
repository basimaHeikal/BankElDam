package com.ipda3.bankeldam.ui.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;


import com.ipda3.bankeldam.R;
import com.ipda3.bankeldam.data.local.SharedPrefManagerUser;
import com.ipda3.bankeldam.data.model.login.LoginResponse;
import com.ipda3.bankeldam.data.model.register.Client;
import com.ipda3.bankeldam.data.rest.ApiClient;
import com.ipda3.bankeldam.data.rest.ApiInterface;
import com.ipda3.bankeldam.helper.HelperMethod;
import com.ipda3.bankeldam.ui.activities.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ipda3.bankeldam.ui.activities.AfterSplashActivity.TAG_SIGN;


public class LoginFragment extends Fragment {


    Unbinder unbinder;
    @BindView(R.id.LoginEtPhone)
    EditText LoginEtPhone;
    @BindView(R.id.LoginEtPassword)
    EditText LoginEtPassword;
    @BindView(R.id.LoginTvForgetPassword)
    TextView LoginTvForgetPassword;
    @BindView(R.id.LoginCbRememberMe)
    CheckBox LoginCbRememberMe;
    @BindView(R.id.SplashBtLogin)
    Button SplashBtLogin;
    @BindView(R.id.SplashBtSign)
    Button SplashBtSign;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        unbinder = ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.SplashBtLogin ,R.id.SplashBtSign,R.id.LoginTvForgetPassword})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.SplashBtLogin:
                login();
                break;
            case R.id.SplashBtSign:
                HelperMethod.getInstance(getActivity()).loadSplashFragments(getActivity(),TAG_SIGN);
                break;
            case R.id.LoginTvForgetPassword:

                break;
        }
    }

    private void login(){
        String phone =LoginEtPhone.getText().toString();
        String password =LoginEtPassword.getText().toString();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        //declare a progress dialog

        HelperMethod.getInstance(getActivity()).initialProgressDialog(getActivity(),getString(R.string.LoginProgressMessage));

        Call<LoginResponse> call = apiInterface.Login(phone,password);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                //get response values
                HelperMethod.getInstance(getActivity()).hideProgressDialog();
                if (response.isSuccessful()) {
                    int status = response.body().getStatus();
                    if(status==0){
                        String msg = response.body().getMsg();
                        HelperMethod.getInstance(getActivity()).callToast(getActivity(),msg);
                    }else if(status==1){

                        storeUserData(response);
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }

                } else {

                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                HelperMethod.getInstance(getActivity()).hideProgressDialog();
                HelperMethod.getInstance(getActivity()).callToast(getActivity(),t.toString());

            }
        });
    }

    public void storeUserData(Response<LoginResponse> response){
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
