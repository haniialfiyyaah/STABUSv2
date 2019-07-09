package com.stabus.app.Interface;

import android.os.Bundle;
import android.support.annotation.MenuRes;
import android.support.v7.widget.RecyclerView;
import android.view.ActionMode;
import android.view.View;

public interface ISetListener {

    void setToolbarTitle(String toolbarTitle);

    void setToolbarTitleListener(boolean editTitle, View.OnClickListener listener);

    void setNavigationIcon(int resId, boolean backStack);

    void setNavigationListener(int resId, View.OnClickListener listener);

    void inflateFragment(String fragmentTag, Bundle bundle);

    void setRecyclerView(View view, RecyclerView recyclerView, RecyclerView.Adapter mAdapter);

}
