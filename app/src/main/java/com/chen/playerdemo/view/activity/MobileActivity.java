package com.chen.playerdemo.view.activity;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chen.playerdemo.Constants;
import com.chen.playerdemo.R;
import com.chen.playerdemo.base.BaseActivity;
import com.chen.playerdemo.bean.tools.MobileData;
import com.chen.playerdemo.contract.MobileContract;
import com.chen.playerdemo.presenter.MobilePresenter;
import com.chen.playerdemo.utils.StringUtils;
import com.chen.playerdemo.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = Constants.PATH_MOBILE)
public class MobileActivity extends BaseActivity<MobilePresenter> implements MobileContract.View {

    @BindView(R.id.clear)
    ImageView clear;
    @BindView(R.id.edt_search)
    EditText mEdtSearch;
    @BindView(R.id.operator)
    TextView mTvOperator;
    @BindView(R.id.city)
    TextView mTvCity;
    @BindView(R.id.cityCode)
    TextView mTvCityCode;
    @BindView(R.id.zipCode)
    TextView mTvZipCode;

    private String value;

    @Override
    public int getLayoutId() {
        return R.layout.activity_mobile;
    }

    @Override
    protected void initView() {
        mPresenter = new MobilePresenter();
        mPresenter.attachView(this);
        mEdtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                value = s.toString();
                if (StringUtils.isEmpty(value)) {
                    clear.setVisibility(View.GONE);
                } else {
                    clear.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        mEdtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                /*判断是否是“搜索”键*/
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (StringUtils.isEmpty(value)) {
                        ToastUtils.show("请输入您要查询的手机号");
                        return true;
                    }
                    //  下面就是大家的业务逻辑
                    mPresenter.searchMobile(Constants.Mob.Mob_key, value);
                    hideKeyboard(MobileActivity.this, mEdtSearch);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void setData(MobileData data) {
        if (data != null && data.getResult() != null) {
            mTvOperator.setText(data.getResult().getOperator());
            mTvCity.setText(data.getResult().getProvince() + " " + data.getResult().getCity());
            mTvCityCode.setText(data.getResult().getCityCode());
            mTvZipCode.setText(data.getResult().getZipCode());
        }
    }

    @OnClick({R.id.back, R.id.clear, R.id.search})
    public void click(View view) {
        int i = view.getId();
        if (i == R.id.back) {
            finish();
        } else if (i == R.id.clear) {
            mEdtSearch.setText("");
        } else if (i == R.id.search) {
            if (StringUtils.isEmpty(value)) {
                ToastUtils.show("请输入您要查询的手机号");
            } else {
                mPresenter.searchMobile(Constants.Mob.Mob_key, value);
            }
        }
    }

    public static void hideKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
