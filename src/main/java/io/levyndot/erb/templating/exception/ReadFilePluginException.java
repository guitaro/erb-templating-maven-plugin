package io.levyndot.erb.templating.exception;

/**
 * The type Read file plugin exception.
 *
 * @author NAGY Levente - 04/06/2019.
 */
public class ReadFilePluginException extends FilePluginException {
    /**
     * Instantiates a new Read file plugin exception.
     */
    public ReadFilePluginException() {
    }

    /**
     * Instantiates a new Read file plugin exception.
     *
     * @param message the message
     */
    public ReadFilePluginException(final String message) {
        super(message);
    }

    /**
     * Instantiates a new Read file plugin exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public ReadFilePluginException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Read file plugin exception.
     *
     * @param cause the cause
     */
    public ReadFilePluginException(final Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new Read file plugin exception.
     *
     * @param message            the message
     * @param cause              the cause
     * @param enableSuppression  the enable suppression
     * @param writableStackTrace the writable stack trace
     */
    public ReadFilePluginException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
