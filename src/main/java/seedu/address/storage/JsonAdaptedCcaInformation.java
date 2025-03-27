package seedu.address.storage;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.cca.Attendance;
import seedu.address.model.cca.Cca;
import seedu.address.model.cca.CcaInformation;
import seedu.address.model.cca.SessionCount;
import seedu.address.model.role.Role;

/**
 * Jackson-friendly version of {@link CcaInformation}.
 */
class JsonAdaptedCcaInformation {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "CcaInformation's %s field is missing!";

    private final JsonAdaptedCca cca;
    private final JsonAdaptedRole role;
    private final int attendedSessions;
    private final int totalSessions;

    /**
     * Constructs a {@code JsonAdaptedCcaInformation} with the given details.
     */
    @JsonCreator
    public JsonAdaptedCcaInformation(@JsonProperty("cca") JsonAdaptedCca cca,
                                     @JsonProperty("role") JsonAdaptedRole role,
                                     @JsonProperty("attendedSessions") int attendedSessions,
                                     @JsonProperty("totalSessions") int totalSessions) {
        this.cca = cca;
        this.role = role;
        this.attendedSessions = attendedSessions;
        this.totalSessions = totalSessions;
    }

    /**
     * Converts a given {@code CcaInformation} into this class for Jackson use.
     */
    public JsonAdaptedCcaInformation(CcaInformation source) {
        this.cca = new JsonAdaptedCca(source.getCca());
        this.role = source.getRole().map(JsonAdaptedRole::new).orElse(null);
        this.attendedSessions = source.getAttendance().getSessionsAttended().getSessionCount();
        this.totalSessions = source.getAttendance().getTotalSessions().getSessionCount();
    }

    /**
     * Converts this Jackson-friendly adapted CCA information object into the model's {@code CcaInformation} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted CCA information.
     */
    public CcaInformation toModelType() throws IllegalValueException {
        if (cca == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "CCA"));
        }
        if (role == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Role"));
        }

        final Cca modelCca = cca.toModelType();
        final Optional<Role> modelRole = role != null ? Optional.of(role.toModelType()) : Optional.empty();

        final int derivedTotalSessions = modelCca.getTotalSessions().getSessionCount();

        final Attendance modelAttendance = new Attendance(
                new SessionCount(attendedSessions), new SessionCount(derivedTotalSessions));

        return new CcaInformation(modelCca, modelRole, modelAttendance);
    }
}
