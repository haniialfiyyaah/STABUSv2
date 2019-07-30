package com.stabus.app.Interface;

import android.view.View;

public interface OnListener {
    void OnClickListener(int position,String nama, View view);

    boolean OnLongListener(int position, View view);

}
