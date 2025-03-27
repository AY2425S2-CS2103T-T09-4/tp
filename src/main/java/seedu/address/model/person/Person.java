package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.cca.Amount;
import seedu.address.model.cca.Attendance;
import seedu.address.model.cca.Cca;
import seedu.address.model.cca.CcaInformation;
import seedu.address.model.cca.CcaName;
import seedu.address.model.cca.exceptions.CcaNotFoundException;
import seedu.address.model.role.Role;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Set<CcaInformation> ccaInformations;

    /**
     * Constructs a {@code Person}.
     * Ensures all fields are non-null and initializes with a defensive copy of the provided set.
     *
     * @param name The person's name.
     * @param phone The person's phone number.
     * @param email The person's email address.
     * @param address The person's address.
     * @param ccaInformations The set of CCA-related information associated with the person.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<CcaInformation> ccaInformations) {
        requireAllNonNull(name, phone, email, address, ccaInformations);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.ccaInformations = new HashSet<>(ccaInformations);
    }

    /**
     * Returns the person's name.
     *
     * @return A {@code Name} object representing the person's name.
     */
    public Name getName() {
        return name;
    }

    /**
     * Returns the person's phone number.
     *
     * @return A {@code Phone} object representing the person's phone number.
     */
    public Phone getPhone() {
        return phone;
    }

    /**
     * Returns the person's email address.
     *
     * @return An {@code Email} object representing the person's email address.
     */
    public Email getEmail() {
        return email;
    }

    /**
     * Returns the person's address.
     *
     * @return An {@code Address} object representing the person's address.
     */
    public Address getAddress() {
        return address;
    }

    // TODO: Remove this method after record attendance method in model is removed.
    /**
     * Returns the CCA information associated with the specified CCA.
     *
     * @param ccaName The CCA name to retrieve information for.
     * @return The {@code CcaInformation} object associated with the specified CCA.
     */
    public CcaInformation getCcaInformation(CcaName ccaName) throws CcaNotFoundException {
        for (CcaInformation ccaInformation : ccaInformations) {
            if (ccaInformation.getCca().getCcaName().equals(ccaName)) {
                return ccaInformation;
            }
        }
        throw new CcaNotFoundException();
    }

    /**
     * Returns the CCA information associated with the specified CCA.
     * {@code cca} must exist in the person's CCA information.
     *
     * @param cca The CCA to retrieve information for.
     * @return The {@code CcaInformation} object associated with the specified CCA.
     * @throws CcaNotFoundException If the specified CCA is not found in the person's CCA information.
     */
    public CcaInformation getCcaInformation(Cca cca) {
        for (CcaInformation ccaInformation : ccaInformations) {
            if (ccaInformation.getCca().equals(cca)) {
                return ccaInformation;
            }
        }
        throw new CcaNotFoundException();
    }
    /**
     * Returns an unmodifiable view of the person's CCA information.
     * Prevents external modifications to maintain immutability.
     *
     * @return An unmodifiable {@code Set<CcaInformation>} associated with the person.
     */
    public Set<CcaInformation> getCcaInformations() {
        return Collections.unmodifiableSet(ccaInformations);
    }

    /**
     * Returns a list of CCAs associated with this person.
     * Extracts the Cca objects from the CcaInformation set.
     *
     * @return An unmodifiable list of Cca objects.
     */
    public List<Cca> getCcas() {
        List<Cca> ccas = new ArrayList<>();
        for (CcaInformation ccaInformation : ccaInformations) {
            ccas.add(ccaInformation.getCca());
        }
        return Collections.unmodifiableList(ccas);
    }

    /**
     * Removes the specified CCA from the person's CCA information.
     *
     * @param cca The CCA to remove.
     * @return A new Person object with the specified CCA removed.
     */
    public Person removeCca(Cca cca) {
        Set<CcaInformation> newCcaInformations = new HashSet<>(ccaInformations);
        newCcaInformations.removeIf(c -> c.getCca().equals(cca));
        return new Person(name, phone, email, address, newCcaInformations);
    }

    /**
     * Updates the attendance record of the person for the specified CCA.
     *
     * @param ccaName The CCA name to update attendance for.
     * @param amount The amount to add to the person's attendance record.
     * @return A new Person object with the updated attendance record.
     */
    public Person attendCca(CcaName ccaName, Amount amount) throws IllegalArgumentException, CcaNotFoundException {
        if (!hasCca(ccaName)) {
            throw new CcaNotFoundException();
        }
        CcaInformation ccaInformation = getCcaInformation(ccaName);
        Attendance attendance = ccaInformation.getAttendance();
        Attendance newAttendance = attendance.attend(amount);
        CcaInformation newCcaInformation = new CcaInformation(ccaInformation.getCca(), ccaInformation.getRole(),
                newAttendance);
        Set<CcaInformation> newCcaInformations = new HashSet<>(ccaInformations);
        newCcaInformations.remove(ccaInformation);
        newCcaInformations.add(newCcaInformation);
        return new Person(name, phone, email, address, newCcaInformations);
    }

    /**
     * Adds the specified role to the person for the specified CCA name.
     * The current {@code role} of the person in the CCA must be the default role.
     * {@code role} must be a valid role defined in the CCA and not the default role.
     * {@code cca} must exist in the person's CCA information.
     *
     * @param cca The CCA name to add role for.
     * @param role The role to add.
     * @return A new Person object with the specified role added.
     */
    public Person addRole(Cca cca, Role role) {
        CcaInformation oldCcaInformation = getCcaInformation(cca);

        CcaInformation newCcaInformation = oldCcaInformation.addRole(role);

        // Replace old ccaInformation with new ccaInformation.
        Set<CcaInformation> newCcaInformations = ccaInformations;
        newCcaInformations.remove(oldCcaInformation);
        newCcaInformations.add(newCcaInformation);

        return new Person(name, phone, email, address, newCcaInformations);
    }

    /**
     * Deletes the role of the person for the specified CCA.
     * The current {@code role} of the person in the CCA must not be the default role.
     * {@code cca} must exist in the person's CCA information.
     *
     * @param cca The CCA to delete role for.
     * @return A new Person object with the role deleted.
     */
    public Person deleteRole(Cca cca) {
        CcaInformation oldCcaInformation = getCcaInformation(cca);

        CcaInformation newCcaInformation = oldCcaInformation.deleteRole();

        // Replace old ccaInformation with new ccaInformation.
        Set<CcaInformation> newCcaInformations = ccaInformations;
        newCcaInformations.remove(oldCcaInformation);
        newCcaInformations.add(newCcaInformation);

        return new Person(name, phone, email, address, newCcaInformations);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     *
     * @param otherPerson The person to compare against.
     * @return {@code true} if both persons have the same name, otherwise {@code false}.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

    /**
     * Returns true if the person has the default role in the specified CCA.
     *
     * @param cca The CCA to check for default role.
     * @return {@code true} if the person has the default role in the specified CCA, otherwise {@code false}.
     */
    public boolean isDefaultRoleInCca(Cca cca) {
        CcaInformation ccaInformation = getCcaInformation(cca);
        return ccaInformation.isDefaultRole();
    }

    /**
     * Returns true if the person has the specified CCA.
     *
     * @param cca The CCA to check for.
     * @return {@code true} if the person has the specified CCA, otherwise {@code false}.
     */
    public boolean hasCca(Cca cca) {
        for (Cca c : getCcas()) {
            if (c.equals(cca)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if the person has the specified CCA.
     *
     * @param ccaName The CCA name to check for.
     * @return {@code true} if the person has the specified CCA, otherwise {@code false}.
     */
    public boolean hasCca(CcaName ccaName) {
        for (Cca c : getCcas()) {
            if (c.getCcaName().equals(ccaName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if two persons are equal based on all identity and data fields.
     * Defines a stronger notion of equality.
     *
     * @param other The object to compare against.
     * @return {@code true} if both persons have the same details, otherwise {@code false}.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
                && ccaInformations.equals(otherPerson.ccaInformations);
    }

    /**
     * Generates a hash code for this person based on all identity and data fields.
     *
     * @return A hash code value for this person.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, phone, email, address, ccaInformations);
    }

    /**
     * Returns a string representation of the person.
     *
     * @return A formatted string containing the person's details.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("ccainformations", ccaInformations)
                .toString();
    }

}
