package allaudin.github.io.ease;

import com.google.gson.reflect.TypeToken;

/**
 * Custom type token for storing <b>String</b> type information
 * with token as well.
 * <p>
 * This is to avoid getting type by reflection in {@link EaseBaseRequest}.
 *
 * @author M. Allaudin
 * @see TypeToken
 * @since 1.0
 */


class EaseStringType<T> extends TypeToken<EaseResponse<T>> {

    /**
     * Actual type parameter for this token.
     */
    private final Class<T> actualType;

    protected EaseStringType(Class<T> token) {
        actualType = token;
    }

    /**
     * Utility method for creating EaseStringType <b>String</b> objects.
     *
     * @param token class for which token is created
     * @param <T>   type of object
     * @return an implementation of {@link EaseStringType}
     */
    public static <T> EaseStringType<T> getType(Class<T> token) {
        return new EaseStringType<T>(token) {
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
