package io.levyndot.erb.templating.exception;

/**
 * The type Plugin exception.
 *
 * @author NAGY Levente - 04/06/2019.
 */
public class PluginException extends Exception {
    /**
     * Instantiates a new Plugin exception.
     */
    public PluginException() {
    }

    /**
     * Instantiates a new Plugin exception.
     *
     * @param message the message
     */
    public PluginException(final String message) {
        super(message);
    }

    /**
     * Instantiates a new Plugin exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public PluginException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Plugin exception.
     *
     * @param cause the cause
     */
    public PluginException(final Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new Plugin exception.
     *
     * @param message            the message
     * @param cause              the cause
     * @param enableSuppression  the enable suppression
     * @param writableStackTrace the writable stack trace
     */
    public PluginException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
