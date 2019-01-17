package com.ipda3.bankeldam.ui.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.ipda3.bankeldam.R;
import com.ipda3.bankeldam.data.local.SharedPrefManagerUser;
import com.ipda3.bankeldam.data.model.report.ReportResponse;
import com.ipda3.bankeldam.data.rest.ApiClient;
import com.ipda3.bankeldam.data.rest.ApiInterface;
import com.ipda3.bankeldam.helper.HelperMethod;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ReportFragment extends Fragment {


    @BindView(R.id.ReportEtMessage)
    EditText ReportEtMessage;
    @BindView(R.id.ReportBtSend)
    Button ReportBtSend;
    Unbinder unbinder;

    String api_token ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_report, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        api_token = SharedPrefManagerUser.getInstance(getActivity()).getKeyApiToken();

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
        getActivity().setTitle("إبلاغ");
    }


    @OnClick(R.id.ReportBtSend)
    public void onViewClicked() {
        sendReport();
    }

    private void sendReport(){
        String message = ReportEtMessage.getText().toString();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        HelperMethod.getInstance(getActivity()).initialProgressDialog(getActivity(),getString(R.string.ReportProgressMessage));

        Call<ReportResponse> call = apiInterface.Report(api_token,message);
        call.enqueue(new Callback<ReportResponse>() {
            @Override
            public void onResponse(Call<ReportResponse> call, Response<ReportResponse> response) {
                //get response values
                HelperMethod.getInstance(getActivity()).hideProgressDialog();
                if (response.isSuccessful()) {
                    int status = response.body().getStatus();
                    String msg = response.body().getMsg();
                    if (status==0){
                        HelperMethod.getInstance(getActivity()).callToast(getActivity(),msg);
                    }else if(status==1){
                        HelperMethod.getInstance(getActivity()).callToast(getActivity(),msg);
                    }

                } else {

                }
            }

            @Override
            public void onFailure(Call<ReportResponse> call, Throwable t) {
                HelperMethod.getInstance(getActivity()).hideProgressDialog();
            }
        });
    }
}
