package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CCA_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TOTAL_SESSIONS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CCA;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCcaCommand;
import seedu.address.logic.commands.EditCcaCommand.EditCcaDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.testutil.EditCcaDescriptorBuilder;

public class EditCcaCommandParserTest {

    private EditCcaCommandParser parser = new EditCcaCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws Exception {
        Index targetIndex = INDEX_FIRST_CCA;
        String userInput = targetIndex.getOneBased() + " "
                + PREFIX_CCA_NAME + "Choir "
                + PREFIX_ROLE + "President "
                + PREFIX_TOTAL_SESSIONS + "10";

        EditCcaDescriptor descriptor = new EditCcaDescriptorBuilder()
                .withCcaName("Choir")
                .withRoles("President")
                .withTotalSessions("10")
                .build();

        EditCcaCommand expectedCommand = new EditCcaCommand(targetIndex, descriptor);
        assertEquals(parser.parse(userInput), expectedCommand);
    }

    @Test
    public void parse_missingIndex_failure() {
        String userInput = PREFIX_CCA_NAME + "Choir";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_noFieldsEdited_failure() {
        String userInput = INDEX_FIRST_CCA.getOneBased() + "";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_invalidTotalSessions_failure() {
        String userInput = INDEX_FIRST_CCA.getOneBased() + " "
                + PREFIX_TOTAL_SESSIONS + "abc"; // non-numeric
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }
}
