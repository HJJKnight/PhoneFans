package vod.chunyi.com.phonefans.ui.fragment.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import vod.chunyi.com.phonefans.ui.activity.base.BaseActivity;


/**
 * Created by knight on 2017/4/5.
 */

public abstract class BaseFragment extends Fragment {

    protected BaseActivity mActivity;

    public abstract int getLayoutId();

    public abstract void initViews(View view);

    protected BaseActivity getHolderActivity() {
        return mActivity;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (BaseActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(this,view);
        initViews(view);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
