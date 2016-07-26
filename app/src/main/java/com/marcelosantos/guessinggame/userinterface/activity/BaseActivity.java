package com.marcelosantos.guessinggame.userinterface.activity;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.marcelosantos.guessinggame.R;
import com.marcelosantos.guessinggame.infrastructure.common.AlertUtil;
import com.marcelosantos.guessinggame.infrastructure.common.LogUtil;

import org.androidannotations.annotations.EActivity;

/**
 * Created by Marcelo on 17/10/2015.
 */
@EActivity
public class BaseActivity extends AppCompatActivity {

    protected void catchException(Exception e) {

        LogUtil.catchException(this, e);
    }

    protected void configureActionBar() {

        Toolbar toolbar = (Toolbar)super.findViewById(R.id.actionbar);
        super.setSupportActionBar(toolbar);
    }
}
