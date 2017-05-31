package allaudin.github.io.ease;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Ease configuration interface for initializing
 * ease wrapper.
 *
 * @author M.Allaudin
 * @since 1.0
 */

public interface EaseConfig {

    /**
     * Base URL for requests.
     *
     * @return baseUrl base url for all requests
     */
    @NonNull
    String getBaseUrl();

    /**
     * Dialog for showing before each network call when applicable.
     *
     * @return EaseDialog progress dialog for network calls
     */
    @NonNull
    EaseDialog getDialog();

    /**
     * Default headers to be added in each network call.
     *
     * @return RequestHeaders headers for adding in every network call.
     */
    @Nullable
    RequestHeaders defaultHeaders();

    /**
     * Enable response caching.
     * <p>
     * <p>
     * <b>Note:</b> This flag has <em>less priority</em> than flag set through {@link EaseRequest.RequestBuilder}
     *
     * @return boolean - true for caching response, false otherwise
     */
    boolean shouldCacheResponse();

    // TODO: 5/30/17 add support for ssl [allaudin]
    
    // TODO: 5/31/2017 add sanitize hook

    /**
     * Enable request logs.
     *
     * @return true for enable logging, false for disabling.
     */
    boolean enableLogging();


    /**
     * Socket timeout for all requests
     * <p>If 0 is returned form this method, default socket timout of {@code 10 sec} will be used.</p>
     * <p>
     * <b>Note:</b> This timeout has <em>less priority</em> than timeout set through {@link EaseRequest.RequestBuilder}
     *
     * @return int timeout in millis
     * @see com.android.volley.RetryPolicy
     */
    int socketTimeOut();

    /**
     * Number for tries in case of failure
     *
     * @return int number of tries
     * @see com.android.volley.RetryPolicy
     */
    int numOfRetries();

}
