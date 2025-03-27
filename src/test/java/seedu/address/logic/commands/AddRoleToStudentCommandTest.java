package seedu.address.logic.commands;

import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditStudentCommand.
 */
public class AddRoleToStudentCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    // TODO: Add tests for AddRoleToStudentCommand after improving code quality for test utils.
    @Test
    public void constructor_nullStudentIndex_throwsNullPointerException() {
    }

    @Test
    public void execute_validInputsUnfilteredList_success() {
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
    }

    @Test
    public void execute_invalidCcaNameUnfilteredList_throwsCommandException() {
    }

    @Test
    public void execute_invalidRoleUnfilteredList_throwsCommandException() {
    }

    @Test
    public void execute_validInputsFilteredList_success() {
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
    }

    @Test
    public void execute_invalidCcaNameFilteredList_throwsCommandException() {
    }

    @Test
    public void execute_invalidRoleFilteredList_throwsCommandException() {
    }

    @Test
    public void equals() {
    }

    @Test
    public void toStringMethod() {

    }

    @Test
    public void findCcaWithCcaName_validInputs_success() {

    }

    @Test
    public void findCcaWithCcaName_invalidInputs_failure() {
        // invalid cca name

        // empty cca list

    }
}
