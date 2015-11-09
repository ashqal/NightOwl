package com.asha.nightowl;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by hzqiujiadi on 15/11/9.
 * hzqiujiadi ashqalcn@gmail.com
 */
public class DialogHelper  {

    public static void show( Activity activity ){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("exit")
                .setMessage("are you sure?")
                .setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }
}
