import com.ebi.bank.domain.BankAccount;
import com.ebi.bank.service.BankAccountService;
import com.ebi.bank.service.IdGenerator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.mockito.Mockito.*;

@RunWith(Parameterized.class)
public class BankAccountServiceParameterizedTest {
    private static final String UUID = "444b378f-bf1c-4878-86e7-ca27c77a7414";
    private static final String NUMERIC_ID = "87";
    private static final String DEFAULT_ID = UUID;

    @Mock
    private IdGenerator generatorMock;

    private BankAccountService service;

    @Parameterized.Parameter
    public String testName;

    @Parameterized.Parameter(1)
    public String accountId;

    @Parameterized.Parameter(2)
    public IdGenerator.Type idType;

    @Parameterized.Parameters(name = "({0})")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"UUID", UUID, IdGenerator.Type.UUID},
                {"NUMERIC_ID", NUMERIC_ID, IdGenerator.Type.NUMERIC}
        });
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        service = new BankAccountService(generatorMock);
    }

    @Test
    public void shouldGenerateBankAccountWithUuidSuccessfully() {
        when(generatorMock.generate(idType)).thenReturn(accountId);

        service.generateBankAccount(idType);

        verify(generatorMock, times(1)).generate(idType);

        BankAccount bankAccount = service.getBankAccount(accountId);

        assertThat(bankAccount, instanceOf(BankAccount.class));
        assertThat(bankAccount.getId(), equalTo(accountId));
    }

}