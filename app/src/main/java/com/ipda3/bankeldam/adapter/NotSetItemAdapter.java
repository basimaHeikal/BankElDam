package com.ipda3.bankeldam.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ipda3.bankeldam.R;
import com.ipda3.bankeldam.data.model.cities.Datum;

import java.util.ArrayList;

public class NotSetItemAdapter extends RecyclerView.Adapter<NotSetItemAdapter.SingleItemRowHolder> {

    private ArrayList<Datum> itemsList;
    private Context mContext;


    public NotSetItemAdapter(Context context, ArrayList<Datum> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;
    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_notifications_settings, null);
        SingleItemRowHolder mh = new SingleItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(final SingleItemRowHolder holder, int i) {

        final Datum singleItem = itemsList.get(i);

        holder.NotSettingsTvCbLabel.setText(singleItem.getName());


    }


    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView NotSettingsTvCbLabel;


        public SingleItemRowHolder(View view) {
            super(view);

            this.NotSettingsTvCbLabel = (TextView) view.findViewById(R.id.NotSettingsTvCbLabel);


        }
    }
}
