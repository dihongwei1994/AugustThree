package com.lcb.augustthree;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.RadioButton;

import androidx.annotation.NonNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * created by: Eroch
 * time: 2020/8/16
 * introduce:
 */
class PermissionDialog extends Dialog {

    @BindView(R.id.agree)
    RadioButton agree;

    public PermissionDialog(@NonNull Context context) {
        super(context, R.style.MyDialogStyle);
        setContentView(R.layout.dialog_permission);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.cancel, R.id.agree})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cancel:
                dismiss();
                break;
        }
    }

    public RadioButton getAgree() {
        return agree;
    }
}
