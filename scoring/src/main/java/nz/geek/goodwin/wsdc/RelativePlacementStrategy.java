package nz.geek.goodwin.wsdc;

import java.math.BigDecimal;
import java.math.BigInteger;
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
public class RelativePlacementStrategy implements Strategy<BigInteger, CallbackResult> {
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
    public void rankInterimResults(List<InterimResult<BigInteger>> interimResults) {
        interimResults.sort(Comparator.comparing(
                (Function<InterimResult<BigInteger>, BigDecimal>) InterimResult::totalScore)
                .thenComparing(InterimResult::headJudgeScore)
        );
    }

    @Override
    public List<InterimResult<BigInteger>> produceInterimResults(List<Person> dancers, List<Score<BigInteger>> scores,
            List<Score<BigDecimal>> headJudgeScores, Judge headJudge) {
        List<InterimResult<BigInteger>> list1 = dancers.stream().map(person -> {
            List<Score<BigInteger>> dancerScores = scores.stream()
                    .filter(score -> StringUtils.equals(score.givenTo(), person.id()))
                    .toList();

            BigInteger scoreTotal = dancerScores.stream()
                    .map(Score::score)
                    .reduce(BigInteger.ZERO, BigInteger::add);

            BigDecimal headJudgeScore = headJudgeScores.stream()
                    .filter(score -> StringUtils.equals(score.givenTo(), person.id())).map(Score::score)
                    .findFirst().orElseThrow();

            return new InterimResult<>(person, dancerScores, headJudgeScore, BigDecimal.valueOf(scoreTotal.intValue()));
        }).toList();

        return new ArrayList<>(list1);
    }

    @Override
    public List<Result<BigInteger, CallbackResult>> produceResults(List<InterimResult<BigInteger>> interimResults) {
        List<Result<BigInteger, CallbackResult>> list = new ArrayList<>();
        int currentCallbacks = 0;
        int currentAlternatives = 0;

        for (InterimResult<BigInteger> interimResult : interimResults) {
            CallbackResult result = CallbackResult.NOT_AVAILABLE;
            if(currentCallbacks < numberOfCallbacks) {
                result = CallbackResult.CALLBACK;
                currentCallbacks++;
            } else if (currentAlternatives < numberOfAlternatives) {
                result = CallbackResult.ALTERNATIVE;
                currentAlternatives++;
            }

            Result<BigInteger, CallbackResult> objectResult = new Result<>(interimResult.dancer(), interimResult.scores(), result);
            list.add(objectResult);
        }

        return list;
    }
}
