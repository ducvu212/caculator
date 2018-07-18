package com.example.ducvu212.caculator;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
    private static final String PREF_KEY_RESULT = "result";
    private final String TAG = CaculatorFragment.class.getSimpleName();
    private int[] mIds = {R.id.button_zero, R.id.button_one, R.id.button_two, R.id.button_three, R.id.button_four, R.id.button_five
            , R.id.button_six, R.id.button_seven, R.id.button_eight, R.id.button_nine, R.id.button_ac, R.id.button_sign
            , R.id.button_delete, R.id.button_div, R.id.button_multi, R.id.button_sub, R.id.button_add, R.id.button_result, R.id.button_dot};
    private TextView mTextviewResult;
    private String mResult = "", mOperator = "", mNothing;
    private Float mFirstNumber, mSecondNumber;
    private boolean mIsClick;

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
        SharedPreferences mSharePre = getContext().getSharedPreferences(PREF_RESULT
                , Context.MODE_PRIVATE);
        mTextviewResult.setText(mSharePre.getString(PREF_KEY_RESULT, mNothing));
        mFirstNumber = Float.parseFloat(mSharePre.getString(PREF_KEY_RESULT, mNothing));
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
        int id = item.getItemId();
        switch (id) {
            case R.id.item_clear:
                mResult = mNothing;
                mOperator = mNothing;
                mFirstNumber = 0.f;
                mSecondNumber = 0.f;
                mTextviewResult.setText(getResources().getString(R.string.zero));
                break;

            case R.id.item_save_last_result:
                SharedPreferences mSharePre = getContext().getSharedPreferences(PREF_RESULT
                        , Context.MODE_PRIVATE);
                SharedPreferences.Editor mEditor = mSharePre.edit();
                mEditor.clear();
                if (mResult.equals(mNothing)) {
                    mResult = String.valueOf(mFirstNumber);
                }
                mResult = checkFormatNumber(mResult);
                mEditor.putString(PREF_KEY_RESULT, mResult);
                mEditor.apply();
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    private String checkFormatNumber(String mResult) {
        if ((mResult.indexOf(getResources().getString(R.string.zero))
                == mResult.indexOf(getResources().getString(R.string.dot)) + 1)
                && mResult.indexOf(getResources().getString(R.string.zero)) == mResult.length() - 1) {
            mResult = mResult.substring(0, mResult.indexOf(getResources().getString(R.string.dot)));
        }

        return mResult;
    }

    private void clickResultButton() {
        if (mSecondNumber != null) {
            if (mOperator.equals(getResources().getString(R.string.add))) {
                mResult = String.valueOf(mFirstNumber + mSecondNumber);
            } else if (mOperator.equals(getResources().getString(R.string.sub))) {
                mResult = String.valueOf(mFirstNumber - mSecondNumber);
            } else if (mOperator.equals(getResources().getString(R.string.multi))) {
                mResult = String.valueOf(mFirstNumber * mSecondNumber);
            } else if (mOperator.equals(getResources().getString(R.string.div))) {
                mResult = String.valueOf(mFirstNumber / mSecondNumber);
            }
            if (!mResult.equals(mNothing)) {
                if ((mResult.indexOf(getResources().getString(R.string.zero)
                        , mResult.indexOf(getResources().getString(R.string.dot)))
                        == mResult.indexOf(getResources().getString(R.string.dot)) + 1)
                        && mResult.indexOf(getResources().getString(R.string.zero)
                        , mResult.indexOf(getResources().getString(R.string.dot))) == mResult.length() - 1) {
                    mTextviewResult.setText(mResult.substring(0, mResult.indexOf(getResources().getString(R.string.dot))));
                } else {
                    mTextviewResult.setText(mResult);
                }
                mFirstNumber = Float.valueOf(mResult);
            }
            mResult = mNothing;
        }
    }

    private void operator() {
        if (mIsClick) {
            mOperator = mNothing;
            mIsClick = false;
        }
        if (!mResult.equals(mNothing) && mOperator.equals(mNothing))
            mFirstNumber = Float.valueOf(mResult);
        clickResultButton();
        mResult = mNothing;
    }

    private void actionDel() {
        if (mResult.length() <= 1 && mFirstNumber <= 0) {
            mResult = mNothing;
            mOperator = mNothing;
            mFirstNumber = 0.f;
            mSecondNumber = 0.f;
            mTextviewResult.setText(getResources().getString(R.string.zero));
        } else {
            if (mResult.length() >= 1) {
                mResult = mResult.substring(0, mResult.length() - 1);
                mTextviewResult.setText(mResult);
            }
        }
    }

    private void actionAc() {
        mResult = mNothing;
        mOperator = mNothing;
        mFirstNumber = 0.f;
        mSecondNumber = 0.f;
        mTextviewResult.setText(getResources().getString(R.string.zero));
    }

    private void actionSign() {
        if (mResult.equals(mNothing)) {
            mResult = String.valueOf(mFirstNumber);
        }
        if (Float.parseFloat(mResult) > 0) {
            mResult = getResources().getString(R.string.sub).concat(mResult);
        } else {
            mResult = mResult.substring(1, mResult.length());
        }
        checkFormatNumber(mResult);
        mTextviewResult.setText(mResult);

    }

    private void actionDefault(View v) {
        if (mIsClick) {
            mIsClick = false;
            mFirstNumber = 0.f;
            mOperator = mNothing;
        }
        mResult += ((Button) v).getText().toString();
        if (!mOperator.equals(mNothing)) {
            mSecondNumber = Float.valueOf(mResult);
        }
        mTextviewResult.setText(mResult);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_add:
                operator();
                mOperator = getResources().getString(R.string.add);
                break;

            case R.id.button_sub:
                operator();
                mOperator = getResources().getString(R.string.sub);
                break;

            case R.id.button_multi:
                operator();
                mOperator = getResources().getString(R.string.multi);
                break;

            case R.id.button_div:
                operator();
                mOperator = getResources().getString(R.string.div);
                break;
            case R.id.button_result:
                if (!mResult.equals(mNothing)) mSecondNumber = Float.valueOf(mResult);
                clickResultButton();
                mIsClick = true;
                break;

            case R.id.button_delete:
                actionDel();
                break;

            case R.id.button_ac:
                actionAc();
                break;

            case R.id.button_sign:
                actionSign();
                break;

            default:
                actionDefault(v);
        }
    }
}
