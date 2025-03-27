package seedu.address.model.cca;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Optional;

import seedu.address.model.role.Role;

/**
 * Represents a person's involvement in a Co-Curricular Activity (CCA).
 * Encapsulates the CCA, the person's role within the CCA, and their attendance record.
 */
public class CcaInformation {

    private final Cca cca;
    private final Optional<Role> role;
    private final Attendance attendance;

    /**
     * Constructs a {@code CcaInformation} object.
     *
     * @param cca The CCA that the person is involved in.
     * @param role The role the person holds in the specified CCA.
     * @param attendance The attendance record of the person for the CCA.
     */
    public CcaInformation(Cca cca, Optional<Role> role, Attendance attendance) {
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
    public Optional<Role> getRole() {
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
        String roleStr = role.map(Role::toString).orElse("[No Role]");
        return cca + " | Role: " + roleStr + " | " + attendance;
    }
}
