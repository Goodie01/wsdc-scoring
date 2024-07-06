package nz.geek.goodwin.wsdc;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

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
public class CallbackScoringRound {
    private final int numberOfYesScores = 2;
    private final int numberOfAlt1Scores = 1;
    private final int numberOfAlt2Scores = 1;
    private final int numberOfAlt3Scores = 1;

    private final int numberOfCallbacks = 3;
    private final int numberOfAlternatives = 1;
    private boolean hideHeadJudgeScores = true;

    private Judge headJudge = null;
    private List<Judge> judges = new ArrayList<>();
    private List<Person> dancers = new ArrayList<>();
    private final List<Score<BigInteger>> headJudgeScores = new ArrayList<>();
    private final List<Score<CallbackOption>> scores = new ArrayList<>();

    public void setHeadJudge(Judge headJudge) {
        this.headJudge = headJudge;
    }

    public void setJudges(List<Judge> judges) {
        this.judges = judges;
    }

    public void setDancers(List<Person> dancers) {
        this.dancers = dancers;
    }

    public void addHeadJudgeScore(String givenTo, BigInteger score) {
        headJudgeScores.add(new Score<>(headJudge.id(), givenTo, !hideHeadJudgeScores, score));
    }
    public void addScore(String givenBy, String givenTo, CallbackOption score) {
        findJudge(givenBy);
        scores.add(new Score<>(givenBy, givenTo, true, score));
    }

    private Judge findJudge(String givenBy) {
        return judges.stream().filter(judge -> StringUtils.equals(judge.id(), givenBy)).findFirst().orElseThrow();
    }

    public List<Result<CallbackOption, CallbackResult>> process() {
        //Validate
        //One head judge, and only one
        if(headJudge == null) {
            throw new IllegalStateException("More or less than 1 head judge");
        }

        //that the headjudge has
        // * given every person a score,
        // * only one score,
        // * each score is unique,
        // * within the range of number of dancers we have
        List<String> list2 = headJudgeScores.stream().map(Score::givenTo).toList();
        if(list2.size() != dancers.size() || Set.copyOf(list2).size() != dancers.size()) {
            throw new IllegalStateException("Issue with head judge scores, not enough scores");
        }

        Set<BigInteger> headJudgeRawScores = headJudgeScores.stream().map(Score::score).collect(Collectors.toSet());
        int max = Collections.max(headJudgeRawScores).intValue();
        int min = Collections.min(headJudgeRawScores).intValue();
        int size = headJudgeRawScores.size();
        if(size != dancers.size() || max != dancers.size() || min != 1) {
            throw new IllegalStateException("Issue with head judge scores, scores not unique and/or out of bounds");
        }

        //That each judge has
        // * given out the required yes's
        // * has given out the required alternates

        List<InterimResult<CallbackOption>> list1 = dancers.stream().map(person -> {
            List<Score<CallbackOption>> dancerScores = scores.stream()
                    .filter(score -> StringUtils.equals(score.givenTo(), person.id()))
                    .toList();

            BigDecimal scoreTotal = dancerScores.stream()
                    .map(Score::score)
                    .map(CallbackOption::scoringValue)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigInteger headJudgeScore = headJudgeScores.stream()
                    .filter(score -> StringUtils.equals(score.givenTo(), person.id())).map(Score::score)
                    .findFirst().orElseThrow();

            return new InterimResult<>(person, dancerScores, headJudgeScore, scoreTotal);
        }).toList();

        List<InterimResult<CallbackOption>> interimResults = new ArrayList(list1);
        interimResults.sort(Comparator.comparing(
                        (Function<InterimResult<CallbackOption>, BigDecimal>) InterimResult::totalScore)
                .reversed()
                .thenComparing(InterimResult::headJudgeScore)
        );

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
