package seedu.address.model.cca;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.model.role.Role;
import seedu.address.model.role.exceptions.DefaultRoleNotAllowedException;
import seedu.address.model.role.exceptions.NoRoleAssignedException;
import seedu.address.model.role.exceptions.RoleAlreadyAssignedException;
import seedu.address.model.role.exceptions.RoleNotFoundException;

/**
 * Represents a person's involvement in a Co-Curricular Activity (CCA).
 * Encapsulates the CCA, the person's role within the CCA, and their attendance record.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class CcaInformation {

    private final Cca cca;
    private final Role role;
    private final Attendance attendance;

    /**
     * Constructs a {@code CcaInformation} object.
     *
     * @param cca The CCA that the person is involved in.
     * @param role The role the person holds in the specified CCA.
     * @param attendance The attendance record of the person for the CCA.
     */
    public CcaInformation(Cca cca, Role role, Attendance attendance) {
        requireAllNonNull(cca, role, attendance);
        this.cca = cca;
        this.role = role;
        this.attendance = attendance;
    }

    /**
     * Returns the CCA associated with this {@code CcaInformation}.
     *
     * @return The CCA object.
     */
    public Cca getCca() {
        return cca;
    }

    /**
     * Returns the role of the person in the CCA.
     *
     * @return The {@code Role} object.
     */
    public Role getRole() {
        return role;
    }

    /**
     * Returns the attendance record of the person in the CCA.
     *
     * @return The {@code Attendance} object.
     */
    public Attendance getAttendance() {
        return attendance;
    }

    /**
     * Returns the name of the CCA associated with this {@code CcaInformation}.
     *
     * @return The name of the CCA.
     */
    public CcaName getCcaName() {
        return cca.getCcaName();
    }

    /**
     * Returns if the role of this {@code CcaInformation} is the default role.
     */
    public boolean isDefaultRole() {
        return role.isDefaultRole();
    }

    /**
     * Returns a new {@code CcaInformation} object with the role added.
     * The current {@code role} of CCA Information must be a default role.
     * {@code roleToAdd} must not be the default role and must be defined in the CCA.
     *
     * @param roleToAdd The role to assign.
     * @return A new {@code CcaInformation} object with the role added.
     * @throws RoleAlreadyAssignedException If the role is already assigned.
     * @throws DefaultRoleNotAllowedException If the role to add is the default role.
     * @throws RoleNotFoundException If the role to add is not found in the CCA.
     */
    public CcaInformation addRole(Role roleToAdd) {
        if (!role.isDefaultRole()) {
            throw new RoleAlreadyAssignedException();
        }
        if (roleToAdd.isDefaultRole()) {
            throw new DefaultRoleNotAllowedException();
        }
        if (!cca.hasRole(roleToAdd)) {
            throw new RoleNotFoundException();
        }
        return new CcaInformation(cca, roleToAdd, attendance);
    }

    /**
     * Returns a new {@code CcaInformation} object with the role deleted.
     * The current {@code role} of CCA Information must not be the default role.
     *
     * @return A new {@code CcaInformation} object with the role deleted.
     * @throws NoRoleAssignedException If the role is not assigned.
     */
    public CcaInformation deleteRole() {
        if (role.isDefaultRole()) {
            throw new NoRoleAssignedException();
        }
        return new CcaInformation(cca, Role.DEFAULT_ROLE, attendance);
    }

    /**
     * Checks if this {@code CcaInformation} is equal to another object.
     * Two instances are considered equal if they have the same CCA, role, and attendance record.
     *
     * @param other The object to compare against.
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CcaInformation)) {
            return false;
        }
        CcaInformation otherCcaInformation = (CcaInformation) other;
        return cca.equals(otherCcaInformation.cca)
            && role.equals(otherCcaInformation.role)
            && attendance.equals(otherCcaInformation.attendance);
    }

    @Override
    public int hashCode() {
        return cca.hashCode() + role.hashCode() + attendance.hashCode();
    }

    @Override
    public String toString() {
        return cca + " | Role: " + role + " | " + attendance;
    }
}
