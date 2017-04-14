package vod.chunyi.com.phonefans.ui.fragment;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import butterknife.BindView;
import okhttp3.Request;
import okhttp3.Response;
import vod.chunyi.com.phonefans.R;
import vod.chunyi.com.phonefans.adapter.CompetionAdapter;
import vod.chunyi.com.phonefans.bean.CompetionInfo;
import vod.chunyi.com.phonefans.modle.NetApi;
import vod.chunyi.com.phonefans.modle.NetApiIml;
import vod.chunyi.com.phonefans.modle.NetCode;
import vod.chunyi.com.phonefans.net.SimpleCallback;
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

    @Override
    public int getLayoutId() {
        return R.layout.fragment_competion;
    }

    @Override
    public void initData() {
        NetApi api = NetApiIml.getInstance(getHolderActivity());

        //请求服务器参数信息列表
        api.postCompetionList(page, ROWS, new SimpleCallback<String>() {
            @Override
            public void onFailure(Request request, Exception e) {

            }

            @Override
            public void onSuccess(Response response, String s) {
                try {
                    Gson gson = new Gson();
                    Type listType = new TypeToken<List<CompetionInfo>>() {
                    }.getType();
                    JSONObject jsonObj = new JSONObject(s);

                    data = gson.fromJson(jsonObj.get(NetCode.ROWS).toString(), listType);

                    Log.e("CompetionList-->list", data.toString());

                    initRecyclerView(data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    @Override
    public void initViews(View view) {

    }

    private void initRecyclerView(List<CompetionInfo> data) {

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getHolderActivity());

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getHolderActivity(), DividerItemDecoration.HORIZONTAL));
        mRecyclerView.setAdapter(new CompetionAdapter(getHolderActivity(), data));

    }
}
