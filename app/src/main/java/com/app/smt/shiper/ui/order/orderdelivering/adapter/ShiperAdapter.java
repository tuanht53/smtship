package com.app.smt.shiper.ui.order.orderdelivering.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.app.smt.shiper.R;
import com.app.smt.shiper.data.model.user.UserProfile;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShiperAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<UserProfile> mDatas;
    private Context mContext;

    private ShiperAdapter.ShiperListener mListener;

    public ShiperAdapter(Context context, ShiperAdapter.ShiperListener listener) {
        mDatas = new ArrayList<>();
        mContext = context;
        mListener = listener;
    }

    private void setDatas(List<UserProfile> datas) {
        mDatas.clear();
        mDatas.addAll(datas);
    }

    public void addData(List<UserProfile> list) {
        mDatas.addAll(list);
        notifyDataSetChanged();
    }

    public void updateData(List<UserProfile> datas) {
        setDatas(datas);
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        mDatas.remove(position);
        notifyItemRemoved(position);
    }

    public void insertItem(UserProfile item) {
        int position = mDatas.size();
        mDatas.add(position, item);
        notifyItemInserted(position);
    }

    public void updateItem(UserProfile item, int position) {
        mDatas.set(position, item);
        notifyItemChanged(position);
    }

    private LayoutInflater getLayoutInflater() {
        return LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = getLayoutInflater().inflate(R.layout.recyclerview_shiper_item, parent, false);
        return new ShiperAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final ShiperAdapter.ViewHolder viewHolder = (ShiperAdapter.ViewHolder) holder;
        viewHolder.bind(mDatas.get(position));

        viewHolder.mBtnSwap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onSelectSwapShipper(mDatas.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name)
        TextView mTvName;

        @BindView(R.id.tv_phone)
        TextView mTvPhone;

        @BindView(R.id.btn_swap)
        Button mBtnSwap;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bind(UserProfile model) {
            mTvName.setText(model.getFullName());
            mTvPhone.setText(model.getUserName());
        }
    }

    public interface ShiperListener {

        void onSelectSwapShipper(UserProfile userProfile);

    }

}
