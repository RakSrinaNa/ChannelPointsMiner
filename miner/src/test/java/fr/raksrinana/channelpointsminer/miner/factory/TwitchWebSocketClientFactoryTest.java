package fr.raksrinana.channelpointsminer.miner.factory;

import fr.raksrinana.channelpointsminer.miner.tests.ParallelizableTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

@ParallelizableTest
class TwitchWebSocketClientFactoryTest{
	@Test
	void create(){
		Assertions.assertThat(TwitchWebSocketClientFactory.createClient()).isNotNull();
	}
}