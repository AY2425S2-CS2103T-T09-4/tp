package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CCA;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.model.role.Role.DEFAULT_ROLE_NAME;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.cca.Cca;
import seedu.address.model.cca.CcaName;
import seedu.address.model.person.Person;
import seedu.address.model.role.Role;

/**
 * Adds a role of a specified cca to a student identified using it's displayed index from the address book.
 */
public class AddRoleToStudentCommand extends Command {

    public static final String COMMAND_WORD = "add_r";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a role to the student identified "
            + "by the index number used in the displayed student list. "
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_CCA + "CCA_NAME "
            + PREFIX_ROLE + "ROLE\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_CCA + "Basketball "
            + PREFIX_ROLE + "Center";

    public static final String MESSAGE_ADD_ROLE_TO_STUDENT_SUCCESS = "Added role to student: %1$s";
    public static final String MESSAGE_ROLE_ALREADY_ASSIGNED = "This student already has a role in this CCA.";
    public static final String MESSAGE_CANNOT_ASSIGN_DEFAULT_ROLE = "Cannot assign a default role "
            + DEFAULT_ROLE_NAME + " to a student.";

    private final Index studentIndex;
    private final CcaName ccaName;
    private final Role role;

    /**
     * @param studentIndex of the student in the filtered student list to add role
     * @param ccaName of the CCA to add role
     * @param role of the role to add
     */
    public AddRoleToStudentCommand(Index studentIndex, CcaName ccaName, Role role) {
        requireNonNull(studentIndex);
        requireNonNull(ccaName);
        requireNonNull(role);
        this.studentIndex = studentIndex;
        this.ccaName = ccaName;
        this.role = role;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownPersonList = model.getFilteredPersonList();
        List<Cca> lastCcaList = model.getCcaList();

        if (studentIndex.getZeroBased() >= lastShownPersonList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        Person personToAddRole = lastShownPersonList.get(studentIndex.getZeroBased());

        Optional<Cca> searchedCca = findCcaWithCcaName(lastCcaList, ccaName);
        if (searchedCca.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_CCA_NOT_FOUND);
        }
        Cca targetCca = searchedCca.get();

        if (!personToAddRole.hasCca(targetCca)) {
            throw new CommandException(Messages.MESSAGE_CCA_NOT_IN_PERSON);
        }

        if (!personToAddRole.isDefaultRoleInCca(targetCca)) {
            throw new CommandException(MESSAGE_ROLE_ALREADY_ASSIGNED);
        }

        if (role.isDefaultRole()) {
            throw new CommandException(MESSAGE_CANNOT_ASSIGN_DEFAULT_ROLE);
        }

        if (!targetCca.hasRole(role)) {
            throw new CommandException(Messages.MESSAGE_ROLE_NOT_FOUND);
        }

        Person personWithAddedRole = personToAddRole.addRole(targetCca, role);

        try {

            model.setPerson(personToAddRole, personWithAddedRole);

        } catch (IllegalArgumentException e) {
            throw new CommandException(Messages.MESSAGE_CCA_NOT_FOUND);
        }

        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(
                MESSAGE_ADD_ROLE_TO_STUDENT_SUCCESS, Messages.format(personWithAddedRole)));
    }

    /**
     * Finds a {@code Cca} from a list of {@code Cca} with the given {@code CcaName}.
     *
     * @param ccaList list of {@code Cca} to search from
     * @param ccaName {@code CcaName} to search for
     * @return {@code Optional<Cca>} containing the {@code Cca} if found, otherwise {@code Optional.empty()}
     */
    public Optional<Cca> findCcaWithCcaName(List<Cca> ccaList, CcaName ccaName) {
        for (Cca cca : ccaList) {
            if (cca.getCcaName().equals(ccaName)) {
                return Optional.of(cca);
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddRoleToStudentCommand)) {
            return false;
        }

        AddRoleToStudentCommand otherCommand = (AddRoleToStudentCommand) other;
        return studentIndex.equals(otherCommand.studentIndex)
                && ccaName.equals(otherCommand.ccaName)
                && role.equals(otherCommand.role);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("studentIndex", studentIndex)
                .add("ccaName", ccaName)
                .add("role", role)
                .toString();
    }
}
