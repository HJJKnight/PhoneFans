package vod.chunyi.com.phonefans.ui.fragment;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.BindView;
import vod.chunyi.com.phonefans.R;
import vod.chunyi.com.phonefans.adapter.SelectSongAdapter;
import vod.chunyi.com.phonefans.ui.fragment.base.BaseFragment;

/**
 * Created by knight on 2017/4/5.
 */

public class SelectSongFragment extends BaseFragment {

    @BindView(R.id.select_song_recycler_view)
    RecyclerView mRecyclerView;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_song;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initViews(View view) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getHolderActivity());

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getHolderActivity(),DividerItemDecoration.HORIZONTAL));
        mRecyclerView.setAdapter(new SelectSongAdapter(getHolderActivity()));
    }

}
