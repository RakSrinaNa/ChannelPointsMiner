package fr.raksrinana.channelpointsminer.miner.priority;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import fr.raksrinana.channelpointsminer.miner.miner.IMiner;
import fr.raksrinana.channelpointsminer.miner.streamer.Streamer;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeInfo(use = NAME, property = "type")
@JsonSubTypes(value = {
		@Type(value = ConstantPriority.class, name = "constant"),
		@Type(value = SubscribedPriority.class, name = "subscribed"),
		@Type(value = PointsAbovePriority.class, name = "pointsAbove"),
		@Type(value = PointsBelowPriority.class, name = "pointsBelow"),
		@Type(value = WatchStreakPriority.class, name = "watchStreak"),
		@Type(value = DropsPriority.class, name = "drops"),
})
@ToString
@EqualsAndHashCode
@SuperBuilder
public abstract class IStreamerPriority{
	@JsonProperty("score")
	private int score;
	
	public abstract int getScore(@NotNull IMiner miner, @NotNull Streamer streamer);
}
