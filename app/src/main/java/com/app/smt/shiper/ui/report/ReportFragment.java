package com.app.smt.shiper.ui.report;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.app.smt.shiper.R;
import com.app.smt.shiper.data.model.order.Order;
import com.app.smt.shiper.data.model.report.ReportTotal;
import com.app.smt.shiper.di.component.ActivityComponent;
import com.app.smt.shiper.ui.base.BaseFragment;
import com.app.smt.shiper.ui.orderdetail.OrderDetailActivity;
import com.app.smt.shiper.ui.report.adapter.OrderReportAdapter;
import com.app.smt.shiper.util.AppConstants;
import com.app.smt.shiper.util.AppUtils;
import com.app.smt.shiper.util.RecyclerViewOnScrollListener;
import com.borax12.materialdaterangepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ReportFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReportFragment extends BaseFragment implements ReportMvpView, OrderReportAdapter.OrderAdapterListener,
        DatePickerDialog.OnDateSetListener, RadioGroup.OnCheckedChangeListener {

    public static final String TAG = ReportFragment.class.getSimpleName();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    @Inject
    ReportPresenter mPresenter;

    @BindView(R.id.btn_select_date)
    View mBtnSelectDate;

    @BindView(R.id.tv_select_date)
    TextView mTvTime;

    @BindView(R.id.view_empty)
    View mViewEmpty;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.radioGroup)
    RadioGroup mRadioGroup;

    @BindView(R.id.textView8)
    TextView mTvMoney;

    @BindView(R.id.textView15)
    TextView mTvCount;

    private OrderReportAdapter mAdapter;

    private RecyclerViewOnScrollListener mRecyclerViewOnScrollListener;

    private LinearLayoutManager mLayoutManager;

    private boolean hasNext = true;

    private long mFromDate = 0, mToDate = 0;
    private String mWorkflow = AppConstants.ORDER_WORKFLOW_DELIVERY_DONE;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ReportFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MoviesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReportFragment newInstance(String param1, String param2) {
        ReportFragment fragment = new ReportFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_report, container, false);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            ButterKnife.bind(this, view);
            mPresenter.attachView(this);
        }

        return view;
    }

    @Override
    protected void setUp(View view) {
        mAdapter = new OrderReportAdapter(getBaseActivity(), this);
        mLayoutManager = new LinearLayoutManager(getBaseActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        //set load more listener for the RecyclerView adapter
        mRecyclerViewOnScrollListener = new RecyclerViewOnScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                fetchData(current_page, false);
            }
        };
        mRecyclerView.addOnScrollListener(mRecyclerViewOnScrollListener);

        mRadioGroup.setOnCheckedChangeListener(this);

        selectToday();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        mPresenter.detachView();
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        switch(checkedId){
            case R.id.radio_success:
                mWorkflow = AppConstants.ORDER_WORKFLOW_DELIVERY_DONE;
                fetchData(1, true);
                break;
            case R.id.radio_fail:
                mWorkflow = AppConstants.ORDER_WORKFLOW_DELIVERY_FAIL;
                fetchData(1, true);
                break;
        }
    }

    @OnClick(R.id.btn_select_date)
    public void onClickBtnSelectDate() {
        PopupMenu popup = new PopupMenu(getBaseActivity(), mBtnSelectDate, Gravity.END);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.today:
                        selectToday();
                        return true;
                    case R.id.this_week:
                        mTvTime.setText(getResources().getString(R.string.select_time_report_week));
                        Calendar c1 = Calendar.getInstance();
                        mToDate = (c1.getTimeInMillis());
                        c1.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                        c1.set(Calendar.HOUR_OF_DAY, 0);
                        c1.set(Calendar.MINUTE, 0);
                        c1.set(Calendar.SECOND, 0);
                        mFromDate = (c1.getTimeInMillis());
                        fetchData(1, true);
                        return true;
                    case R.id.this_month:
                        mTvTime.setText(getResources().getString(R.string.select_time_report_month));
                        Calendar c2 = Calendar.getInstance();
                        mToDate = (c2.getTimeInMillis());
                        c2.set(Calendar.DAY_OF_MONTH, 1);
                        c2.set(Calendar.HOUR_OF_DAY, 0);
                        c2.set(Calendar.MINUTE, 0);
                        c2.set(Calendar.SECOND, 0);
                        mFromDate = (c2.getTimeInMillis());
                        fetchData(1, true);
                        return true;
                    case R.id.custom:
                        showDateRangePicker();
                        return true;
                    default:
                        return false;
                }
            }
        });
        popup.inflate(R.menu.menu_select_time);
        popup.show();
    }

    private void selectToday() {
        mTvTime.setText(getResources().getString(R.string.select_time_report_title));
        Calendar c = Calendar.getInstance();
        mToDate = (c.getTimeInMillis());
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        mFromDate = (c.getTimeInMillis());
        fetchData(1, true);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth, int yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {
        if(yearEnd < year || monthOfYearEnd < monthOfYear || dayOfMonthEnd < dayOfMonth){
            showDateRangePicker();
        }
        mFromDate = AppUtils.componentTimeToTimestampUTC(year, monthOfYear, dayOfMonth, 0, 0);
        mToDate = AppUtils.componentTimeToTimestampUTC(yearEnd, monthOfYearEnd, dayOfMonthEnd, 23, 59);
        mTvTime.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year + "-" + dayOfMonthEnd + "/" + (monthOfYearEnd + 1) + "/" + yearEnd);
        fetchData(1, true);
    }

    public void showDateRangePicker() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setStartTitle("Từ");
        dpd.setEndTitle("Đến");
        dpd.show(getBaseActivity().getFragmentManager(), "Datepickerdialog");
    }

    private void fetchData(int page, final boolean forceRefresh) {
        if (page == 1) {
            mRecyclerViewOnScrollListener.reset();
            mPresenter.apiGetAllTotalReport(mWorkflow, mFromDate, mToDate);
        }

        if (!isAdded() || (!hasNext && !forceRefresh)) {
            return;
        }

        if (mRecyclerViewOnScrollListener != null) {
            mRecyclerViewOnScrollListener.setCanNextPage(false);
            mRecyclerViewOnScrollListener.current_page = page;
        }

        mPresenter.apiGetListOrderReport(mWorkflow, mFromDate, mToDate, page, forceRefresh);
    }

    @Override
    public void showTotal(ReportTotal reportTotal) {
        mTvMoney.setText(reportTotal.getTotalMoneyStr() + " đ");
        mTvCount.setText(reportTotal.getCountSaleOrder() + "");
    }

    @Override
    public void loadDataError() {
        if (mRecyclerViewOnScrollListener != null)
            mRecyclerViewOnScrollListener.setCanNextPage(true);

        mViewEmpty.setVisibility(View.GONE);
        if (mAdapter == null || mAdapter.getItemCount() == 0) {
            mViewEmpty.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showListOrder(List<Order> list, boolean forceRefresh) {
        if (list.size() == 0) {
            hasNext = false;
        }
        if (mRecyclerViewOnScrollListener != null) {
            mRecyclerViewOnScrollListener.setCanNextPage(true);
        }

        if (forceRefresh) {
            if (list.size() == 0) {
                mViewEmpty.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.GONE);
            } else {
                mViewEmpty.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);
                mAdapter.updateData(list);
            }
        } else {
            mViewEmpty.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
            mAdapter.addData(list);
        }
    }

    @Override
    public void onClickOrderItem(int index, Order order) {
        startActivity(OrderDetailActivity.getStartIntent(getBaseActivity(), order));
    }
}
