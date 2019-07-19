package com.chen.playerdemo.view.activity;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
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
import com.chen.playerdemo.bean.tools.DictionaryData;
import com.chen.playerdemo.contract.DictionaryContract;
import com.chen.playerdemo.presenter.DictionaryPresenter;
import com.chen.playerdemo.utils.StringUtils;
import com.chen.playerdemo.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = Constants.PATH_DICTIONARY)
public class DictionaryActivity extends BaseActivity<DictionaryPresenter> implements DictionaryContract.View {

    @BindView(R.id.clear)
    ImageView clear;
    @BindView(R.id.edt_search)
    EditText mEdtSearch;
    @BindView(R.id.pinyin)
    TextView mTvPinyin;
    @BindView(R.id.brief)
    TextView mTvBrief;
    @BindView(R.id.detail)
    TextView mTvDetail;
    @BindView(R.id.bushou)
    TextView mTvBushou;
    @BindView(R.id.bihua)
    TextView mTvBihua;
    @BindView(R.id.wubi)
    TextView mTvWubi;

    private String value;

    @Override
    public int getLayoutId() {
        return R.layout.activity_dictionary;
    }

    @Override
    protected void initView() {
        mPresenter = new DictionaryPresenter();
        mPresenter.attachView(this);

        /**
         * EditText只能输入中文
         */
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if (!isChinese(source.charAt(i))) {
                        return "";
                    }
                }
                return null;
            }
        };
        mEdtSearch.setFilters(new InputFilter[]{filter, new InputFilter.LengthFilter(1)});
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
                        ToastUtils.show("请输入您要查询的汉字");
                        return true;
                    }
                    //  下面就是大家的业务逻辑
                    mPresenter.getDictionary(Constants.Mob.Mob_key, value);
                    hideKeyboard(DictionaryActivity.this, mEdtSearch);
                    return true;
                }
                return false;
            }
        });
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
                ToastUtils.show("请输入您要查询的汉字");
            } else {
                mPresenter.getDictionary(Constants.Mob.Mob_key, value);
            }
        }
    }

    @Override
    public void setData(DictionaryData data) {
        if (data != null && data.getResult() != null) {
            mTvPinyin.setText(data.getResult().getPinyin());
            mTvBrief.setText(data.getResult().getBrief());
            mTvDetail.setText(data.getResult().getDetail());
            mTvBushou.setText(data.getResult().getBushou());
            mTvBihua.setText(data.getResult().getBihua());
            mTvWubi.setText(data.getResult().getWubi());
        }
    }

    /**
     * 只能输入中文的判断
     *
     * @param c
     * @return
     */
    private boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    public static void hideKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
