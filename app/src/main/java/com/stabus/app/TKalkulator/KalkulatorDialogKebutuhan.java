package com.stabus.app.TKalkulator;

import android.app.Dialog;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.stabus.app.Class.MyTextWatcher;
import com.stabus.app.Model.CollectKebutuhan;
import com.stabus.app.Model.CollectKebutuhanCRUD;
import com.stabus.app.Model.CollectString;
import com.stabus.app.Model.CollectStringCRUD;
import com.stabus.app.Model.MKebutuhan;
import com.stabus.app.R;
import com.stabus.app.RecyclerView.KebutuhanAdapter;

import java.util.Locale;

public class KalkulatorDialogKebutuhan  implements View.OnClickListener {

    private Dialog d;
    private View view;

    private KebutuhanAdapter mAdapter;

    //widget
    private TextInputLayout mTxNama;
    private TextInputLayout mTxHarga;
    private MaterialButton mfabOk;
    private TextView mTvJmlHarga;

    //value
    private String mNama;
    private float mHarga;

    private CollectStringCRUD stringCRUD;
    private CollectKebutuhanCRUD kebutuhanCRUD;

    KalkulatorDialogKebutuhan(View view, KebutuhanAdapter mAdapter) {
        this.view = view;
        this.mAdapter = mAdapter;
    }

    public void showDialog(){
        d = new Dialog(view.getContext());
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.setContentView(R.layout.dialog_pilih_kebutuhan);
        init();
        d.show();
    }
    private void init() {
        initView();
        initListener();
        initObject();
    }
    private void initView(){
        mTxNama = d.findViewById(R.id.textNama);
        mTxHarga = d.findViewById(R.id.textHarga);
        mfabOk = d.findViewById(R.id.fabOk);
        mTvJmlHarga = view.findViewById(R.id.tvJumlahHarga);
    }
    private void initListener(){
        mfabOk.setOnClickListener(this);
        assert mTxNama.getEditText()!=null;
        mTxNama.getEditText().addTextChangedListener(new MyTextWatcher(mTxNama, "SetNull"));
        assert mTxHarga.getEditText()!=null;
        mTxHarga.getEditText().addTextChangedListener(new MyTextWatcher(mTxHarga,"SetNull"));
    }
    private void initObject(){
        stringCRUD = new CollectStringCRUD(CollectString.getString());
        kebutuhanCRUD = new CollectKebutuhanCRUD(CollectKebutuhan.getKebutuhanList());
    }
    private void getValue(){
        if (mTxNama.getEditText()!=null&&mTxHarga.getEditText()!=null) {
            mNama = mTxNama.getEditText().getText().toString();
            mHarga = Float.valueOf(mTxHarga.getEditText().getText().toString());
        }
    }
    private void setErrorMessage(TextInputLayout inputLayout, String message){
        clearError();
        inputLayout.setError(message);
        inputLayout.requestFocus();
    }
    private void clearError(){
        mTxNama.setError(null);
        mTxHarga.setError(null);
    }
    private void clearText(){
        if (mTxNama.getEditText()!=null&&mTxHarga.getEditText()!=null) {
            mTxNama.getEditText().setText("");
            mTxHarga.getEditText().setText("");
        }
    }
    private void setKemasanCRUD(){
        if (mTxNama.getEditText()!=null&&mTxHarga.getEditText()!=null) {
            if (mTxNama.getEditText().getText().toString().trim().length() <= 0) {
                setErrorMessage(mTxNama, "Tidak Boleh Kosong");
                return;
            }
            if (mTxHarga.getEditText().getText().toString().trim().length() <= 0) {
                setErrorMessage(mTxHarga, "Tidak Boleh Kosong");
                return;
            }
            setErrorMessage(mTxNama, "");
            setErrorMessage(mTxHarga, "");
            getValue();
            MKebutuhan kebutuhan = new MKebutuhan();
            kebutuhan.setNama(mNama);
            kebutuhan.setHarga(mHarga);
            kebutuhanCRUD.addNew(kebutuhan);
            mAdapter.notifyDataSetChanged();
            setHargaSatuan();
            clearError();
            clearText();
            d.dismiss();
        }
    }
    private void setHargaSatuan(){
        /*
        Total Harga = SUM( jumlah_terpakai/1 * Harga )
         */
        float harga_total = stringCRUD.getString().get(0).getHarga_total();
        harga_total = harga_total + mHarga;

        stringCRUD.getString().get(0).setHarga_total(harga_total);
        mTvJmlHarga.setText(String.format(Locale.US,"Rp. %,.0f", harga_total));
    }


    @Override
    public void onClick(View v) {
        setKemasanCRUD();
    }
}
