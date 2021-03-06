package com.app.smt.shiper.ui.order.orderreceiver.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.smt.shiper.R;
import com.app.smt.shiper.data.model.order.Order;
import com.app.smt.shiper.util.AppUtils;
import com.app.smt.shiper.util.FilterNumberString;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderReceiverAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Order> mListData;
    private OrderReceiverAdapter.OrderAdapterListener mListener;
    private Context mContext;

    public OrderReceiverAdapter(Context context, OrderReceiverAdapter.OrderAdapterListener listener) {
        this.mListData = new ArrayList<>();;
        this.mListener = listener;
        this.mContext = context;
    }

    private void setDatas(List<Order> datas) {
        mListData.clear();
        mListData.addAll(datas);
    }

    public void updateData(List<Order> datas) {
        setDatas(datas);
        notifyDataSetChanged();
    }

    public void addData(List<Order> data) {
        mListData.addAll(data);
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        mListData.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    public void updateItem(Order item, int position) {
        mListData.set(position, item);
        notifyItemChanged(position);
    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_order_item, parent, false);

        return new OrderReceiverAdapter.ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder,int position) {
        final OrderReceiverAdapter.ViewHolder viewHolder = (OrderReceiverAdapter.ViewHolder) holder;
        viewHolder.bind(mListData.get(holder.getAdapterPosition()), holder.getAdapterPosition());

        viewHolder.mBtnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClickOrderDelivery(holder.getAdapterPosition(), mListData.get(holder.getAdapterPosition()));
            }
        });
        viewHolder.mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClickOrderMoveStock(holder.getAdapterPosition(), mListData.get(holder.getAdapterPosition()));
            }
        });
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClickOrderItem(holder.getAdapterPosition(), mListData.get(holder.getAdapterPosition()));
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.btn_ok)
        TextView mBtnOK;

        @BindView(R.id.btn_cancel)
        TextView mBtnCancel;

        @BindView(R.id.tv_time)
        TextView mTvTime;

        @BindView(R.id.tv_dropoff_title)
        TextView mTvDropOffTitle;

        @BindView(R.id.tv_dropoff_des)
        TextView mTvDropOffDes;

        @BindView(R.id.tv_price_value)
        TextView mTvPrice;

        @BindView(R.id.tv_price_unit)
        TextView mTvPriceUnit;

        @BindView(R.id.tv_note)
        TextView mTvNote;

        @BindView(R.id.tv_count)
        TextView mTvCount;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bind(Order order, int index) {
            mTvDropOffTitle.setText(order.getCustomerInformation().getCustUserFullName() + " - " + order.getCustomerInformation().getCustUserName());
            if (order.getAddressForDelivery() != null)
                mTvDropOffDes.setText(order.getAddressForDelivery().getAddress());
            mTvPrice.setText(FilterNumberString.filterMoney(order.getSaleOrderInformationCommon().getTotalMoney() + ""));
            mBtnOK.setText(mContext.getResources().getString(R.string.btn_order_delivering));
            mBtnCancel.setText(mContext.getResources().getString(R.string.btn_order_move_stock));
            mTvTime.setText(Html.fromHtml(mContext.getResources().getString(R.string.history_item_time_format,
                    AppUtils.getDateFromTimestamp(order.getUpdateDate(), "HH:mm"),
                    AppUtils.getDateFromTimestamp(order.getUpdateDate(), "dd/MM/yyyy"))));

            if (!TextUtils.isEmpty(order.getNote())) {
                mTvNote.setText(Html.fromHtml(mContext.getResources().getString(R.string.order_note, order.getNote())));
            } else {
                mTvNote.setText("");
            }
            mTvCount.setText("" + (index + 1));

        }
    }

    public interface OrderAdapterListener {
        void onClickOrderItem(int index, Order order);
        void onClickOrderDelivery(int index, Order order);
        void onClickOrderMoveStock(int index, Order order);

    }
}