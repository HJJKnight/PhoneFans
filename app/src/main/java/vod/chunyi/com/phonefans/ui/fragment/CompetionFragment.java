package vod.chunyi.com.phonefans.ui.fragment;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.List;

import butterknife.BindView;
import vod.chunyi.com.phonefans.R;
import vod.chunyi.com.phonefans.adapter.CompetionAdapter;
import vod.chunyi.com.phonefans.bean.CompetionInfo;
import vod.chunyi.com.phonefans.modle.NetApi;
import vod.chunyi.com.phonefans.ui.fragment.base.BaseFragment;

/**
 * Created by knight on 2017/4/5.
 */

public class CompetionFragment extends BaseFragment {

    @BindView(R.id.competion_recycler_view)
    RecyclerView mRecyclerView;

    private static int ROWS = 10;

    private int page = 1;

    private List<CompetionInfo> data;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            data = (List<CompetionInfo>) msg.obj;
            //Log.e("CompetionFragment","data:"+data.toString());
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.fragment_competion;
    }

    @Override
    public void initData() {
        NetApi.getInstance(getActivity()).postCompetionList(page, ROWS, handler);
    }

    @Override
    public void initViews(View view) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getHolderActivity());

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(new CompetionAdapter(getHolderActivity()));


    }
}
