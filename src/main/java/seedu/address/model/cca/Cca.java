package seedu.address.model.cca;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.role.Role;

/**
 * Represents a CCA in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Cca {

    private final CcaName ccaName;
    private final Set<Role> roles;
    private final SessionCount totalSessions;

    /**
     * Constructs a {@code Cca} with only a name.
     * Initializes with an empty set of roles and zero total sessions.
     *
     * @param ccaName A valid CCA name.
     */
    public Cca(CcaName ccaName) {
        requireNonNull(ccaName);
        this.ccaName = ccaName;
        this.roles = new HashSet<>();
        this.totalSessions = new SessionCount(0);
    }

    /**
     * Constructs a {@code Cca}.
     *
     * @param ccaName A valid CCA name.
     * @param roles A set of roles associated with the CCA.
     * @param totalSessions The total number of sessions conducted for this CCA.
     */
    public Cca(CcaName ccaName, Set<Role> roles, SessionCount totalSessions) {
        requireNonNull(ccaName);
        requireNonNull(roles);
        this.ccaName = ccaName;
        this.roles = new HashSet<>(roles);
        this.totalSessions = totalSessions;
    }

    /**
     * Returns the name of the CCA.
     */
    public CcaName getCcaName() {
        return ccaName;
    }

    /**
     * Returns a copy of the set of roles associated with this CCA.
     * The returned set is a defensive copy to maintain immutability.
     *
     * @return A set of {@code Role} objects associated with this CCA.
     */
    public Set<Role> getRoles() {
        return new HashSet<>(roles);
    }

    /**
     * Returns the total number of sessions conducted for this CCA.
     *
     * @return The total number of sessions as an integer.
     */
    public SessionCount getTotalSessions() {
        return totalSessions;
    }

    /**
     * Returns true if the CCA has the specified role.
     *
     * @param role The role to check for.
     * @return True if the CCA has the specified role, false otherwise.
     */
    public boolean hasRole(Role role) {
        return roles.contains(role);
    }

    /**
     * Returns a new {@code Attendance} object with the total sessions set to the total sessions of this CCA.
     * The attended sessions are set to zero.
     * @return A new {@code Attendance} object.
     */
    public Attendance createNewAttendance() {
        return new Attendance(new SessionCount(0), totalSessions);
    }

    /**
     * Returns true if both ccas have the same cca name.
     * This defines a weaker notion of equality between two ccas.
     */
    public boolean isSameCca(Cca otherCca) {
        if (otherCca == this) {
            return true;
        }

        return otherCca != null
                && otherCca.getCcaName().equals(getCcaName());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Cca)) {
            return false;
        }

        Cca otherCca = (Cca) other;
        return ccaName.equals(otherCca.ccaName)
                && roles.equals(otherCca.roles)
                && totalSessions.equals(otherCca.totalSessions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ccaName, roles, totalSessions);
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return "[" + ccaName + "]";
    }

}
