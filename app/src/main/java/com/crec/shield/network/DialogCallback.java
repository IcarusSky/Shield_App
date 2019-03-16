package com.crec.shield.network;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.annotation.Nullable;
import android.view.Window;

import com.crec.shield.R;
import com.lzy.okgo.request.BaseRequest;

/**
 * Created by diaokaibin@gmail.com on 2017/6/21.
 */

public abstract class DialogCallback<T> extends JsonCallback<T> {

    private ProgressDialog dialog;


    private void initDialog(Activity activity) {
        dialog = new ProgressDialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage(activity.getResources().getString(R.string.loading_message));
    }

    public DialogCallback(Activity activity) {
        super();
        initDialog(activity);
    }

    @Override
    public void onBefore(BaseRequest request) {
        super.onBefore(request);
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    @Override
    public void onAfter(@Nullable T t, @Nullable Exception e) {
        super.onAfter(t, e);
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
