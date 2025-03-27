package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCcaCommand.EditCcaDescriptor;
import seedu.address.model.cca.Cca;
import seedu.address.model.cca.CcaName;
import seedu.address.model.cca.SessionCount;
import seedu.address.model.role.Role;

/**
 * A utility class to help with building EditCcaDescriptor objects.
 */
public class EditCcaDescriptorBuilder {

    private EditCcaDescriptor descriptor;

    public EditCcaDescriptorBuilder() {
        descriptor = new EditCcaDescriptor();
    }

    public EditCcaDescriptorBuilder(EditCcaDescriptor descriptor) {
        this.descriptor = new EditCcaDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditCcaDescriptor} with fields containing {@code cca}'s details
     */
    public EditCcaDescriptorBuilder(Cca cca) {
        descriptor = new EditCcaDescriptor();
        descriptor.setCcaName(cca.getCcaName());
        descriptor.setRoles(cca.getRoles());
        descriptor.setTotalSessions(cca.getTotalSessions());
    }

    /**
     * Sets the {@code CcaName} of the {@code EditCcaDescriptor} that we are building.
     */
    public EditCcaDescriptorBuilder withCcaName(String ccaName) {
        descriptor.setCcaName(new CcaName(ccaName));
        return this;
    }

    /**
     * Parses the {@code roles} into a {@code Set<Role>} and set it to the {@code EditCcaDescriptor}
     * that we are building.
     */
    public EditCcaDescriptorBuilder withRoles(String... roles) {
        Set<Role> roleSet = Stream.of(roles).map(Role::new).collect(Collectors.toSet());
        descriptor.setRoles(roleSet);
        return this;
    }

    /**
     * Sets the {@code SessionCount} of the {@code EditCcaDescriptor} that we are building.
     */
    public EditCcaDescriptorBuilder withTotalSessions(String totalSessions) {
        descriptor.setTotalSessions(new SessionCount(Integer.parseInt(totalSessions)));
        return this;
    }

    public EditCcaDescriptor build() {
        return descriptor;
    }
}
