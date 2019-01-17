package com.ipda3.bankeldam.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.ipda3.bankeldam.R;
import com.ipda3.bankeldam.helper.HelperMethod;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.ipda3.bankeldam.ui.activities.AfterSplashActivity.TAG_LOGIN;

/**
 * A simple {@link Fragment} subclass.
 */
public class AfterSplashFragment extends Fragment {


    @BindView(R.id.SplashIvUseFirst)
    ImageView SplashIvUseFirst;
    @BindView(R.id.SplashIvUseSecond)
    ImageView SplashIvUseSecond;
    @BindView(R.id.SplashBtSkip)
    Button SplashBtSkip;
    Unbinder unbinder;

    private boolean flag =true;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_after_splash, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.SplashBtSkip)
    public void onViewClicked() {
        if (flag ==true){
            SplashIvUseSecond.setVisibility(View.VISIBLE);
            SplashIvUseFirst.setVisibility(View.GONE);
            flag =false;
        }else if (flag ==false){
            SplashIvUseSecond.setVisibility(View.GONE);
            SplashIvUseFirst.setVisibility(View.GONE);
            HelperMethod.getInstance(getActivity()).loadSplashFragments(getActivity(),TAG_LOGIN);
            flag=true;
        }

    }
}
