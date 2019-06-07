package io.github.guitaro.erb.templating.exception;

/**
 * The type File write plugin exception.
 *
 * @author NAGY Levente - 04/06/2019.
 */
public class WriteFilePluginException extends FilePluginException {
    /**
     * Instantiates a new File write plugin exception.
     */
    public WriteFilePluginException() {
    }

    /**
     * Instantiates a new File write plugin exception.
     *
     * @param message the message
     */
    public WriteFilePluginException(final String message) {
        super(message);
    }

    /**
     * Instantiates a new File write plugin exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public WriteFilePluginException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new File write plugin exception.
     *
     * @param cause the cause
     */
    public WriteFilePluginException(final Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new File not found plugin exception.
     *
     * @param message            the message
     * @param cause              the cause
     * @param enableSuppression  the enable suppression
     * @param writableStackTrace the writable stack trace
     */
    public WriteFilePluginException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
