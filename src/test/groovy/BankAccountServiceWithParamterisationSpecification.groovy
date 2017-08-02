import com.ebi.bank.domain.BankAccount
import com.ebi.bank.service.BankAccountService
import com.ebi.bank.service.IdGenerator
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

class BankAccountServiceWithParamterisationSpecification extends Specification {
    @Shared
    static final UUID = "444b378f-bf1c-4878-86e7-ca27c77a7414"
    @Shared
    static final NUMERIC_ID = "87"

    def generatorMock = Mock(IdGenerator)
    def service = new BankAccountService(generatorMock)

    @Unroll
    def "Should generate bank account with '#type' successfully"() {
        given:
        1 * generatorMock.generate(IdGenerator.Type.UUID) >> accountId

        when:
        service.generateBankAccount(IdGenerator.Type.UUID)

        then:
        def account = service.getBankAccount(accountId)
        account instanceof BankAccount
        account.id == accountId

        where:
        type                     |  accountId
        IdGenerator.Type.UUID    |  UUID
        IdGenerator.Type.NUMERIC |  NUMERIC_ID

    }

}