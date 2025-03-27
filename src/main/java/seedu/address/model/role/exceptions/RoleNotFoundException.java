package seedu.address.model.role.exceptions;

/**
 * Signals that the operation is unable to find the specified role.
 */
public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException() {
        super("The role is not found.");
    }
}
