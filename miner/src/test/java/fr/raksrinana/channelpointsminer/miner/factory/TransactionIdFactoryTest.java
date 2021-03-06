package fr.raksrinana.channelpointsminer.miner.factory;

import fr.raksrinana.channelpointsminer.miner.tests.ParallelizableTest;
import org.junit.jupiter.api.RepeatedTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ParallelizableTest
class TransactionIdFactoryTest{
	@RepeatedTest(15)
	void create(){
		var generated = TransactionIdFactory.create();
		
		assertThat(generated).isNotNull().hasSize(32);
		
		var hexChars = generated.chars().allMatch(c -> ('0' <= c && c <= '9') || ('a' <= c && c <= 'z'));
		assertTrue(hexChars);
	}
}