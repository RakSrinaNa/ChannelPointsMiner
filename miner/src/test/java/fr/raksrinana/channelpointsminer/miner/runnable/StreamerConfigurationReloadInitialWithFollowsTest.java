package fr.raksrinana.channelpointsminer.miner.runnable;

import fr.raksrinana.channelpointsminer.miner.api.gql.GQLApi;
import fr.raksrinana.channelpointsminer.miner.api.gql.data.types.User;
import fr.raksrinana.channelpointsminer.miner.factory.StreamerSettingsFactory;
import fr.raksrinana.channelpointsminer.miner.miner.IMiner;
import fr.raksrinana.channelpointsminer.miner.streamer.Streamer;
import fr.raksrinana.channelpointsminer.miner.streamer.StreamerSettings;
import fr.raksrinana.channelpointsminer.miner.tests.ParallelizableTest;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import java.util.List;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ParallelizableTest
@ExtendWith(MockitoExtension.class)
class StreamerConfigurationReloadInitialWithFollowsTest{
	private static final String STREAMER_ID = "streamer-id";
	private static final String STREAMER_USERNAME = "streamer-username";
	
	private StreamerConfigurationReload tested;
	
	@Mock
	private IMiner miner;
	@Mock
	private StreamerSettingsFactory streamerSettingsFactory;
	@Mock
	private GQLApi gqlApi;
	
	@Mock
	private User user;
	@Mock
	private StreamerSettings streamerSettings;
	
	@BeforeEach
	void setUp(){
		tested = new StreamerConfigurationReload(miner, streamerSettingsFactory, true);
		
		lenient().when(streamerSettingsFactory.getStreamerConfigs()).thenReturn(Stream.empty());
		lenient().when(streamerSettingsFactory.createStreamerSettings(STREAMER_USERNAME)).thenReturn(streamerSettings);
		
		lenient().when(miner.getGqlApi()).thenReturn(gqlApi);
		lenient().when(miner.getStreamers()).thenReturn(List.of());
	}
	
	@Test
	void loadFromFollows(){
		when(user.getId()).thenReturn(STREAMER_ID);
		when(user.getLogin()).thenReturn(STREAMER_USERNAME);
		
		when(gqlApi.allChannelFollows()).thenReturn(List.of(user));
		
		assertDoesNotThrow(() -> tested.run());
		
		var expectedStreamer = new Streamer(STREAMER_ID, STREAMER_USERNAME, streamerSettings);
		verify(miner).addStreamer(expectedStreamer);
		verify(gqlApi, never()).reportMenuItem(anyString());
	}
	
	@Test
	void loadFromFollowsEmpty(){
		when(gqlApi.allChannelFollows()).thenReturn(List.of());
		
		assertDoesNotThrow(() -> tested.run());
		
		verify(miner, never()).addStreamer(any());
		verify(gqlApi, never()).reportMenuItem(anyString());
	}
}