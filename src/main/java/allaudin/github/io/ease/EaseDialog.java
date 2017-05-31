package allaudin.github.io.ease;

import android.content.Context;

/**
 * Interface for managing progress dialog before network call.
 *
 * @author M.Allaudin
 * @see EaseRequest
 */

public interface EaseDialog {

    /**
     * Called by the wrapper for initializing dialog before each request if applicable.
     *
     * @param context context for initializing dialog
     * @return EaseDialog
     */
    EaseDialog init(Context context);

    /**
     * Show dialog.
     */
    void show();

    /**
     * Hide dialog.
     */
    void hide();
}
