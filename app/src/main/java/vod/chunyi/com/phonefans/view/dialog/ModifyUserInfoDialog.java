package vod.chunyi.com.phonefans.view.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import vod.chunyi.com.phonefans.R;

/**
 * Created by knight on 2017/4/18.
 */

public class ModifyUserInfoDialog extends BaseDialog implements View.OnClickListener {

    private String mTag;

    private TextView mTvTitle;
    private EditText mEtInput;
    private Button mCancle;
    private Button mConfirm;

    private OnButtonClickListener listener;

    public ModifyUserInfoDialog(Context context) {
        super(context);
    }

    @Override
    protected int getDialogStyleId() {
        return R.style.dialog_ios_style;
    }

    @Override
    protected View getView() {
        View view = LayoutInflater.from(context).inflate(R.layout.view_modify_user_info_dialog, null);

        mTvTitle = (TextView) view.findViewById(R.id.view_modify_user_info_title);
        mEtInput = (EditText) view.findViewById(R.id.view_modify_user_info_input);
        mCancle = (Button) view.findViewById(R.id.view_modify_user_info_cancle);
        mConfirm = (Button) view.findViewById(R.id.view_modify_user_info_confirm);

        initViewEvent();
        return view;
    }

    private void initViewEvent() {
        mCancle.setOnClickListener(this);
        mConfirm.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onClick(v);
        }
    }

    public ModifyUserInfoDialog setTitle(String title) {
        mTvTitle.setText(title);
        return this;
    }

    public String getInputText() {
        return mEtInput.getText().toString();
    }

    public ModifyUserInfoDialog setInputText(String str) {
        mEtInput.setText(str);
        mEtInput.setSelection(str.length());
        return this;
    }


    public void setTag(String tag) {
        this.mTag = tag;
    }

    public String getTag() {
        if (!TextUtils.isEmpty(mTag)) {
            return mTag;
        }
        return null;
    }

    public ModifyUserInfoDialog setOnButtonClickListener(OnButtonClickListener listener) {
        this.listener = listener;
        return this;
    }

    public interface OnButtonClickListener {
        void onClick(View view);
    }
}
