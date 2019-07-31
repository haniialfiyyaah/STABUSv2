package com.stabus.app.TKalkulator;

import android.app.Dialog;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.view.Window;
import android.widget.Spinner;

import com.stabus.app.Database.DBMHarga;
import com.stabus.app.Interface.ISetListener;
import com.stabus.app.Model.CollectBahanBaku;
import com.stabus.app.Model.CollectBahanCRUD;
import com.stabus.app.Model.CollectKemasan;
import com.stabus.app.Model.CollectKemasanCRUD;
import com.stabus.app.Model.CollectString;
import com.stabus.app.Model.CollectStringCRUD;
import com.stabus.app.Model.MKemasan;
import com.stabus.app.R;

public class KalkulatorDialogKemasan implements View.OnClickListener {

    private Dialog d ;
    private View view;
    private ISetListener mISetListener;

    //widget
    private TextInputLayout mTxNama;
    private TextInputLayout mTxJumlah;
    private Spinner mSpSatuan;
    private TextInputLayout mTxHarga;
    private MaterialButton mfabOk;

    //value
    private String mNama;
    private String mSatuan;
    private int mJumlah;
    private float mHarga;

    CollectStringCRUD stringCRUD;
    CollectKemasanCRUD kemasanCRUD;

    //value
    public KalkulatorDialogKemasan(View view, ISetListener mISetListener) {
        this.view = view;
        this.mISetListener = mISetListener;
    }

    public void showDialog(){
        d = new Dialog(view.getContext());
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.setContentView(R.layout.dialog_pilih_kemasan);
        init();
        d.show();
    }

    private void init() {
        initView();
        initListener();
        initObject();
    }
    private void initView(){
        mTxNama = view.findViewById(R.id.textNama);
        mTxJumlah = view.findViewById(R.id.textJumlahdg);
        mSpSatuan = view.findViewById(R.id.spSatuan);
        mTxHarga = view.findViewById(R.id.textHarga);
        mfabOk = view.findViewById(R.id.fabOk);
    }
    private void initListener(){
        mfabOk.setOnClickListener(this);
    }

    private void initObject(){
        stringCRUD = new CollectStringCRUD(CollectString.getString());
        kemasanCRUD = new CollectKemasanCRUD(CollectKemasan.getKemasanList());
    }

    private void getValue(){
        if (mTxNama.getEditText()!=null&&mTxJumlah.getEditText()!=null&&mTxHarga.getEditText()!=null) {
            mNama = mTxNama.getEditText().getText().toString();
            mJumlah = Integer.valueOf(mTxJumlah.getEditText().getText().toString());
            mSatuan = mSpSatuan.getSelectedItem().toString();
            mHarga = Float.valueOf(mTxHarga.getEditText().getText().toString());
        }
    }
    private void validasi(){
        if (mTxNama.getEditText().getText().toString().trim().length()<=0){
            setErrorMessage(mTxNama,"Tidak Boleh Kosong");
            return;
        }
    }
    private void setErrorMessage(TextInputLayout inputLayout, String message){
        clearError();
        inputLayout.setError(message);
        inputLayout.requestFocus();
    }
    private void clearError(){
        mTxNama.setError(null);
        mTxJumlah.setError(null);
        mTxHarga.setError(null);
    }

    private void setKemasanCRUD(){
        getValue();
        MKemasan kemasan = new MKemasan();
        kemasan.setNama(mNama);
        kemasan.setJumlah(mJumlah);
        kemasan.setSatuan(mSatuan);
        kemasan.setHarga(mHarga);
        kemasanCRUD.addNew(kemasan);
    }


    @Override
    public void onClick(View v) {
        if (v==mfabOk){
            setKemasanCRUD();
        }
    }
}
