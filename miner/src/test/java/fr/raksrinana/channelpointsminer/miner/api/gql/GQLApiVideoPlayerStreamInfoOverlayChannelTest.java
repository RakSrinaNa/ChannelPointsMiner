package fr.raksrinana.channelpointsminer.miner.api.gql;

import fr.raksrinana.channelpointsminer.miner.api.gql.data.GQLResponse;
import fr.raksrinana.channelpointsminer.miner.api.gql.data.types.BroadcastSettings;
import fr.raksrinana.channelpointsminer.miner.api.gql.data.types.Game;
import fr.raksrinana.channelpointsminer.miner.api.gql.data.types.Stream;
import fr.raksrinana.channelpointsminer.miner.api.gql.data.types.Tag;
import fr.raksrinana.channelpointsminer.miner.api.gql.data.types.User;
import fr.raksrinana.channelpointsminer.miner.api.gql.data.videoplayerstreaminfooverlaychannel.VideoPlayerStreamInfoOverlayChannelData;
import fr.raksrinana.channelpointsminer.miner.api.passport.TwitchLogin;
import fr.raksrinana.channelpointsminer.miner.tests.TestUtils;
import fr.raksrinana.channelpointsminer.miner.tests.UnirestMockExtension;
import kong.unirest.core.MockClient;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import static kong.unirest.core.HttpMethod.POST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ExtendWith(UnirestMockExtension.class)
class GQLApiVideoPlayerStreamInfoOverlayChannelTest{
	private static final String ACCESS_TOKEN = "access-token";
	private static final String USERNAME = "username";
	private static final String VALID_QUERY = "{\"extensions\":{\"persistedQuery\":{\"sha256Hash\":\"a5f2e34d626a9f4f5c0204f910bab2194948a9502089be558bb6e779a9e1b3d2\",\"version\":1}},\"operationName\":\"VideoPlayerStreamInfoOverlayChannel\",\"variables\":{\"channel\":\"%s\"}}";
	
	@InjectMocks
	private GQLApi tested;
	
	@Mock
	private TwitchLogin twitchLogin;
	
	@BeforeEach
	void setUp(){
		when(twitchLogin.getAccessToken()).thenReturn(ACCESS_TOKEN);
	}
	
	@Test
	void nominalOnline(MockClient unirest) throws MalformedURLException{
		var expected = GQLResponse.<VideoPlayerStreamInfoOverlayChannelData> builder()
				.extensions(Map.of(
						"durationMilliseconds", 58,
						"operationName", "VideoPlayerStreamInfoOverlayChannel",
						"requestID", "request-id"
				))
				.data(VideoPlayerStreamInfoOverlayChannelData.builder()
						.user(User.builder()
								.id("123456789")
								.profileUrl(new URL("https://google.com/streamer"))
								.displayName("streamername")
								.login("streamer")
								.profileImageUrl(new URL("https://google.com/streamer/profile"))
								.broadcastSettings(BroadcastSettings.builder()
										.id("147258369")
										.title("title")
										.game(Game.builder()
												.id("123")
												.displayName("gamename")
												.name("game")
												.build())
										.build())
								.stream(Stream.builder()
										.id("369258147")
										.viewersCount(2586)
										.tags(List.of(
												Tag.builder()
														.id("tag-id")
														.localizedName("name")
														.build()
										))
										.build())
								.build())
						.build())
				.build();
		
		unirest.expect(POST, "https://gql.twitch.tv/gql")
				.header("Authorization", "OAuth " + ACCESS_TOKEN)
				.body(VALID_QUERY.formatted(USERNAME))
				.thenReturn(TestUtils.getAllResourceContent("api/gql/videoPlayerStreamInfoOverlayChannel_online.json"))
				.withStatus(200);
		
		assertThat(tested.videoPlayerStreamInfoOverlayChannel(USERNAME)).isPresent().get().isEqualTo(expected);
		
		unirest.verifyAll();
	}
	
	@Test
	void nominalOffline(MockClient unirest) throws MalformedURLException{
		var expected = GQLResponse.<VideoPlayerStreamInfoOverlayChannelData> builder()
				.extensions(Map.of(
						"durationMilliseconds", 58,
						"operationName", "VideoPlayerStreamInfoOverlayChannel",
						"requestID", "request-id"
				))
				.data(VideoPlayerStreamInfoOverlayChannelData.builder()
						.user(User.builder()
								.id("123456789")
								.profileUrl(new URL("https://google.com/streamer"))
								.displayName("streamername")
								.login("streamer")
								.profileImageUrl(new URL("https://google.com/streamer/profile"))
								.broadcastSettings(BroadcastSettings.builder()
										.id("147258369")
										.title("title")
										.game(Game.builder()
												.id("123")
												.displayName("gamename")
												.name("game")
												.build())
										.build())
								.build())
						.build())
				.build();
		
		unirest.expect(POST, "https://gql.twitch.tv/gql")
				.header("Authorization", "OAuth " + ACCESS_TOKEN)
				.body(VALID_QUERY.formatted(USERNAME))
				.thenReturn(TestUtils.getAllResourceContent("api/gql/videoPlayerStreamInfoOverlayChannel_offline.json"))
				.withStatus(200);
		
		assertThat(tested.videoPlayerStreamInfoOverlayChannel(USERNAME)).isPresent().get().isEqualTo(expected);
		
		unirest.verifyAll();
	}
	
	@Test
	void invalidCredentials(MockClient unirest){
		unirest.expect(POST, "https://gql.twitch.tv/gql")
				.header("Authorization", "OAuth " + ACCESS_TOKEN)
				.body(VALID_QUERY.formatted(USERNAME))
				.thenReturn(TestUtils.getAllResourceContent("api/gql/invalidAuth.json"))
				.withStatus(401);
		
		assertThrows(RuntimeException.class, () -> tested.videoPlayerStreamInfoOverlayChannel(USERNAME));
		
		unirest.verifyAll();
	}
	
	@Test
	void invalidRequest(MockClient unirest){
		unirest.expect(POST, "https://gql.twitch.tv/gql")
				.header("Authorization", "OAuth " + ACCESS_TOKEN)
				.body(VALID_QUERY.formatted(USERNAME))
				.thenReturn(TestUtils.getAllResourceContent("api/gql/invalidRequest.json"))
				.withStatus(200);
		
		assertThat(tested.videoPlayerStreamInfoOverlayChannel(USERNAME)).isEmpty();
		
		unirest.verifyAll();
	}
	
	@Test
	void invalidResponse(MockClient unirest){
		unirest.expect(POST, "https://gql.twitch.tv/gql")
				.header("Authorization", "OAuth " + ACCESS_TOKEN)
				.body(VALID_QUERY.formatted(USERNAME))
				.thenReturn()
				.withStatus(500);
		
		assertThat(tested.videoPlayerStreamInfoOverlayChannel(USERNAME)).isEmpty();
		
		unirest.verifyAll();
	}
}