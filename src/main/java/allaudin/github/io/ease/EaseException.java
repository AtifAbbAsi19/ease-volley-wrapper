package allaudin.github.io.ease;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * An exception which returns custom message if <em>message</em>
 * is null for this exception.
 *
 * @author M.Allaudin
 * @since 1.0
 */

public class EaseException extends Exception {

    EaseException() {
    }

    EaseException(Throwable cause) {
        super(cause);
    }

    @NonNull
    @Override
    public String getMessage() {
        return super.getMessage() == null ? "No exception message is available." : super.getMessage();
    }

    @Nullable
    @Override
    public synchronized Throwable getCause() {
        return super.getCause();
    }
}
