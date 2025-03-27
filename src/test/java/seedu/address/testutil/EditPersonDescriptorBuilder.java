package seedu.address.testutil;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.logic.commands.EditStudentCommand.EditPersonDescriptor;
import seedu.address.model.cca.Attendance;
import seedu.address.model.cca.Cca;
import seedu.address.model.cca.CcaInformation;
import seedu.address.model.cca.CcaName;
import seedu.address.model.cca.SessionCount;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.role.Role;

/**
 * A utility class to help with building EditPersonDescriptor objects.
 */
public class EditPersonDescriptorBuilder {

    private EditPersonDescriptor descriptor;

    public EditPersonDescriptorBuilder() {
        descriptor = new EditPersonDescriptor();
    }

    public EditPersonDescriptorBuilder(EditPersonDescriptor descriptor) {
        this.descriptor = new EditPersonDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPersonDescriptor} with fields containing {@code person}'s details
     */
    public EditPersonDescriptorBuilder(Person person) {
        descriptor = new EditPersonDescriptor();
        descriptor.setName(person.getName());
        descriptor.setPhone(person.getPhone());
        descriptor.setEmail(person.getEmail());
        descriptor.setAddress(person.getAddress());
        descriptor.setCcaInformation(person.getCcaInformations());
    }

    /**
     * Sets the {@code Name} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Parses the provided CCA and role into a {@code Set<CcaInformation>} and assigns it to the `EditPersonDescriptor`.
     * By default, it initializes the attendance with zero attended sessions.
     *
     * @param ccaName The name of the CCA.
     * @param roleName The role associated with the CCA.
     * @param totalSessions The total number of sessions for this CCA.
     * @return The updated `EditPersonDescriptorBuilder` instance.
     */
    public EditPersonDescriptorBuilder withCcaInformation(String ccaName, String roleName, int totalSessions) {
        Set<CcaInformation> ccaInfoSet = new HashSet<>();

        Cca cca = new Cca(new CcaName(ccaName), new HashSet<>(), new SessionCount(totalSessions));
        Role role = new Role(roleName);
        Attendance attendance = new Attendance(new SessionCount(0), new SessionCount(totalSessions));

        ccaInfoSet.add(new CcaInformation(cca, Optional.of(role), attendance));
        descriptor.setCcaInformation(ccaInfoSet);
        return this;
    }

    public EditPersonDescriptor build() {
        return descriptor;
    }
}
