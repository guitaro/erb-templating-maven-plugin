package fr.nagy.erb.templating.exception;

/**
 * The type Ruby plugin exception.
 *
 * @author NAGY Levente - 04/06/2019.
 */
public class RubyPluginException extends PluginException {
    /**
     * Instantiates a new Ruby plugin exception.
     */
    public RubyPluginException() {
    }

    /**
     * Instantiates a new Ruby plugin exception.
     *
     * @param message the message
     */
    public RubyPluginException(final String message) {
        super(message);
    }

    /**
     * Instantiates a new Ruby plugin exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public RubyPluginException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Ruby plugin exception.
     *
     * @param cause the cause
     */
    public RubyPluginException(final Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new Ruby plugin exception.
     *
     * @param message            the message
     * @param cause              the cause
     * @param enableSuppression  the enable suppression
     * @param writableStackTrace the writable stack trace
     */
    public RubyPluginException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
