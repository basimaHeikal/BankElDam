package com.ipda3.bankeldam.adapter;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ipda3.bankeldam.R;
import com.ipda3.bankeldam.data.model.NotificationSettingsModel;

import java.util.ArrayList;

public class NotSetGroupAdapter extends RecyclerView.Adapter<NotSetGroupAdapter.ItemRowHolder> {

    private ArrayList<NotificationSettingsModel> dataList;
    private Context mContext;


    public NotSetGroupAdapter(Context context, ArrayList<NotificationSettingsModel> dataList) {
        this.dataList = dataList;
        this.mContext = context;
    }

    public NotSetGroupAdapter() {

    }

    @Override
    public ItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_notifications_settings_group, null);
        ItemRowHolder mh = new ItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(ItemRowHolder itemRowHolder, int i) {

        final String sectionName = dataList.get(i).getHeaderTitle();

        ArrayList singleSectionItems = dataList.get(i).getAllItemsInSection();
        itemRowHolder.NotSettingsTvLabel.setText(sectionName);

        NotSetItemAdapter itemListDataAdapter = new NotSetItemAdapter(mContext, singleSectionItems);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(mContext, 2);
        itemRowHolder.NotSettingsRvItem.setLayoutManager(mLayoutManager);
        itemRowHolder.NotSettingsRvItem.setItemAnimator(new DefaultItemAnimator());
        itemRowHolder.NotSettingsRvItem.setAdapter(itemListDataAdapter);


//
//        itemRowHolder.btnMore.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if(sectionName=="Sticks"){
//                    Intent intent2 = new Intent(mContext, AllActivity.class);
//                    intent2.putExtra("Label", "Sticks");
//                    mContext.startActivity(intent2);
//                }
//            }
//        });
    }


    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }

    public class ItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView NotSettingsTvLabel;
        protected RecyclerView NotSettingsRvItem;


        public ItemRowHolder(View view) {
            super(view);

            this.NotSettingsTvLabel = (TextView) view.findViewById(R.id.NotSettingsTvLabel);
            this.NotSettingsRvItem = (RecyclerView) view.findViewById(R.id.NotSettingsRv);
        }
    }
}