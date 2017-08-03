import com.ebi.bank.domain.BankAccount;
import com.ebi.bank.service.BankAccountService;
import com.ebi.bank.service.IdGenerator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static junit.framework.TestCase.fail;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class BankAccountServiceTest {
    private static final String UUID = "444b378f-bf1c-4878-86e7-ca27c77a7414";
    private static final String NUMERIC_ID = "87";

    @Mock
    IdGenerator generatorMock;

    BankAccountService service;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        service = new BankAccountService(generatorMock);
    }

    @Test
    public void shouldCreateBankAccountSuccessfully() {
        try {
            service.createBankAccount(UUID);
        } catch (IllegalArgumentException e) {
            fail("IllegalArgumentException was thrown");
        }
    }

    @Test
    public void shouldGenerateBankAccountWithUuidSuccessfully() {
        when(generatorMock.generate(eq(IdGenerator.Type.UUID))).thenReturn(UUID);

        service.generateBankAccount(IdGenerator.Type.UUID);

        verify(generatorMock, times(1)).generate(eq(IdGenerator.Type.UUID));

        BankAccount bankAccount = service.getBankAccount(UUID);

        assertThat(bankAccount, instanceOf(BankAccount.class));
        assertThat(bankAccount.getId(), equalTo(UUID));
    }

    @Test
    public void shouldGenerateBankAccountWithNumericIdSuccessfully() {
        when(generatorMock.generate(eq(IdGenerator.Type.NUMERIC))).thenReturn(NUMERIC_ID);

        service.generateBankAccount(IdGenerator.Type.NUMERIC);

        verify(generatorMock, times(1)).generate(eq(IdGenerator.Type.NUMERIC));

        BankAccount bankAccount = service.getBankAccount(NUMERIC_ID);

        assertThat(bankAccount, instanceOf(BankAccount.class));
        assertThat(bankAccount.getId(), equalTo(NUMERIC_ID));
    }

    @Test
    public void shouldGetBankAccountByIbanSuccessfully() {
        service.createBankAccount(UUID);

        BankAccount bankAccount = service.getBankAccount(UUID);

        assertThat(bankAccount, instanceOf(BankAccount.class));
        assertThat(bankAccount.getId(), equalTo(UUID));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenTryingToCreateDuplicateBankAccounts() {
        service.createBankAccount(UUID);
        service.createBankAccount(UUID);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionIfBankAccountIsNotFoundByIban() {
        service.getBankAccount(UUID);
    }
}