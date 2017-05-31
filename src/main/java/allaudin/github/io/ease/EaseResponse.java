package allaudin.github.io.ease;

import com.google.gson.annotations.SerializedName;

/**
 * Parsed response for {@link EaseRequest}
 *
 * @param <T> type of this response
 * @author M.Allaudin
 * @see EaseRequest
 * @since 1.0
 */


public final class EaseResponse<T> {

    /**
     * Data parsed from network response.
     *
     * @see EaseBaseRequest
     */
    private T data;

    @SerializedName("result_description")
    private String description;

    private int statusCode;

    /**
     * Get status code for this request
     *
     * @return status code
     */
    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * Check if request is successful or not.
     *
     * @return true if {@code statusCode == 200}, false otherwise.
     */
    public boolean isSuccessful() {
        return statusCode == 200;
    }

    /**
     * Get description for this request
     *
     * @param description for this request
     */
    public void setDescription(String description) {
        this.description = description;
    }

    public void setData(T data) {
        this.data = data;
    }


    public String getDescription() {
        return description;
    }

    public T getData() {
        return data;
    }

} // Korek
