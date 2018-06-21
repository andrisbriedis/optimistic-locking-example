package andrisbriedis.mongo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MongoApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Autowired
    AccountRepository accountRepository;

    @Test
	public void shouldSaveAndLoadAccount() {
		Account account = new Account("Andris Briedis");

		account.deposit(BigDecimal.TEN);

        account = accountRepository.save(account);

        Account savedAccount = accountRepository
                .findById(account.id())
                .orElseThrow(this::noAcount);

        assertEquals(BigDecimal.TEN, savedAccount.balance());
    }

    @Test(expected = OptimisticLockingFailureException.class)
    public void shouldDenyConcurrentUpdates() {
        Account account = new Account("Andris Briedis");

        account = accountRepository.save(account);
        account.deposit(BigDecimal.ONE);

        Account sameAccount = accountRepository
                .findById(account.id())
                .orElseThrow(this::noAcount);
        sameAccount.deposit(BigDecimal.TEN);

        account = accountRepository.save(account);
        assertEquals(BigDecimal.ONE, account.balance());

        accountRepository.save(sameAccount);
    }

    private AssertionError noAcount() {
        return new AssertionError("Could not find saved account");
    }

}


