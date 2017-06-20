package allaudin.github.io.ease;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Default implementation of {@link EaseDialog} which shows
 * default progress dialog.
 *
 * @author M.Allaudin
 */

public class DefaultEaseDialog implements EaseDialog {

    private CharSequence mtitle;
    private ProgressDialog mProgressDialog;

    private DefaultEaseDialog(@NonNull CharSequence title) {
        this.mtitle = title;
    }

    @Override
    public EaseDialog init(Context context) {
        if (mProgressDialog == null) {
            this.mProgressDialog = new ProgressDialog(context);
            this.mProgressDialog.setTitle(mtitle);
        }
        return this;
    }

    public static DefaultEaseDialog newDialog(@NonNull CharSequence title) {
        return new DefaultEaseDialog(title);
    }


    @Override
    public void show() {
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    @Override
    public void hide() {
        if (mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }


} // DefaultEaseDialog
