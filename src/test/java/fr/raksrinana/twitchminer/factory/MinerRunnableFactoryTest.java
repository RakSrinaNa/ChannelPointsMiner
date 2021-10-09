package fr.raksrinana.twitchminer.factory;

import fr.raksrinana.twitchminer.miner.IMiner;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MinerRunnableFactoryTest{
	@Mock
	private IMiner miner;
	
	@Test
	void getUpdateChannelPointsContext(){
		assertThat(MinerRunnableFactory.getUpdateChannelPointsContext(miner)).isNotNull();
	}
	
	@Test
	void getUpdateStreamInfo(){
		assertThat(MinerRunnableFactory.getUpdateStreamInfo(miner)).isNotNull();
	}
	
	@Test
	void getSendMinutesWatched(){
		assertThat(MinerRunnableFactory.getSendMinutesWatched(miner)).isNotNull();
	}
	
	@Test
	void getWebSocketPing(){
		assertThat(MinerRunnableFactory.getWebSocketPing(miner)).isNotNull();
	}
}