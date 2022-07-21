package fr.raksrinana.channelpointsminer.miner.factory;

import fr.raksrinana.channelpointsminer.miner.api.chat.ws.TwitchChatWebSocketClient;
import fr.raksrinana.channelpointsminer.miner.api.passport.TwitchLogin;
import fr.raksrinana.channelpointsminer.miner.api.ws.TwitchPubSubWebSocketClient;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import java.net.URI;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class TwitchWebSocketClientFactory{
	private static final URI PUB_SUB_URI = URI.create("wss://pubsub-edge.twitch.tv/v1");
	private static final URI IRC_URI = URI.create("wss://irc-ws.chat.twitch.tv/");
	
	@NotNull
	public static TwitchPubSubWebSocketClient createPubSubClient(){
		return new TwitchPubSubWebSocketClient(PUB_SUB_URI);
	}
	
	@NotNull
	public static TwitchChatWebSocketClient createChatClient(@NotNull TwitchLogin twitchLogin){
		return new TwitchChatWebSocketClient(IRC_URI, twitchLogin);
	}
}
