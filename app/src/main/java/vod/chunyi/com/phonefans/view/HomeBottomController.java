package vod.chunyi.com.phonefans.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import vod.chunyi.com.phonefans.R;
import vod.chunyi.com.phonefans.utils.Constants;

/**
 * Created by knight on 2017/4/5.
 */

public class HomeBottomController extends LinearLayout implements View.OnClickListener {

    private ImageView mIVSupport;
    private ImageView mIVFlower;
    private ImageView mIVScore;
    private ImageView mIVPause;
    private ImageView mIVList;

    private OnHomeBottomControllerClickListener listener;

    public HomeBottomController(Context context) {
        this(context, null, 0);
    }

    public HomeBottomController(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HomeBottomController(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_bottom_controller, this, true);
        mIVScore = (ImageView) view.findViewById(R.id.iv_addScore);
        mIVSupport = (ImageView) view.findViewById(R.id.iv_support);
        mIVFlower = (ImageView) view.findViewById(R.id.iv_flower);
        mIVList = (ImageView) view.findViewById(R.id.iv_list);
        mIVPause = (ImageView) view.findViewById(R.id.iv_pause);

        mIVSupport.setOnClickListener(this);
        mIVFlower.setOnClickListener(this);
        mIVScore.setOnClickListener(this);
        mIVList.setOnClickListener(this);
        mIVPause.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int positon;
        switch (view.getId()) {
            case R.id.iv_addScore:
                positon = Constants.BOTTOM_CONTROLLER_SCORE;
                break;
            case R.id.iv_flower:
                positon = Constants.BOTTOM_CONTROLLER_FLOWER;
                break;
            case R.id.iv_pause:
                positon = Constants.BOTTOM_CONTROLLER_PAUSE;
                break;
            case R.id.iv_list:
                positon = Constants.BOTTOM_CONTROLLER_LIST;
                break;
            default:
                positon = Constants.BOTTOM_CONTROLLER_SUPPORT;

        }
        if (listener != null) {
            listener.onControllerClick(positon);
        }
    }

    public void setOnHomeBottomControllerClickListener(OnHomeBottomControllerClickListener listener) {
        this.listener = listener;
    }

    interface OnHomeBottomControllerClickListener {

        void onControllerClick(int position);
    }
}
