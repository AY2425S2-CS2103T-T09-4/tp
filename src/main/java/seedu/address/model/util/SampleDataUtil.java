package seedu.address.model.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
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
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    /**
     * Returns an array of sample CCAs with predefined roles and session counts.
     *
     * @return An array of sample {@code Cca} objects.
     */
    public static Cca[] getSampleCcas() {
        Role captain = new Role("Captain");
        Role viceCaptain = new Role("Vice-Captain");
        Role president = new Role("President");
        Role vicePresident = new Role("Vice-President");
        Role member = new Role("Member");

        return new Cca[] {

            new Cca(new CcaName("Acting"),
                    new HashSet<>(Arrays.asList(president, vicePresident, member)), new SessionCount(10)),
            new Cca(new CcaName("Basketball"),
                    new HashSet<>(Arrays.asList(captain, viceCaptain, member)), new SessionCount(12)),
            new Cca(new CcaName("Badminton"),
                    new HashSet<>(Arrays.asList(captain, viceCaptain, member)), new SessionCount(15)),
            new Cca(new CcaName("Canoe"),
                    new HashSet<>(Arrays.asList(captain, viceCaptain, member)), new SessionCount(8)),
            new Cca(new CcaName("Dance"),
                    new HashSet<>(Arrays.asList(president, vicePresident, member)), new SessionCount(20)),
            new Cca(new CcaName("E Sports"),
                    new HashSet<>(Arrays.asList(president, vicePresident, member)), new SessionCount(5)),
            new Cca(new CcaName("Football"),
                    new HashSet<>(Arrays.asList(captain, viceCaptain, member)), new SessionCount(14))
        };
    }

    public static Person[] getSamplePersons(ObservableList<Cca> uniqueCcaList) {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                    getCcaInformationSet(uniqueCcaList, "Canoe", "Member", "5")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    getCcaInformationSet(uniqueCcaList, "Basketball", "Vice-Captain", "10")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    getCcaInformationSet(uniqueCcaList, "Badminton", "Member", "10")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    getCcaInformationSet(uniqueCcaList, "E Sports", "Vice-President", "4")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                    getCcaInformationSet(uniqueCcaList, "Football", "Captain", "5")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                    getCcaInformationSet(uniqueCcaList, "Dance", "President", "5"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Cca sampleCca : getSampleCcas()) {
            sampleAb.addCca(sampleCca);
        }

        ObservableList<Cca> uniqueCcaList = sampleAb.getCcaList();

        for (Person samplePerson : getSamplePersons(uniqueCcaList)) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a set of CCA information objects based on the provided data.
     *
     * - **Checks if the CCA exists** in `uniqueCcaList` (compares names).
     * - **Uses the existing CCA reference** if found.
     * - **Checks if the role exists** in the CCA's role set.
     * - **Retrieves total sessions** from the existing CCA instead of user input.
     *
     * @param uniqueCcaList The list of existing CCAs.
     * @param ccaInformationData Alternating parameters: CCA Name, Role, Attended Sessions.
     * @return A set of {@code CcaInformation} objects.
     */
    public static Set<CcaInformation> getCcaInformationSet(
            ObservableList<Cca> uniqueCcaList, String... ccaInformationData) {

        Set<CcaInformation> ccaInformationSet = new HashSet<>();

        for (int i = 0; i < ccaInformationData.length; i += 3) {
            String ccaName = ccaInformationData[i];
            String roleName = ccaInformationData[i + 1];
            int attendedSessions = Integer.parseInt(ccaInformationData[i + 2]);

            Cca existingCca = getCcaByName(uniqueCcaList, new CcaName(ccaName)); //Check if the cca exists
            int totalSessions = existingCca.getTotalSessions().getSessionCount();

            // Check if the role exists in the cca
            Optional<Role> roleReference = Optional.ofNullable(findRoleInCca(existingCca, roleName));

            // Create Attendance object
            Attendance attendance = new Attendance(new SessionCount(attendedSessions), new SessionCount(totalSessions));

            // Create CcaInformation and add to the set
            ccaInformationSet.add(new CcaInformation(existingCca, roleReference, attendance));
        }
        return ccaInformationSet;
    }

    /**
     * Retrieves a CCA by its name from the given list.
     *
     * @param ccaList The observable list of CCAs.
     * @param ccaName The CCA name to search for.
     * @return The matching {@code Cca} object if found, otherwise {@code null}.
     */
    public static Cca getCcaByName(ObservableList<Cca> ccaList, CcaName ccaName) {
        return ccaList.stream()
                .filter(cca -> cca.getCcaName().equals(ccaName))
                .findFirst()
                .orElse(null);
    }

    /**
     * Finds a role in the given CCA.
     *
     * @param cca The CCA to search in.
     * @param roleName The name of the role to find.
     * @return The matching {@code Role} if found, otherwise {@code null}.
     */
    private static Role findRoleInCca(Cca cca, String roleName) {
        return cca.getRoles().stream()
                .filter(role -> role.roleName.equals(roleName))
                .findFirst()
                .orElse(null);
    }

    /**
     * Returns a role set containing the list of strings given.
     */
    public static Set<Role> getRoleSet(String... strings) {
        return Arrays.stream(strings)
                .map(Role::new)
                .collect(Collectors.toSet());
    }

    /**
     * Returns a cca set containing the list of strings given.
     */
    public static Set<Cca> getCcaSet(String... strings) {
        return Arrays.stream(strings)
                .map(CcaName::new)
                .map(Cca::new)
                .collect(Collectors.toSet());
    }

}
