package nz.geek.goodwin.wsdc;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import nz.geek.goodwin.wsdc.domain.CallbackResult;
import nz.geek.goodwin.wsdc.domain.InterimResult;
import nz.geek.goodwin.wsdc.domain.Judge;
import nz.geek.goodwin.wsdc.domain.Person;
import nz.geek.goodwin.wsdc.domain.Result;
import nz.geek.goodwin.wsdc.domain.Score;

/**
 * @author thomas.goodwin
 */
public class ScoringRound {
    private int numberOfYesScores = 2;
    private int numberOfAltScores = 2;
    private int nubberOfCallbacks = 3;
    private int numberOfAlternatives = 1;
    private boolean hideHeadJudgeScores = true;

    private List<Judge> judges = new ArrayList<>();
    private List<Person> dancers = new ArrayList<>();
    private List<Score> scores = new ArrayList<>();

    public void setJudges(List<Judge> judges) {
        this.judges = judges;
    }

    public void setDancers(List<Person> dancers) {
        this.dancers = dancers;
    }

    public void setScores(List<Score> scores) {
        this.scores = scores;
    }

    public void addScore(String givenBy, String givenTo, BigDecimal score) {
        Judge judge = findJudge(givenBy);

        boolean displayableScore; //I'm almost certain there's a fancy way to simplify this. But I'm hoping this is readable.
        if(judge.headJudge()) {
            displayableScore = !hideHeadJudgeScores;
        } else {
            displayableScore = true;
        }

        scores.add(new Score(givenBy, givenTo, displayableScore, score));
    }

    private Judge findJudge(String givenBy) {
        return judges.stream().filter(judge -> StringUtils.equals(judge.id(), givenBy)).findFirst().orElseThrow();
    }

    public List<Result> process() {
        //Validate
        //One head judge, and only one
        List<Judge> judges = this.judges.stream().filter(Judge::headJudge).toList();
        if (judges.size() != 1) {
            throw new IllegalStateException("More or less than 1 head judge");
        }
        Judge headJudge = judges.getFirst();

        //that the headjudge has
        // * given every dancer a score,
        // * only one score,
        // * each score is unique,
        // * within the range of number of dancers we have
        List<Score> headJudgeScores = scores.stream().filter(score -> StringUtils.equals(score.givenBy(), headJudge.id())).toList();

        List<String> list2 = headJudgeScores.stream().map(Score::givenTo).toList();
        if(list2.size() != dancers.size() || Set.copyOf(list2).size() != dancers.size()) {
            throw new IllegalStateException("Issue with head judge scores, not enough scores");
        }

        Set<BigDecimal> headJudgeRawScores = headJudgeScores.stream().map(Score::score).collect(Collectors.toSet());
        int max = Collections.max(headJudgeRawScores).intValue();
        int min = Collections.min(headJudgeRawScores).intValue();
        int size = headJudgeRawScores.size();
        if(size != dancers.size() || max != dancers.size() || min != 1) {
            throw new IllegalStateException("Issue with head judge scores, scores not unique and/or out of bounds");
        }

        //That each judge has
        // * given out the required yes's
        // * has given out the required alternates

        List<InterimResult> list1 = dancers.stream().map(person -> {
            List<Score> dancerScores = scores.stream().filter(score -> StringUtils.equals(score.givenTo(), person.id())).toList();

            BigDecimal headJudgeScore = dancerScores.stream().filter(score -> StringUtils.equals(score.givenBy(), headJudge.id())).findFirst()
                    .orElseThrow().score();

            BigDecimal scoreTotal = dancerScores.stream().filter(score -> !StringUtils.equals(score.givenBy(), headJudge.id())).map(Score::score)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            return new InterimResult(person, dancerScores, headJudgeScore, scoreTotal);
        }).toList();
        ArrayList<InterimResult> interimResults = new ArrayList<>(list1);

        interimResults.sort(Comparator.comparing(InterimResult::totalScore).thenComparing(InterimResult::headJudgeScore));

        List<Result> list = new ArrayList<>();
        int currentCallbacks = 0;
        int currentAlternatives = 0;

        for (InterimResult interimResult : interimResults) {
            CallbackResult result = CallbackResult.NOT_AVAILABLE;
            if(currentCallbacks < nubberOfCallbacks) {
                result = CallbackResult.CALLBACK;
                currentCallbacks++;
            } else if (currentAlternatives < numberOfAlternatives) {
                result = CallbackResult.ALTERNATIVE;
                currentAlternatives++;
            }

            list.add(new Result(interimResult.dancer(), interimResult.scores(), result));
        }

        return list;
    }
}
