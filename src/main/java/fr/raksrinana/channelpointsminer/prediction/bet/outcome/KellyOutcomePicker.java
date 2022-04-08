package fr.raksrinana.channelpointsminer.prediction.bet.outcome;

import com.fasterxml.jackson.annotation.JsonTypeName;
import fr.raksrinana.channelpointsminer.api.ws.data.message.subtype.Outcome;
import fr.raksrinana.channelpointsminer.handler.data.BettingPrediction;
import fr.raksrinana.channelpointsminer.prediction.bet.BetPlacementException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;

@JsonTypeName("kelly")
@Getter
@EqualsAndHashCode
@ToString
@Builder
@AllArgsConstructor
@Log4j2
public class KellyOutcomePicker implements IOutcomePicker{
    @Override
    @NotNull
    public Outcome chooseOutcome(@NotNull BettingPrediction bettingPrediction) throws BetPlacementException{
        var outcomes = bettingPrediction.getEvent().getOutcomes();
        if(outcomes.size() != 2){
            throw new BetPlacementException("Two outcomes are needed, there was " + outcomes.size());
        }
        
        var outcome1 = outcomes.get(0);
        var outcome2 = outcomes.get(1);
    
        if(outcome1.getTotalPoints() == 0){
            throw new BetPlacementException("First outcome needs to have some point bet on it");
        }
        
        return getKellyValue(outcome1, outcome2) >= 0 ? outcome1 : outcome2;
    }
    
    private float getKellyValue(@NotNull Outcome outcome1, @NotNull Outcome outcome2){
        var winProbability = ((float) outcome1.getTotalUsers()) / (outcome1.getTotalUsers() + outcome2.getTotalUsers());
        var lossProbability = 1 - winProbability;
        var proportionGain = (outcome1.getTotalPoints() + outcome2.getTotalPoints()) / ((float) outcome1.getTotalPoints()) - 1;
        
        return winProbability - lossProbability / proportionGain;
    }
}
