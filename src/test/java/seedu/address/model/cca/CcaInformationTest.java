package seedu.address.model.cca;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.role.Role;

public class CcaInformationTest {

    @Test
    public void equals_sameValues_returnsTrue() {
        Cca cca = new Cca(new CcaName("Basketball"), Set.of(new Role("Captain")), new SessionCount(10));
        Role role = new Role("Captain");
        Attendance attendance = new Attendance(new SessionCount(5), new SessionCount(10));

        CcaInformation ccaInfo1 = new CcaInformation(cca, Optional.of(role), attendance);
        CcaInformation ccaInfo2 = new CcaInformation(cca, Optional.of(role), attendance);

        assertEquals(ccaInfo1, ccaInfo2);
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        Cca cca1 = new Cca(new CcaName("Basketball"), Set.of(new Role("Captain")), new SessionCount(10));
        Cca cca2 = new Cca(new CcaName("Soccer"), Set.of(new Role("Player")), new SessionCount(8));
        Role role1 = new Role("Captain");
        Role role2 = new Role("Player");
        Attendance attendance1 = new Attendance(new SessionCount(5), new SessionCount(10));
        Attendance attendance2 = new Attendance(new SessionCount(3), new SessionCount(8));

        CcaInformation ccaInfo1 = new CcaInformation(cca1, Optional.of(role1), attendance1);
        CcaInformation ccaInfo2 = new CcaInformation(cca2, Optional.of(role2), attendance2);

        assertNotEquals(ccaInfo1, ccaInfo2);
    }

    @Test
    public void toString_correctFormat() {
        Cca cca = new Cca(new CcaName("Basketball"), Set.of(new Role("Captain")), new SessionCount(10));
        Role role = new Role("Captain");
        Attendance attendance = new Attendance(new SessionCount(5), new SessionCount(10));
        CcaInformation ccaInfo = new CcaInformation(cca, Optional.of(role), attendance);

        String expectedString = "[Basketball] | Role: [Captain] | Attendance: 5/10";
        assertEquals(expectedString, ccaInfo.toString());
    }
}
