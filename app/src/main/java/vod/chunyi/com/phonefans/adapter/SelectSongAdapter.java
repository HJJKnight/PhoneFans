package vod.chunyi.com.phonefans.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import javax.crypto.spec.PSource;

import butterknife.BindView;
import butterknife.ButterKnife;
import vod.chunyi.com.phonefans.R;
import vod.chunyi.com.phonefans.ui.activity.base.BaseActivity;
import vod.chunyi.com.phonefans.utils.Constants;

/**
 * Created by knight on 2017/4/6.
 */
public class SelectSongAdapter extends RecyclerView.Adapter<SelectSongAdapter.SelectSongViewHolder> {

    private static final int RECOMMENDS = 0;
    private static final int SINGERS = 1;
    private static final int SONGLIST = 2;

    private Context mContext;
    private String[] mTitles;

    public SelectSongAdapter(BaseActivity activity) {
        this.mContext = activity;
        mTitles = activity.getResources().getStringArray(R.array.category_title);
    }

    @Override
    public SelectSongViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_select_song, parent, false);

        return new SelectSongViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SelectSongViewHolder holder, int position) {

        SelectSongItemAdapter itemAdapter;
        switch (position) {
            case RECOMMENDS:
                itemAdapter = new SelectSongItemAdapter(mContext, Constants.SELECT_SONG_RECOMMEND);
                break;
            case SINGERS:
                itemAdapter = new SelectSongItemAdapter(mContext, Constants.SELECT_SONG_SINGER);
                break;
            case SONGLIST:
                itemAdapter = new SelectSongItemAdapter(mContext, Constants.SELECT_SONG_SONGLIST);
                break;
            default:
                itemAdapter = new SelectSongItemAdapter(mContext, 0);
                break;
        }
        holder.rvItems.setLayoutManager(new GridLayoutManager(mContext, Constants.SELECT_SONG_ITEMS_ROWS));
        holder.rvItems.setAdapter(itemAdapter);
        holder.tvCategory.setText(mTitles[position]);
    }

    @Override
    public int getItemViewType(int position) {

        if (position == 0) {
            return RECOMMENDS;
        } else if (position == 1) {
            return SINGERS;
        } else if (position == 2) {
            return SONGLIST;
        } else {
            return 3;
        }
    }

    @Override
    public int getItemCount() {
        return Constants.SELECT_SONG_ITEMS;
    }

    static class SelectSongViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.leftTv)
        TextView tvCategory;
        @BindView(R.id.select_song_item)
        RecyclerView rvItems;

        public SelectSongViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
