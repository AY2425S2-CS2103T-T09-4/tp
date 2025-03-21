package seedu.address.model.cca;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a person's attendance in a specific CCA.
 * Guarantees: The attended sessions cannot exceed the total session count of the CCA.
 */
public class Attendance {

    public static final String MESSAGE_CONSTRAINTS =
            "Attended sessions cannot be higher than the total sessions of the cca!";

    private final SessionCount sessionsAttended;
    private final SessionCount totalSessions;

    /**
     * Constructs an Attendance object.
     *
     * @param sessionsAttended Number of attended sessions (must be non-negative).
     * @param totalSessions The total number of sessions in the CCA.
     */
    public Attendance(SessionCount sessionsAttended, SessionCount totalSessions) {
        requireNonNull(sessionsAttended);
        requireNonNull(totalSessions);
        checkArgument(isValidAttendance(sessionsAttended, totalSessions), MESSAGE_CONSTRAINTS);
        this.sessionsAttended = sessionsAttended;
        this.totalSessions = totalSessions;
    }

    /**
     * Returns true if the given attendance is valid where the sessions attended are less than the
     * total number of Cca sessions.
     */
    public boolean isValidAttendance(SessionCount sessionsAttended, SessionCount totalSessions) {
        return sessionsAttended.getSessionCount() <= totalSessions.getSessionCount();
    }

    /**
     * Returns the SessionCount class of sessions attended.
     */
    public SessionCount getSessionsAttended() {
        return sessionsAttended;
    }

    /**
     * Returns the SessionCount class of total sessions.
     */
    public SessionCount getTotalSessions() {
        return totalSessions;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Attendance)) {
            return false;
        }
        Attendance otherAttendance = (Attendance) other;
        return sessionsAttended.equals(otherAttendance.sessionsAttended)
                && totalSessions.equals(otherAttendance.totalSessions);
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(sessionsAttended.getSessionCount()) + totalSessions.hashCode();
    }

    @Override
    public String toString() {
        return "Attendance: " + sessionsAttended.getSessionCount() + "/" + totalSessions.getSessionCount();
    }
}
