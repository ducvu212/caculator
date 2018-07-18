package com.example.ducvu212.caculator;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class CaculatorFragment extends Fragment implements View.OnClickListener {

    private static final String PREF_RESULT = "com.example.ducvu212.caculator";
    private final String TAG = CaculatorFragment.class.getSimpleName();
    private int[] mIds = {R.id.button_zero, R.id.button_one, R.id.button_two, R.id.button_three
            , R.id.button_four, R.id.button_five, R.id.button_six, R.id.button_seven, R.id.button_eight
            , R.id.button_nine, R.id.button_ac, R.id.button_sign, R.id.button_delete, R.id.button_div
            , R.id.button_multi, R.id.button_sub, R.id.button_add, R.id.button_result, R.id.button_dot};
    private TextView mTextviewResult;
    private String mResult = "", mOperator = "", mNothing;
    private Float mPara1, mPara2;
    private boolean mCheckClick;

    public CaculatorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_caculator, container, false);
        findViewByIds(view);
        mNothing = getResources().getString(R.string.nothing);
        setHasOptionsMenu(true);
        SharedPreferences mSharePre = getContext().getSharedPreferences("com.example.ducvu212.caculator"
                , Context.MODE_PRIVATE);
        mTextviewResult.setText(mSharePre.getString("result", ""));
        mPara1 = Float.parseFloat(mSharePre.getString("result", ""));
        return view;
    }

    private void findViewByIds(View view) {
        mTextviewResult = view.findViewById(R.id.editext_result);
        for (int id : mIds) {
            view.findViewById(id).setOnClickListener(this);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int mId = item.getItemId();
        switch (mId) {
            case R.id.item_clear:
                Log.d(TAG, "clearrrrrrrrr");
                mResult = mNothing;
                mOperator = mNothing;
                mPara1 = 0.f ;
                mPara2 = 0.f ;
                mTextviewResult.setText(getResources().getString(R.string.zero));
                break;

            case R.id.item_save_last_result:
                Log.d(TAG, "saveeeeeeeeeeee");
                SharedPreferences mSharePre = getContext().getSharedPreferences(PREF_RESULT
                        , Context.MODE_PRIVATE);
                SharedPreferences.Editor mEditor = mSharePre.edit();
                mEditor.clear();
                if (mResult.equals("")) {
                    mResult = String.valueOf(mPara1);
                }
                if ((mResult.indexOf(getResources().getString(R.string.zero))
                        == mResult.indexOf(getResources().getString(R.string.dot)) + 1)
                        && mResult.indexOf("0") == mResult.length() - 1) {
                    mResult = mResult.substring(0, mResult.indexOf("."));
                }
                mEditor.putString("result", mResult);
                mEditor.apply();
                break;

            default:
        }
        return super.onOptionsItemSelected(item);
    }
    private void clickResultButton() {
        if (mPara2 != null) {
            if (mOperator.equals(getResources().getString(R.string.add))) {
                mResult = String.valueOf(mPara1 + mPara2);
            } else if (mOperator.equals(getResources().getString(R.string.sub))) {
                mResult = String.valueOf(mPara1 - mPara2);
            } else if (mOperator.equals(getResources().getString(R.string.multi))) {
                mResult = String.valueOf(mPara1 * mPara2);
            } else if (mOperator.equals(getResources().getString(R.string.div))){
                mResult = String.valueOf(mPara1 / mPara2);
            }
            if (!mResult.equals("")) {
                if ((mResult.indexOf(getResources().getString(R.string.zero)
                        , mResult.indexOf(getResources().getString(R.string.dot)))
                        == mResult.indexOf(getResources().getString(R.string.dot)) + 1)
                        && mResult.indexOf(getResources().getString(R.string.zero)
                        , mResult.indexOf(getResources().getString(R.string.dot))) == mResult.length() - 1) {
                    mTextviewResult.setText(mResult.substring(0, mResult.indexOf(".")));
                } else {
                    mTextviewResult.setText(mResult);
                }
                mPara1 = Float.valueOf(mResult);
            }
            mResult = mNothing;
        }
    }
    @Override
    public void onClick(View v) {
//        if (!mOperator.equals(mNothing)) {
//            mResult = mNothing ;
//            mOperator = mNothing;
//            mTextviewResult.setText(mNothing);
//        }
        switch (v.getId()) {
            case R.id.button_add:
                if (mCheckClick) {
                    mOperator = mNothing ;
                }
                if (!mResult.equals("") && mOperator.equals("")) mPara1 = Float.valueOf(mResult);
                clickResultButton();
//                result(getResources().getString(R.string.add));
                mResult = mNothing;
                mOperator = getResources().getString(R.string.add);
                break;

            case R.id.button_sub:
                if (mCheckClick) {
                    mOperator = mNothing ;
                }
                if (!mResult.equals("") && mOperator.equals("")) mPara1 = Float.valueOf(mResult);
                clickResultButton();
//                result(getResources().getString(R.string.sub));
                mResult = mNothing;
                mOperator = getResources().getString(R.string.sub);
                break;

            case R.id.button_multi:
                if (mCheckClick) {
                    mOperator = mNothing ;
                }
                if (!mResult.equals("") && mOperator.equals("")) mPara1 = Float.valueOf(mResult);
                clickResultButton();
//                result(getResources().getString(R.string.multi));
                mResult = mNothing;
                mOperator = getResources().getString(R.string.multi);
                break;

            case R.id.button_div:
                if (mCheckClick) {
                    mOperator = mNothing ;
                }
                if (!mResult.equals("") && mOperator.equals("")) mPara1 = Float.valueOf(mResult);
                clickResultButton();
//                result(getResources().getString(R.string.div));
                mResult = mNothing;
                mOperator = getResources().getString(R.string.div);
                break;
            case R.id.button_result:
//                if (mPara1 != 0 && mPara2 == null)
                if (!mResult.equals("")) mPara2 = Float.valueOf(mResult);
                clickResultButton();
                mCheckClick = true;

                break;

            case R.id.button_delete:
                if (mResult.length() <= 1 && mPara1 <= 0) {
                    mResult = mNothing;
                    mOperator = mNothing;
                    mPara1 = 0.f ;
                    mPara2 = 0.f ;
                    mTextviewResult.setText(getResources().getString(R.string.zero));
                } else {
                    mResult = mResult.substring(0, mResult.length() - 1);
                    mTextviewResult.setText(mResult);
                }
//                mResult = mNothing;
//                mOperator = mNothing ;
                break;

            case R.id.button_ac:
                mResult = mNothing;
                mOperator = mNothing;
                mPara1 = 0.f ;
                mPara2 = 0.f ;
                mTextviewResult.setText(getResources().getString(R.string.zero));
                break;

            case R.id.button_sign:
                if (mResult.equals("")) {
                    mResult = String.valueOf(mPara1);
                }
                if (Float.parseFloat(mResult) > 0) {
                    mResult = getResources().getString(R.string.sub).concat(mResult);
                } else {
                    mResult = mResult.substring(1, mResult.length());
                }
                if ((mResult.indexOf(getResources().getString(R.string.zero))
                        == mResult.indexOf(getResources().getString(R.string.dot)) + 1)
                        && mResult.indexOf("0") == mResult.length() - 1) {
                    mResult = mResult.substring(0, mResult.indexOf("."));
                }
                mTextviewResult.setText(mResult);

                break;

            default:
                if (mCheckClick) {
                    mCheckClick = false;
                    mPara1 = 0.f;
                    mOperator = mNothing ;
                }
                mResult += ((Button) v).getText().toString();
                if (!mOperator.equals("")) {
                    mPara2 = Float.valueOf(mResult);
                }
                mTextviewResult.setText(mResult);
        }
    }
}
