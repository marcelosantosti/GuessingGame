package com.marcelosantos.guessinggame.infrastructure.common;

import android.content.Context;

import com.marcelosantos.guessinggame.R;

/**
 * Created by Marcelo on 20/10/2015.
 */
public class LogUtil {

    public static void catchException(Context context, Exception e) {

        AlertUtil.showOkAlert(context, context.getString(R.string.ops), context.getString(R.string.general_error));

        //// TODO: 20/10/2015
        //log exception with some server integration
    }
}
