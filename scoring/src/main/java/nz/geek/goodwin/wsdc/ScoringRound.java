package nz.geek.goodwin.wsdc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
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
public class ScoringRound<T,R> {
    private final Strategy<T,R> strategy;
    private boolean hideHeadJudgeScores = true;

    private Judge headJudge = null;
    private List<Judge> judges = new ArrayList<>();
    private List<Person> dancers = new ArrayList<>();
    private final List<Score<BigDecimal>> headJudgeScores = new ArrayList<>();
    private final List<Score<T>> scores = new ArrayList<>();

    public ScoringRound(Strategy<T,R> strategy) {
        this.strategy = strategy;
    }

    public void setHeadJudge(Judge headJudge) {
        this.headJudge = headJudge;
    }

    public void setJudges(List<Judge> judges) {
        this.judges = judges;
    }

    public void setDancers(List<Person> dancers) {
        this.dancers = dancers;
    }

    public void addHeadJudgeScore(String givenTo, BigDecimal score) {
        headJudgeScores.add(new Score<>(headJudge.id(), givenTo, !hideHeadJudgeScores, score));
    }
    public void addScore(String givenBy, String givenTo, T score) {
        findJudge(givenBy);
        scores.add(new Score<>(givenBy, givenTo, true, score));
    }

    private Judge findJudge(String givenBy) {
        return judges.stream().filter(judge -> StringUtils.equals(judge.id(), givenBy)).findFirst().orElseThrow();
    }

    public List<Result<T,R>> process() {
        //Validate
        //One head judge, and only one
        if(headJudge == null) {
            throw new IllegalStateException("More or less than 1 head judge");
        }

        //that the headjudge has
        // * given every dancer a score,
        // * only one score,
        // * each score is unique,
        // * within the range of number of dancers we have
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

        List<InterimResult<T>> interimResults = strategy.produceInterimResults(dancers, scores, headJudgeScores, headJudge);
        strategy.rankInterimResults(interimResults);
        return strategy.produceResults(interimResults);
    }
}
