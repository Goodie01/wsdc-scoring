package nz.geek.goodwin.wsdc;

import nz.geek.goodwin.wsdc.domain.InterimResult;
import nz.geek.goodwin.wsdc.domain.Judge;
import nz.geek.goodwin.wsdc.domain.Person;
import nz.geek.goodwin.wsdc.domain.RelativePlacingInterimResult;
import nz.geek.goodwin.wsdc.domain.Result;
import nz.geek.goodwin.wsdc.domain.Score;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author thomas.goodwin
 */
public class RelativePlacementRound {
    private boolean hideHeadJudgeScores = true;
    private final int numberOfYesScores = 2;
    private final int numberOfAlt1Scores = 1;
    private final int numberOfAlt2Scores = 1;
    private final int numberOfAlt3Scores = 1;

    private final int numberOfCallbacks = 3;
    private final int numberOfAlternatives = 1;

    private Judge headJudge = null;
    private List<Judge> judges = new ArrayList<>();
    private List<Person> dancers = new ArrayList<>();
    private final List<Score<BigInteger>> headJudgeScores = new ArrayList<>();
    private final List<Score<BigInteger>> scores = new ArrayList<>();

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

    public void addScore(String givenBy, String givenTo, BigInteger score) {
        findJudge(givenBy);
        scores.add(new Score<>(givenBy, givenTo, true, score));
    }

    private Judge findJudge(String givenBy) {
        return judges.stream().filter(judge -> StringUtils.equals(judge.id(), givenBy)).findFirst().orElseThrow();
    }

    public List<Result<BigInteger, BigInteger>> process() {
        //Validate
        //One head judge, and only one
        if (headJudge == null) {
            throw new IllegalStateException("More or less than 1 head judge");
        }

        //that the headjudge has
        // * given every person a score,
        // * only one score,
        // * each score is unique,
        // * within the range of number of dancers we have
        List<String> list2 = headJudgeScores.stream().map(Score::givenTo).toList();
        if (list2.size() != dancers.size() || Set.copyOf(list2).size() != dancers.size()) {
            throw new IllegalStateException("Issue with head judge scores, not enough scores");
        }

        Set<BigInteger> headJudgeRawScores = headJudgeScores.stream().map(Score::score).collect(Collectors.toSet());
        int max = Collections.max(headJudgeRawScores).intValue();
        int min = Collections.min(headJudgeRawScores).intValue();
        int size = headJudgeRawScores.size();
        if (size != dancers.size() || max != dancers.size() || min != 1) {
            throw new IllegalStateException("Issue with head judge scores, scores not unique and/or out of bounds");
        }

        //That each judge has
        // * given out the required yes's
        // * has given out the required alternates

        List<InterimResult<BigInteger>> interimResults = dancers.stream().map(person -> {
                    List<Score<BigInteger>> dancerScores = scores.stream()
                            .filter(score -> StringUtils.equals(score.givenTo(), person.id()))
                            .toList();

                    BigInteger headJudgeScore = headJudgeScores.stream()
                            .filter(score -> StringUtils.equals(score.givenTo(), person.id())).map(Score::score)
                            .findFirst().orElseThrow();

                    return new InterimResult<>(person, dancerScores, headJudgeScore, BigDecimal.ZERO);
                })
                .collect(Collectors.toCollection(ArrayList::new));

        //TODO scoring

        int judgesMajority = getJudgeMajority();
        BigInteger currentScore = BigInteger.ONE;
        BigInteger currentRank = BigInteger.ONE;
        List<Result<BigInteger, BigInteger>> results = new ArrayList<>();


        while (results.size() != dancers.size()) {
            BigInteger currentCurrentScore = currentScore;
            List<RelativePlacingInterimResult> collect = interimResults.stream()
                    .map(interimResult -> {
                        Person person = interimResult.dancer();
                        long scoreSum = interimResult.scores().stream()
                                .filter(bigIntegerScore -> bigIntegerScore.score().compareTo(currentCurrentScore) <= 0)
                                .mapToLong(value -> value.score().longValue())
                                .sum();
                        long scoreCount = interimResult.scores().stream()
                                .filter(bigIntegerScore -> bigIntegerScore.score().compareTo(currentCurrentScore) <= 0)
                                .count();
                        return new RelativePlacingInterimResult(person, scoreCount, scoreSum, interimResult.scores());
                    })
                    .filter(scores -> scores.countScore() >= judgesMajority)
                    .toList();

            //if this has 1 entry, then we have a majority. Yay.
            if (!collect.isEmpty()) {
                Long topScoreThisRound = collect.stream()
                        .map(RelativePlacingInterimResult::countScore)
                        .max(Comparator.comparing(Function.identity()))
                        .orElse((long) judges.size());

                List<RelativePlacingInterimResult> topScorersThisRound = collect.stream()
                        .filter(personLongEntry -> personLongEntry.countScore().equals(topScoreThisRound))
                        .collect(Collectors.toCollection(ArrayList::new));

                RelativePlacingInterimResult placing;
                if (topScorersThisRound.size() == 1) {
                    placing = topScorersThisRound.getFirst();
                    currentScore = currentScore.add(BigInteger.ONE);
                } else {
                    //TODO What happens if we have a tie break STILL
                    topScorersThisRound.sort(Comparator.comparing(RelativePlacingInterimResult::sumScore));
                    placing = topScorersThisRound.getFirst();
                }

                interimResults.removeIf(interimResult -> interimResult.dancer().id().equals(placing.person().id()));
                results.add(new Result<>(placing.person(), placing.scores(), currentRank));

                currentRank = currentRank.add(BigInteger.ONE);
            } else {
                currentScore = currentScore.add(BigInteger.ONE);
            }

        }

        return results;


        //old

//        List<Result<BigInteger, BigInteger>> list = new ArrayList<>();
//        for (InterimResult<BigInteger> interimResult : interimResults) {
//            BigInteger result = BigInteger.ONE;
//
//            Result<BigInteger, BigInteger> objectResult = new Result<>(interimResult.person(), interimResult.scores(), result);
//            list.add(objectResult);
//        }
//        return list;
    }

    private int getJudgeMajority() {
        if (judges.size() % 2 == 0) {
            return judges.size() / 2 + 1;
        } else {
            return (int) Math.ceil(judges.size() / 2.0f);
        }
    }
}
