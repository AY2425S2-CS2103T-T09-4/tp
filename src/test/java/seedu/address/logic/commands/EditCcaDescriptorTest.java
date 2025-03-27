package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditCcaCommand.EditCcaDescriptor;
import seedu.address.model.cca.CcaName;
import seedu.address.model.cca.SessionCount;
import seedu.address.model.role.Role;

public class EditCcaDescriptorTest {

    @Test
    public void equals_sameValues_returnsTrue() {
        EditCcaDescriptor descriptor1 = new EditCcaDescriptor();
        descriptor1.setCcaName(new CcaName("Choir"));
        descriptor1.setRoles(Set.of(new Role("President")));
        descriptor1.setTotalSessions(new SessionCount(10));

        EditCcaDescriptor descriptor2 = new EditCcaDescriptor(descriptor1);

        assertEquals(descriptor1, descriptor2);
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        EditCcaDescriptor descriptor1 = new EditCcaDescriptor();
        descriptor1.setCcaName(new CcaName("Choir"));

        EditCcaDescriptor descriptor2 = new EditCcaDescriptor();
        descriptor2.setCcaName(new CcaName("Dance"));

        assertNotEquals(descriptor1, descriptor2);
    }

    @Test
    public void getRoles_returnsUnmodifiableSet() {
        EditCcaDescriptor descriptor = new EditCcaDescriptor();
        Set<Role> roles = new HashSet<>();
        roles.add(new Role("Treasurer"));
        descriptor.setRoles(roles);

        Optional<Set<Role>> optionalRoles = descriptor.getRoles();
        assertTrue(optionalRoles.isPresent());

        Set<Role> retrievedRoles = optionalRoles.get();
        assertThrows(UnsupportedOperationException.class, () -> {
            retrievedRoles.add(new Role("President"));
        });
    }

    @Test
    public void getName_returnsOptional() {
        CcaName name = new CcaName("Football");
        EditCcaDescriptor descriptor = new EditCcaDescriptor();
        descriptor.setCcaName(name);

        assertEquals(Optional.of(name), descriptor.getName());
    }

    @Test
    public void getTotalSessions_returnsOptional() {
        SessionCount sessionCount = new SessionCount(15);
        EditCcaDescriptor descriptor = new EditCcaDescriptor();
        descriptor.setTotalSessions(sessionCount);

        assertEquals(Optional.of(sessionCount), descriptor.getTotalSessions());
    }

    @Test
    public void isAnyFieldEdited_allFieldsNull_returnsFalse() {
        EditCcaDescriptor descriptor = new EditCcaDescriptor();
        assertFalse(descriptor.isAnyFieldEdited());
    }

    @Test
    public void isAnyFieldEdited_someFieldSet_returnsTrue() {
        EditCcaDescriptor descriptor = new EditCcaDescriptor();
        descriptor.setCcaName(new CcaName("Netball"));

        assertTrue(descriptor.isAnyFieldEdited());
    }
}
