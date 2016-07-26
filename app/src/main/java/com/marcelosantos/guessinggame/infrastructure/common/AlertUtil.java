package com.marcelosantos.guessinggame.infrastructure.common;

import android.content.Context;
import android.support.v7.app.AlertDialog;

import com.marcelosantos.guessinggame.R;

/**
 * Created by Marcelo on 17/10/2015.
 */
public class AlertUtil {

	public static void showOkAlert(Context context, String title, String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setPositiveButton(context.getString(R.string.ok), null);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.create().show();
    }
}
