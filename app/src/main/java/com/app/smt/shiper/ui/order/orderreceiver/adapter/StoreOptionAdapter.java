package com.app.smt.shiper.ui.order.orderreceiver.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.smt.shiper.R;
import com.app.smt.shiper.data.model.store.Store;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StoreOptionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Store> mListData;
    private StoreOptionAdapter.StoreOptionAdapterListener mListener;
    private Context mContext;
    private String mSelectedID;

    public StoreOptionAdapter(Context context, StoreOptionAdapter.StoreOptionAdapterListener listener) {
        this.mListData = new ArrayList<>();
        this.mListener = listener;
        this.mContext = context;
    }

    private void setDatas(List<Store> datas) {
        mListData.clear();
        mListData.addAll(datas);
    }

    public void updateData(List<Store> datas) {
        setDatas(datas);
        notifyDataSetChanged();
    }

    public void setSelectedID(String id) {
        mSelectedID = id;
    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_store_item, parent, false);

        return new StoreOptionAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final StoreOptionAdapter.ViewHolder viewHolder = (StoreOptionAdapter.ViewHolder) holder;
        viewHolder.bind(mListData.get(holder.getAdapterPosition()));

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClickStoreOptionItem(mListData.get(holder.getAdapterPosition()));
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_store)
        TextView mTvStore;

        @BindView(R.id.img_check)
        ImageView mBtnChecked;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bind(Store store) {
            mTvStore.setText(store.getStoreName());
            if (TextUtils.isEmpty(mSelectedID)) {
                if (TextUtils.isEmpty(store.getTxtId())) {
                    mBtnChecked.setVisibility(View.VISIBLE);
                } else {
                    mBtnChecked.setVisibility(View.INVISIBLE);
                }
            } else {
                if (mSelectedID.equals(store.getTxtId())) {
                    mBtnChecked.setVisibility(View.VISIBLE);
                } else {
                    mBtnChecked.setVisibility(View.INVISIBLE);
                }
            }
        }
    }

    public interface StoreOptionAdapterListener {
        void onClickStoreOptionItem(Store store);
    }
}
