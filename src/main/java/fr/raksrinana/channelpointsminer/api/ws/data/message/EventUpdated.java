package fr.raksrinana.channelpointsminer.api.ws.data.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import fr.raksrinana.channelpointsminer.api.ws.data.message.eventupdated.EventUpdatedData;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@JsonTypeName("event-updated")
@Getter
@EqualsAndHashCode(callSuper = true)
@ToString
public class EventUpdated extends IMessage{
	@JsonProperty("data")
	private EventUpdatedData data;
}
