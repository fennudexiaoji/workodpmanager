package com.mvp.lib.view;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.mvp.lib.base.BaseFragment;


public interface IBaseFragmentView extends IUIView {

    Bundle getBundle();

    FragmentManager getFragmentManager();
    BaseFragment getFragment();
}
