package com.app.smt.shiper.ui.order.orderdelivering.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.smt.shiper.R;
import com.app.smt.shiper.data.model.order.Order;
import com.app.smt.shiper.util.AppConstants;
import com.app.smt.shiper.util.AppUtils;
import com.app.smt.shiper.util.FilterNumberString;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderDeliveringAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Order> mListData;
    private OrderDeliveringAdapter.OrderAdapterListener mListener;
    private Context mContext;

    public OrderDeliveringAdapter(Context context, OrderDeliveringAdapter.OrderAdapterListener listener) {
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

        return new OrderDeliveringAdapter.ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder,int position) {
        final OrderDeliveringAdapter.ViewHolder viewHolder = (OrderDeliveringAdapter.ViewHolder) holder;
        viewHolder.bind(mListData.get(holder.getAdapterPosition()), holder.getAdapterPosition());

        viewHolder.mBtnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onDeliveryOrderSuccess(holder.getAdapterPosition(), mListData.get(holder.getAdapterPosition()));
            }
        });
        viewHolder.mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onDeliveryOrderFail(holder.getAdapterPosition(), mListData.get(holder.getAdapterPosition()));
            }
        });
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClickOrderItem(holder.getAdapterPosition(), mListData.get(holder.getAdapterPosition()));
            }
        });
        viewHolder.mBtnSwapShip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onSwapShipper(holder.getAdapterPosition(), mListData.get(holder.getAdapterPosition()));
            }
        });
        viewHolder.mBtnStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupPriority(viewHolder.mBtnStatus, mListData.get(holder.getAdapterPosition()));
            }
        });
    }

    private void showPopupPriority(View view, final Order order) {
        PopupMenu popup = new PopupMenu(mContext, view, Gravity.END);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.normal:
                        mListener.onChangePriority(order, AppConstants.PRIORITY_NORMAL);
                        return true;
                    case R.id.high:
                        mListener.onChangePriority(order, AppConstants.PRIORITY_HIGH);
                        return true;
                    case R.id.highest:
                        mListener.onChangePriority(order, AppConstants.PRIORITY_HIGHEST);
                        return true;
                    default:
                        return false;
                }
            }
        });
        popup.inflate(R.menu.menu_select_priority);
        popup.show();
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

        @BindView(R.id.btn_swap_ship)
        TextView mBtnSwapShip;

        @BindView(R.id.btn_status)
        TextView mBtnStatus;

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
            mBtnOK.setText(mContext.getResources().getString(R.string.btn_order_success));
            mBtnCancel.setText(mContext.getResources().getString(R.string.btn_order_fail));
            mBtnSwapShip.setVisibility(View.VISIBLE);
            mTvTime.setText(Html.fromHtml(mContext.getResources().getString(R.string.history_item_time_format,
                    AppUtils.getDateFromTimestamp(order.getUpdateDate(), "HH:mm"),
                    AppUtils.getDateFromTimestamp(order.getUpdateDate(), "dd/MM/yyyy"))));
            mBtnStatus.setVisibility(View.VISIBLE);
            mBtnStatus.setText(mContext.getResources().getString(R.string.order_priority));
            if (AppConstants.PRIORITY_HIGHEST == order.getSaleOrderInformationCommon().getPriority()) {
                mBtnStatus.setBackground(mContext.getResources().getDrawable(R.drawable.bg_status_red));
            } else if (AppConstants.PRIORITY_HIGH == order.getSaleOrderInformationCommon().getPriority()) {
                mBtnStatus.setBackground(mContext.getResources().getDrawable(R.drawable.bg_status_yellow));
            }  else {
                mBtnStatus.setBackground(mContext.getResources().getDrawable(R.drawable.bg_status_blue));
            }
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
        void onDeliveryOrderSuccess(int index, Order order);
        void onDeliveryOrderFail(int index, Order order);
        void onSwapShipper(int index, Order order);
        void onChangePriority(Order order, int priority);
    }
}