package vod.chunyi.com.phonefans.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import vod.chunyi.com.phonefans.R;
import vod.chunyi.com.phonefans.bean.CompetionDetailInfo;
import vod.chunyi.com.phonefans.bean.CompetionInfo;
import vod.chunyi.com.phonefans.ui.activity.CompetionDetailActivity;
import vod.chunyi.com.phonefans.ui.activity.base.BaseActivity;
import vod.chunyi.com.phonefans.utils.ToastUtil;

/**
 * TODO recyclerview下拉刷新
 * Created by knight on 2017/4/13.
 */

public class CompetionDetailAdapter extends RecyclerView.Adapter<CompetionDetailAdapter.CompetionDetailViewHolder> {

    private Context mContext;

    private List<CompetionDetailInfo> mData;

    private LayoutInflater mInflater;

    public CompetionDetailAdapter(BaseActivity context, List<CompetionDetailInfo> data) {
        this.mContext = context;
        mData = data;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public CompetionDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = mInflater.inflate(R.layout.item_competion_detail, parent, false);

        return new CompetionDetailViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CompetionDetailViewHolder holder, final int position) {
        if (!mData.isEmpty() && mData.size() > 0) {
            Glide.with(mContext)
                    .load(mData.get(position).getCoverImgPath())
                    .error(R.mipmap.icon_load_fail)
                    .crossFade()
                    .into(holder.ivCovery);
            holder.tvUserName.setText(mData.get(position).getUserName());

            holder.tvSongName.setText(mData.get(position).getSongName());

        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showShort(mContext, "position:" + position + ",name:" + mData.get(position).getUserName());

            }
        });
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    static class CompetionDetailViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_competion_detail_covery)
        ImageView ivCovery;
        @BindView(R.id.tv_competion_detail_user)
        TextView tvUserName;
        @BindView(R.id.tv_competion_detail_song)
        TextView tvSongName;

        public CompetionDetailViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

    }
}
