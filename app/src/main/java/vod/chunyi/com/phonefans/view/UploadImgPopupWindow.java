package vod.chunyi.com.phonefans.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import vod.chunyi.com.phonefans.R;


/**
 * Created by knight on 2017/4/17.
 */

public class UploadImgPopupWindow extends PopupWindow {

    private Context mContext;

    private View view;

    private Button mTakePhoto, mPickPhoto, mCancel;

    public UploadImgPopupWindow(Context mContext, View.OnClickListener listener) {
        init(mContext, listener);
    }

    private void init(Context mContext, View.OnClickListener listener) {
        this.mContext = mContext;
        this.view = LayoutInflater.from(mContext).inflate(R.layout.view_upload_img_pop, null);

        mTakePhoto = (Button) view.findViewById(R.id.pop_btn_take_photo);
        mPickPhoto = (Button) view.findViewById(R.id.pop_btn_pick_photo);
        mCancel = (Button) view.findViewById(R.id.pop_btn_cancel);

        // 取消按钮
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        // 设置按钮监听
        mPickPhoto.setOnClickListener(listener);
        mTakePhoto.setOnClickListener(listener);

        // 设置外部可点击
        this.setOutsideTouchable(true);
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        /*this.view.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = view.findViewById(R.id.pop_layout).getTop();

                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });*/

        setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    return true;
                }
                return false;
            }
        });

		/* 设置弹出窗口特征 */
        // 设置视图
        this.setContentView(this.view);
        // 获取屏幕高度
        WindowManager wm = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();

        this.setHeight((int) (height * 0.3));
        this.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);
        // 设置弹出窗体可点击
        this.setFocusable(true);

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xbfffffff);
        // 设置弹出窗体的背景
        this.setBackgroundDrawable(dw);

        // 设置弹出窗体显示时的动画，从底部向上弹出
        this.setAnimationStyle(R.style.UploadImgPopwindow);
    }

}
