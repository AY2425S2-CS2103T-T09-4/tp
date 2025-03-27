package seedu.address.model.role.exceptions;

/**
 * Signals that the operation will result in assigning a role to a person who already has
 * a non-default role assigned in the specified CCA.
 */
public class RoleAlreadyAssignedException extends RuntimeException {
    public RoleAlreadyAssignedException() {
        super("Operation would result in assigning a role to a person who already has a role assigned");
    }
}
