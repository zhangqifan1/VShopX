package com.zqf.vshop.mvp.MyselfMvp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;

import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RequestExecutor;
import com.zqf.vshop.R;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public final class DefaultRationale implements Rationale {

    @Override
    public void showRationale(Context context, List<String> permissions, final RequestExecutor executor) {
        List<String> permissionNames = Permission.transformText(context, permissions);
        @SuppressLint({"StringFormatInvalid", "LocalSuppress"}) String message = context.getString(R.string.message_permission_rationale, TextUtils.join("\n", permissionNames));
        new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText(message)
                .setCancelText("No,cancel plx!")
                .setConfirmText("Yes,delete it!")
                .showCancelButton(true)
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        executor.cancel();
                        sDialog.dismiss();
                    }
                })
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        executor.execute();
                    }
                })
                .show();
    }
}