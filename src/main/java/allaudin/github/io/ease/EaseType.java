package allaudin.github.io.ease;

import com.google.gson.reflect.TypeToken;

/**
 * Custom type token for storing type information
 * with token as well.
 * <p>
 * This is to avoid getting type by reflection in {@link EaseBaseRequest}.
 *
 * @author M. Allaudin
 * @see TypeToken
 * @since 1.0
 */


class EaseType<T> extends TypeToken<EaseResponse<T>> {

    /**
     * Actual type parameter for this token.
     */
    private final Class<T> actualType;

    protected EaseType(Class<T> token) {
        actualType = token;
    }

    /**
     * Utility method for creating EaseType objects.
     *
     * @param token class for which token is created
     * @param <T>   type of object
     * @return an implementation of {@link EaseType}
     */
    public static <T> EaseType<T> getType(Class<T> token) {
        return new EaseType<T>(token) {
        };
    }

    /**
     * Return actual type parameter.
     *
     * @return actual type parameter
     */
    public Class<?> getActualType() {
        return actualType;
    }
}
