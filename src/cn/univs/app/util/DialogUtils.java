package cn.univs.app.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import cn.univs.app.R;

public class DialogUtils {
    private static DialogUtils mInstance;
    private Dialog mDialog;

    private DialogUtils() {
    }

    public static DialogUtils getInstance() {
        if (mInstance == null) {
            mInstance = new DialogUtils();
        }
        return mInstance;
    }

    public void displayDialog(Context context, View view, int gravity) {
        displayDialog(context, view, gravity, Color.TRANSPARENT);
    }

    public void displayDialog(Context context, View view, int gravity,
            int backGround) {
        if (context instanceof Activity && ((Activity) context).isFinishing()) {
            return;
        }
        dismissDialog();
        mDialog = new Dialog(context, R.style.ShareMenuDialog);
        mDialog.setContentView(view);
        mDialog.setCanceledOnTouchOutside(true);
        Window window = mDialog.getWindow();     
        window.setGravity(gravity);
        window.setBackgroundDrawable(new ColorDrawable(backGround));
        window.setWindowAnimations(R.style.ShareAnimation);
        WindowManager.LayoutParams mParams = mDialog.getWindow()
                .getAttributes();
        WindowManager windowManager =(WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        mParams.width = (int) (display.getWidth() * 1.0);
        mDialog.getWindow().setAttributes(mParams);
        mDialog.show();
    }

    public void dismissDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        mDialog = null;
    }
}
