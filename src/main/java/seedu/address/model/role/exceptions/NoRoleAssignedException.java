package seedu.address.model.role.exceptions;

/**
 * Signals that the operation will result in attempting to remove a role when the person
 * currently has only the default role assigned, which cannot be removed.
 */
public class NoRoleAssignedException extends RuntimeException {
    public NoRoleAssignedException() {
        super("Operation would result in no role assigned to the person");
    }
}
