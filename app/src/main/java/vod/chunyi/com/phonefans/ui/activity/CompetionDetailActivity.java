package vod.chunyi.com.phonefans.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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
import vod.chunyi.com.phonefans.adapter.CompetionDetailAdapter;
import vod.chunyi.com.phonefans.bean.CompetionDetailInfo;
import vod.chunyi.com.phonefans.modle.NetApi;
import vod.chunyi.com.phonefans.modle.NetApiIml;
import vod.chunyi.com.phonefans.modle.NetCode;
import vod.chunyi.com.phonefans.net.SimpleCallback;
import vod.chunyi.com.phonefans.ui.activity.base.BaseActivity;
import vod.chunyi.com.phonefans.utils.Constants;
import vod.chunyi.com.phonefans.utils.StatusBarUtils;

/**
 * Created by knight on 2017/4/14.
 */

public class CompetionDetailActivity extends BaseActivity {

    @BindView(R.id.iv_competion_covery)
    ImageView mIvCompetionCovery;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout mToolbarLayout;

    @BindView(R.id.competion_detail_recycler_view)
    RecyclerView mRecyclerView;

    private List<CompetionDetailInfo> mData;
    //比赛编号
    private int mMatchNum;
    //封面图片地址
    private String mImgPath;

    private int page = 1;

    private int rows = 12;

    @Override
    public int getLayoutId() {
        return R.layout.activity_competion_detail;
    }

    @Override
    public void initViews() {

        StatusBarUtils.setImage(this);

        mToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(this, R.color.result_text));
        mToolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(this, R.color.result_text));
        Glide.with(this).load(mImgPath).error(R.mipmap.icon_load_fail).into(mIvCompetionCovery);
    }

    @Override
    public void initVaribles(Intent intent) {
        if (intent != null) {
            mMatchNum = intent.getIntExtra(Constants.MATCH_NUM, -1);
            mImgPath = intent.getStringExtra(Constants.MATCH_IMG_PATH);
        }

        NetApi api = NetApiIml.getInstance(this);
        api.postCompetionDetailList(page, rows, mMatchNum, new SimpleCallback<String>() {
            @Override
            public void onFailure(Request request, Exception e) {

            }

            @Override
            public void onSuccess(Response response, String s) {
                try {
                    Gson gson = new Gson();
                    Type listType = new TypeToken<List<CompetionDetailInfo>>() {
                    }.getType();
                    JSONObject jsonObj = new JSONObject(s);

                    mData = gson.fromJson(jsonObj.get(NetCode.ROWS).toString(), listType);

                    Log.e("postCompetionListDetail", s);

                    initRecyclerView(mData);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public static void startActivity(Context context, int matchNo, String imgPath) {
        Intent intent = new Intent(context, CompetionDetailActivity.class);
        intent.putExtra(Constants.MATCH_NUM, matchNo);
        intent.putExtra(Constants.MATCH_IMG_PATH, imgPath);
        context.startActivity(intent);
    }

    private void initRecyclerView(List<CompetionDetailInfo> data) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.HORIZONTAL));
        mRecyclerView.setAdapter(new CompetionDetailAdapter(this, data));
    }
}
