package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CCA_NAME_BASKETBALL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CCA_NAME_SWIMMING;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalCcas.BASKETBALL;
import static seedu.address.testutil.TypicalCcas.MEMBER;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.cca.Amount;
import seedu.address.model.cca.Cca;
import seedu.address.model.cca.CcaInformation;
import seedu.address.model.cca.CcaName;
import seedu.address.model.person.Person;
import seedu.address.testutil.ModelStub;

public class RecordAttendanceCommandTest {

    @Test
    public void execute_validPersonAttendance_success() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();

        Cca validCca = BASKETBALL;
        Set<CcaInformation> ccaInformations = new HashSet<>();
        ccaInformations.add(new CcaInformation(validCca, Optional.of(MEMBER), validCca.createNewAttendance()));
        Person validPerson = ALICE;
        modelStub.addCca(validCca);
        modelStub.addPerson(validPerson);

        Index studentIndex = Index.fromOneBased(1);
        CcaName ccaName = new CcaName(VALID_CCA_NAME_BASKETBALL);
        Amount amount = new Amount(1);

        RecordAttendanceCommand command = new RecordAttendanceCommand(studentIndex, ccaName, amount);
        CommandResult commandResult = command.execute(modelStub);

        assertEquals(String.format(RecordAttendanceCommand.MESSAGE_SUCCESS, validPerson.getName(), ccaName, amount),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_invalidStudentIndex_throwsCommandException() {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();

        Cca validCca = BASKETBALL;
        Set<CcaInformation> ccaInformations = new HashSet<>();
        ccaInformations.add(new CcaInformation(validCca, Optional.of(MEMBER), validCca.createNewAttendance()));
        Person validPerson = ALICE;
        modelStub.addCca(validCca);
        modelStub.addPerson(validPerson);

        Index outOfBoundIndex = Index.fromOneBased(2);
        CcaName ccaName = new CcaName(VALID_CCA_NAME_BASKETBALL);
        Amount amount = new Amount(1);

        RecordAttendanceCommand command = new RecordAttendanceCommand(outOfBoundIndex, ccaName, amount);
        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, (
                ) -> command.execute(modelStub));
    }

    @Test
    public void execute_ccaNotFound_throwsCommandException() {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Person validPerson = ALICE;
        modelStub.addPerson(validPerson);
        Cca validCca = BASKETBALL;
        modelStub.addCca(validCca);

        Index studentIndex = Index.fromOneBased(1);
        CcaName ccaName = new CcaName(VALID_CCA_NAME_SWIMMING);
        Amount amount = new Amount(1);

        RecordAttendanceCommand command = new RecordAttendanceCommand(studentIndex, ccaName, amount);
        assertThrows(CommandException.class, Messages.MESSAGE_CCA_NOT_FOUND, (
                ) -> command.execute(modelStub));
    }

    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Person> personsAdded = new ArrayList<>();
        final ArrayList<Cca> ccasAdded = new ArrayList<>();

        @Override
        public void addPerson(Person person) {
            requireNonNull(person);
            personsAdded.add(person);
        }

        @Override
        public void addCca(Cca cca) {
            requireNonNull(cca);
            ccasAdded.add(cca);
        }

        @Override
        public void recordAttendance(CcaName ccaName, Person person, Amount amount) {
            requireAllNonNull(person, ccaName, amount);
            Person newPerson = person.attendCca(ccaName, amount);
            personsAdded.set(0, newPerson);
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return FXCollections.observableList(personsAdded);
        }
    }
}
