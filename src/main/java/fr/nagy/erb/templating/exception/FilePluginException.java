package fr.nagy.erb.templating.exception;

/**
 * The type File plugin exception.
 *
 * @author NAGY Levente - 04/06/2019.
 */
public class FilePluginException extends PluginException {
    /**
     * Instantiates a new File plugin exception.
     */
    public FilePluginException() {
    }

    /**
     * Instantiates a new File plugin exception.
     *
     * @param message the message
     */
    public FilePluginException(final String message) {
        super(message);
    }

    /**
     * Instantiates a new File plugin exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public FilePluginException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new File plugin exception.
     *
     * @param cause the cause
     */
    public FilePluginException(final Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new File plugin exception.
     *
     * @param message            the message
     * @param cause              the cause
     * @param enableSuppression  the enable suppression
     * @param writableStackTrace the writable stack trace
     */
    public FilePluginException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
