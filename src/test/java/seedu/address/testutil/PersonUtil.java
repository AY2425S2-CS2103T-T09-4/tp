package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CCA;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;

import java.util.Set;

import seedu.address.logic.commands.CreateStudentCommand;
import seedu.address.logic.commands.EditStudentCommand.EditPersonDescriptor;
import seedu.address.model.cca.CcaInformation;
import seedu.address.model.person.Person;

/**
 * A utility class for Person.
 */
public class PersonUtil {

    /**
     * Returns a create student command string for adding the {@code person}.
     */
    public static String getCreateStudentCommand(Person person) {
        return CreateStudentCommand.COMMAND_WORD + " " + getPersonDetails(person);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getPersonDetails(Person person) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + person.getName().fullName + " ");
        sb.append(PREFIX_PHONE + person.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + person.getEmail().value + " ");
        sb.append(PREFIX_ADDRESS + person.getAddress().value + " ");

        // Extract CCA and role details
        person.getCcaInformations().forEach(ccaInfo -> {
            sb.append(PREFIX_CCA).append(ccaInfo.getCca().getCcaName().fullCcaName).append(" ");
            sb.append(PREFIX_ROLE).append(ccaInfo.getRole().map(role -> role.roleName)
                    .orElse("No role")).append(" ");
        });
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditPersonDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.value).append(" "));

        // Extract CCA and role details if present
        if (descriptor.getCcaInformation().isPresent()) {
            Set<CcaInformation> ccaInfoSet = descriptor.getCcaInformation().get();
            if (ccaInfoSet.isEmpty()) {
                sb.append(PREFIX_CCA); // Handle case where all CCAs are cleared
            } else {
                ccaInfoSet.forEach(ccaInfo -> {
                    sb.append(PREFIX_CCA).append(ccaInfo.getCca().getCcaName().fullCcaName).append(" ");
                    sb.append(PREFIX_ROLE).append(ccaInfo.getRole().map(role -> role.roleName)
                            .orElse("No role")).append(" ");
                });
            }
        }
        return sb.toString();
    }
}
