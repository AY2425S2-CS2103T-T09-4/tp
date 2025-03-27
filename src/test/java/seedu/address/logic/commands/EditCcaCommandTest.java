package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CCA;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CCA;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditCcaCommand.EditCcaDescriptor;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.cca.Cca;
import seedu.address.testutil.CcaBuilder;
import seedu.address.testutil.EditCcaDescriptorBuilder;

public class EditCcaCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_allFieldsSpecified_success() throws Exception {
        Cca editedCca = new CcaBuilder().withCcaName("Choir").withRoles("President").withTotalSessions(10).build();
        EditCcaDescriptor descriptor = new EditCcaDescriptorBuilder(editedCca).build();
        EditCcaCommand command = new EditCcaCommand(INDEX_FIRST_CCA, descriptor);

        String expectedMessage = String.format(EditCcaCommand.MESSAGE_EDIT_CCA_SUCCESS, Messages.format(editedCca));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setCca(model.getCcaList().get(0), editedCca);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getCcaList().size() + 1);
        EditCcaCommand.EditCcaDescriptor descriptor = new EditCcaDescriptorBuilder().withCcaName("NewCca").build();
        EditCcaCommand command = new EditCcaCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_CCA_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        EditCcaDescriptor descriptor = new EditCcaDescriptorBuilder().withCcaName("Choir").build();
        EditCcaCommand command1 = new EditCcaCommand(INDEX_FIRST_CCA, descriptor);
        EditCcaCommand command2 = new EditCcaCommand(INDEX_FIRST_CCA, new EditCcaDescriptor(descriptor));

        // same values -> returns true
        assertEquals(command1, command2);

        // different index -> returns false
        assertNotEquals(command1, new EditCcaCommand(INDEX_SECOND_CCA, descriptor));

        // different descriptor -> returns false
        EditCcaDescriptor differentDescriptor = new EditCcaDescriptorBuilder().withCcaName("Dance").build();
        assertNotEquals(command1, new EditCcaCommand(INDEX_FIRST_CCA, differentDescriptor));
    }
}

