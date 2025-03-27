package seedu.address.model.role.exceptions;

/**
 * Signals that the operation attempted to explicitly assign the default role ("Member"),
 * which is not allowed.
 */
public class DefaultRoleNotAllowedException extends RuntimeException {
    public DefaultRoleNotAllowedException() {
        super("Explicitly assigning the default role ('Member') is not allowed.");
    }
}
