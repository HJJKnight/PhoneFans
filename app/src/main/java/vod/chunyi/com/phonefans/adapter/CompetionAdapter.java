package vod.chunyi.com.phonefans.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import vod.chunyi.com.phonefans.R;
import vod.chunyi.com.phonefans.bean.CompetionInfo;
import vod.chunyi.com.phonefans.ui.activity.CompetionDetailActivity;
import vod.chunyi.com.phonefans.ui.activity.base.BaseActivity;
import vod.chunyi.com.phonefans.utils.ToastUtil;

/**
 * TODO recyclerview下拉刷新
 * Created by knight on 2017/4/13.
 */

public class CompetionAdapter extends RecyclerView.Adapter<CompetionAdapter.CompetionViewHolder> {

    private Context mContext;

    private List<CompetionInfo> mData;

    private LayoutInflater mInflater;

    public CompetionAdapter(BaseActivity context, List<CompetionInfo> data) {
        this.mContext = context;
        mData = data;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public CompetionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = mInflater.inflate(R.layout.item_competion, parent, false);

        return new CompetionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CompetionViewHolder holder, final int position) {
        if (!mData.isEmpty() && mData.size() > 0) {
            Glide.with(mContext)
                    .load(mData.get(position).getCoverPicPath())
                    .error(R.mipmap.icon_load_fail)
                    .crossFade()
                    .into(holder.ivCovery);
            holder.tvName.setText(mData.get(position).getMatchName());

            // 如果活动正在进行 让正在进行的textview显示
            if ("1".equals(mData.get(position).getIsUsed())) {
                holder.tvStatus.setVisibility(View.VISIBLE);
            }
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ToastUtil.showShort(mContext, "position:" + position + ",name:" + mData.get(position).getMatchName());

                CompetionDetailActivity.startActivity(mContext,
                        mData.get(position).getMatchNo(),
                        mData.get(position).getCoverPicPath(),
                        mData.get(position).getMatchName());

            }
        });
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    static class CompetionViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_competion_Item)
        ImageView ivCovery;
        @BindView(R.id.tv_competion_name)
        TextView tvName;
        @BindView(R.id.tv_competion_state)
        TextView tvStatus;

        public CompetionViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

    }
}
