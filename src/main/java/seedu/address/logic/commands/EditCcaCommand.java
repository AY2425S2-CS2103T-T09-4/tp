package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CCA_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TOTAL_SESSIONS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.cca.Cca;
import seedu.address.model.cca.CcaName;
import seedu.address.model.cca.SessionCount;
import seedu.address.model.role.Role;

/**
 * Edits the details of an existing cca in the address book.
 */
public class EditCcaCommand extends Command {

    public static final String COMMAND_WORD = "edit_c";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the cca identified "
            + "by the index number used in the displayed cca list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_CCA_NAME + "CCA NAME] "
            + "[" + PREFIX_ROLE + "ROLE]...\n"
            + "[" + PREFIX_TOTAL_SESSIONS + "TOTAL SESSIONS]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_CCA_NAME + "Choir "
            + PREFIX_ROLE + "Treasurer "
            + PREFIX_TOTAL_SESSIONS + "14 ";

    public static final String MESSAGE_EDIT_CCA_SUCCESS = "Edited cca: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_CCA = "This cca already exists in the address book.";

    private final Index index;
    private final EditCcaDescriptor editCcaDescriptor;

    /**
     * @param index of the cca in the cca list to edit
     * @param editCcaDescriptor details to edit the student with
     */
    public EditCcaCommand(Index index, EditCcaDescriptor editCcaDescriptor) {
        requireNonNull(index);
        requireNonNull(editCcaDescriptor);
        this.index = index;
        this.editCcaDescriptor = editCcaDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Cca> lastShownList = model.getCcaList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CCA_DISPLAYED_INDEX);
        }

        Cca ccaToEdit = lastShownList.get(index.getZeroBased());
        Cca editedCca = createEditedCca(ccaToEdit, editCcaDescriptor);

        if (!ccaToEdit.isSameCca(editedCca) && model.hasCca(editedCca)) {
            throw new CommandException(MESSAGE_DUPLICATE_CCA);
        }

        model.setCca(ccaToEdit, editedCca);

        return new CommandResult(String.format(MESSAGE_EDIT_CCA_SUCCESS, Messages.format(editedCca)));
    }

    /**
     * Creates and returns a {@code Cca} with the details of {@code ccaToEdit}
     * edited with {@code editCcaDescriptor}.
     */
    private static Cca createEditedCca(Cca ccaToEdit, EditCcaDescriptor editCcaDescriptor) {
        assert ccaToEdit != null;

        CcaName updatedCcaName = editCcaDescriptor.getName().orElse(ccaToEdit.getCcaName());
        Set<Role> updatedRoles = editCcaDescriptor.getRoles().orElse(ccaToEdit.getRoles());
        SessionCount updatedTotalSessions = editCcaDescriptor.getTotalSessions().orElse(ccaToEdit.getTotalSessions());

        return new Cca(updatedCcaName, updatedRoles, updatedTotalSessions);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCcaCommand)) {
            return false;
        }

        EditCcaCommand otherEditCcaCommand = (EditCcaCommand) other;
        return index.equals(otherEditCcaCommand.index)
                && editCcaDescriptor.equals(otherEditCcaCommand.editCcaDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editCcaDescriptor", editCcaDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the cca with. Each non-empty field value will replace the
     * corresponding field value of the cca.
     */
    public static class EditCcaDescriptor {
        private CcaName ccaName;
        private Set<Role> roles;
        private SessionCount totalSessions;

        public EditCcaDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code roles} is used internally.
         */
        public EditCcaDescriptor(EditCcaDescriptor toCopy) {
            setCcaName(toCopy.ccaName);
            setRoles(toCopy.roles);
            setTotalSessions(toCopy.totalSessions);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(ccaName, roles, totalSessions);
        }

        public void setCcaName(CcaName ccaName) {
            this.ccaName = ccaName;
        }

        public Optional<CcaName> getName() {
            return Optional.ofNullable(ccaName);
        }

        /**
         * Sets {@code roles} to this object's {@code roles}.
         * A defensive copy of {@code roles} is used internally.
         */
        public void setRoles(Set<Role> roles) {
            this.roles = (roles != null) ? new HashSet<>(roles) : null;
        }

        /**
         * Returns an unmodifiable set of roles in the cca.
         * Returns {@code Optional#empty()} if {@code roles} is null.
         */
        public Optional<Set<Role>> getRoles() {
            return (roles != null)
                    ? Optional.of(Collections.unmodifiableSet(roles)) : Optional.empty();
        }

        public void setTotalSessions(SessionCount totalSessions) {
            this.totalSessions = totalSessions;
        }

        public Optional<SessionCount> getTotalSessions() {
            return Optional.ofNullable(totalSessions);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditCcaDescriptor)) {
                return false;
            }

            EditCcaDescriptor otherEditCcaDescriptor = (EditCcaDescriptor) other;
            return Objects.equals(ccaName, otherEditCcaDescriptor.ccaName)
                    && Objects.equals(roles, otherEditCcaDescriptor.roles)
                    && Objects.equals(totalSessions, otherEditCcaDescriptor.totalSessions);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("cca name", ccaName)
                    .add("roles", roles)
                    .add("total sessions", totalSessions)
                    .toString();
        }
    }
}
