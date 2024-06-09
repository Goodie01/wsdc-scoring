package nz.geek.goodwin.wsdc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

import org.apache.commons.lang3.StringUtils;

import nz.geek.goodwin.wsdc.domain.CallbackOption;
import nz.geek.goodwin.wsdc.domain.CallbackResult;
import nz.geek.goodwin.wsdc.domain.InterimResult;
import nz.geek.goodwin.wsdc.domain.Judge;
import nz.geek.goodwin.wsdc.domain.Person;
import nz.geek.goodwin.wsdc.domain.Result;
import nz.geek.goodwin.wsdc.domain.Score;

/**
 * @author thomas.goodwin
 */
public class CallbackStrategy implements Strategy<CallbackOption, CallbackResult> {
    private final int numberOfYesScores = 2;
    private final int numberOfAlt1Scores = 1;
    private final int numberOfAlt2Scores = 1;
    private final int numberOfAlt3Scores = 1;

    private final int numberOfCallbacks = 3;
    private final int numberOfAlternatives = 1;

    public int getNumberOfYesScores() {
        return numberOfYesScores;
    }

    public int getNumberOfAlt1Scores() {
        return numberOfAlt1Scores;
    }

    public int getNumberOfAlt2Scores() {
        return numberOfAlt2Scores;
    }

    public int getNumberOfAlt3Scores() {
        return numberOfAlt3Scores;
    }

    public int getNumberOfCallbacks() {
        return numberOfCallbacks;
    }

    public int getNumberOfAlternatives() {
        return numberOfAlternatives;
    }

    @Override
    public void rankInterimResults(List<InterimResult<CallbackOption>> interimResults) {
        interimResults.sort(Comparator.comparing(
                (Function<InterimResult<CallbackOption>, BigDecimal>) InterimResult::totalScore)
                .reversed()
                .thenComparing(InterimResult::headJudgeScore)
        );
    }

    @Override
    public List<InterimResult<CallbackOption>> produceInterimResults(List<Person> dancers, List<Score<CallbackOption>> scores,
            List<Score<BigDecimal>> headJudgeScores, Judge headJudge) {
        List<InterimResult<CallbackOption>> list1 = dancers.stream().map(person -> {
            List<Score<CallbackOption>> dancerScores = scores.stream()
                    .filter(score -> StringUtils.equals(score.givenTo(), person.id()))
                    .toList();

            BigDecimal scoreTotal = dancerScores.stream()
                    .map(Score::score)
                    .map(CallbackOption::scoringValue)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal headJudgeScore = headJudgeScores.stream()
                    .filter(score -> StringUtils.equals(score.givenTo(), person.id())).map(Score::score)
                    .findFirst().orElseThrow();

            return new InterimResult<>(person, dancerScores, headJudgeScore, scoreTotal);
        }).toList();

        return new ArrayList<>(list1);
    }

    @Override
    public List<Result<CallbackOption, CallbackResult>> produceResults(List<InterimResult<CallbackOption>> interimResults) {
        List<Result<CallbackOption, CallbackResult>> list = new ArrayList<>();
        int currentCallbacks = 0;
        int currentAlternatives = 0;

        for (InterimResult<CallbackOption> interimResult : interimResults) {
            CallbackResult result = CallbackResult.NOT_AVAILABLE;
            if(currentCallbacks < numberOfCallbacks) {
                result = CallbackResult.CALLBACK;
                currentCallbacks++;
            } else if (currentAlternatives < numberOfAlternatives) {
                result = CallbackResult.ALTERNATIVE;
                currentAlternatives++;
            }

            Result<CallbackOption, CallbackResult> objectResult = new Result<>(interimResult.dancer(), interimResult.scores(), result);
            list.add(objectResult);
        }

        return list;
    }
}
