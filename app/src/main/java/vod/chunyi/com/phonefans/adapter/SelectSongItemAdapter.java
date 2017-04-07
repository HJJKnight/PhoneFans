package vod.chunyi.com.phonefans.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import vod.chunyi.com.phonefans.R;
import vod.chunyi.com.phonefans.utils.Constants;

/**
 * Created by knight on 2017/4/6.
 */
public class SelectSongItemAdapter extends RecyclerView.Adapter<SelectSongItemAdapter.SelectSongItemViewHolder> {

    private int itemCounts;

    private Context mContext;

    public SelectSongItemAdapter(Context context, int type) {
        itemCounts = type * Constants.SELECT_SONG_ITEMS_ROWS;
        mContext = context;

    }

    @Override
    public SelectSongItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_select_song_category, parent, false);
        return new SelectSongItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SelectSongItemViewHolder holder, final int position) {

        Glide.with(mContext)
                .load("https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png").into(holder.image);

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
                Toast.makeText(mContext, "position:" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemCounts;
    }

    static class SelectSongItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_select_song_item)
        ImageView image;

        public SelectSongItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
