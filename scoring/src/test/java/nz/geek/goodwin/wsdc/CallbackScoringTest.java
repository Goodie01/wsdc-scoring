package nz.geek.goodwin.wsdc;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import nz.geek.goodwin.wsdc.domain.CallbackOption;
import nz.geek.goodwin.wsdc.domain.CallbackResult;
import nz.geek.goodwin.wsdc.domain.Judge;
import nz.geek.goodwin.wsdc.domain.Person;
import nz.geek.goodwin.wsdc.domain.Result;

/**
 * @author thomas.goodwin
 */
class CallbackScoringTest {
    /**
     * 3 judge example from https://www.worldsdc.com/wp-content/uploads/2023/06/Call-Back-Scoring_white-paper.pdf
     */
    @Test
    void exampleFromWsdcForm() {
        CallbackScoringRound scoringRound = new CallbackScoringRound();

        scoringRound.setHeadJudge(new Judge("head","Head Judge"));
        scoringRound.setJudges(List.of(
                new Judge("1","Judge 1"),
                new Judge("2","Judge 2"),
                new Judge("3","Judge 3")
        ));

        scoringRound.setDancers(List.of(
                new Person("1","Dancer 1"),
                new Person("2","Dancer 2"),
                new Person("3","Dancer 3"),
                new Person("4","Dancer 4"),
                new Person("5","Dancer 5"),
                new Person("6","Dancer 5"),
                new Person("7","Dancer 5"),
                new Person("8","Dancer 5"),
                new Person("9","Dancer 5")
        ));

        scoringRound.addHeadJudgeScore("1", BigInteger.valueOf(1));
        scoringRound.addScore("1","1", CallbackOption.YES);
        scoringRound.addScore("2","1", CallbackOption.YES);
        scoringRound.addScore("3","1", CallbackOption.YES);

        scoringRound.addHeadJudgeScore("2", BigInteger.valueOf(2));
        scoringRound.addScore("1","2", CallbackOption.YES);
        scoringRound.addScore("2","2", CallbackOption.YES);
        scoringRound.addScore("3","2", CallbackOption.ALT_1);

        scoringRound.addHeadJudgeScore("3", BigInteger.valueOf(3));
        scoringRound.addScore("1","3", CallbackOption.YES);
        scoringRound.addScore("2","3", CallbackOption.ALT_1);
        scoringRound.addScore("3","3", CallbackOption.ALT_1);

        scoringRound.addHeadJudgeScore("4", BigInteger.valueOf(4));
        scoringRound.addScore("1","4", CallbackOption.YES);
        scoringRound.addScore("2","4", CallbackOption.ALT_1);
        scoringRound.addScore("3","4", CallbackOption.NO);

        scoringRound.addHeadJudgeScore("5", BigInteger.valueOf(5));
        scoringRound.addScore("1","5", CallbackOption.ALT_1);
        scoringRound.addScore("2","5", CallbackOption.ALT_1);
        scoringRound.addScore("3","5", CallbackOption.ALT_1);

        scoringRound.addHeadJudgeScore("6", BigInteger.valueOf(6));
        scoringRound.addScore("1","6", CallbackOption.YES);
        scoringRound.addScore("2","6", CallbackOption.NO);
        scoringRound.addScore("3","6", CallbackOption.NO);

        scoringRound.addHeadJudgeScore("7", BigInteger.valueOf(7));
        scoringRound.addScore("1","7", CallbackOption.ALT_1);
        scoringRound.addScore("2","7", CallbackOption.ALT_1);
        scoringRound.addScore("3","7", CallbackOption.NO);

        scoringRound.addHeadJudgeScore("8", BigInteger.valueOf(8));
        scoringRound.addScore("1","8", CallbackOption.ALT_1);
        scoringRound.addScore("2","8", CallbackOption.NO);
        scoringRound.addScore("3","8", CallbackOption.NO);

        scoringRound.addHeadJudgeScore("9", BigInteger.valueOf(9));
        scoringRound.addScore("1","9", CallbackOption.NO);
        scoringRound.addScore("2","9", CallbackOption.NO);
        scoringRound.addScore("3","9", CallbackOption.NO);

        List<Result<CallbackOption, CallbackResult>> process = scoringRound.process();
        Assertions.assertEquals(9, process.size());

        validate(process.get(0), "1", CallbackResult.CALLBACK);
        validate(process.get(1), "2", CallbackResult.CALLBACK);
        validate(process.get(2), "3", CallbackResult.CALLBACK);
        validate(process.get(3), "4", CallbackResult.ALTERNATIVE);
        validate(process.get(4), "5", CallbackResult.NOT_AVAILABLE);
        validate(process.get(5), "6", CallbackResult.NOT_AVAILABLE);
        validate(process.get(6), "7", CallbackResult.NOT_AVAILABLE);
        validate(process.get(7), "8", CallbackResult.NOT_AVAILABLE);
        validate(process.get(8), "9", CallbackResult.NOT_AVAILABLE);
    }

    @Test
    void headJudgeDecider() {
        CallbackScoringRound scoringRound = new CallbackScoringRound();

        scoringRound.setHeadJudge(new Judge("head","Head Judge"));
        scoringRound.setJudges(List.of(
                new Judge("1","Judge 1"),
                new Judge("2","Judge 2"),
                new Judge("3","Judge 3")
        ));

        scoringRound.setDancers(List.of(
                new Person("1","Dancer 1"),
                new Person("2","Dancer 2"),
                new Person("3","Dancer 3"),
                new Person("4","Dancer 4"),
                new Person("5","Dancer 5"),
                new Person("6","Dancer 5"),
                new Person("7","Dancer 5"),
                new Person("8","Dancer 5"),
                new Person("9","Dancer 5")
        ));

        scoringRound.addHeadJudgeScore("1", BigInteger.valueOf(4));
        scoringRound.addScore("1","1", CallbackOption.YES);
        scoringRound.addScore("2","1", CallbackOption.YES);
        scoringRound.addScore("3","1", CallbackOption.YES);

        scoringRound.addHeadJudgeScore("2", BigInteger.valueOf(3));
        scoringRound.addScore("1","2", CallbackOption.YES);
        scoringRound.addScore("2","2", CallbackOption.YES);
        scoringRound.addScore("3","2", CallbackOption.YES);

        scoringRound.addHeadJudgeScore("3", BigInteger.valueOf(2));
        scoringRound.addScore("1","3", CallbackOption.YES);
        scoringRound.addScore("2","3", CallbackOption.YES);
        scoringRound.addScore("3","3", CallbackOption.YES);

        scoringRound.addHeadJudgeScore("4", BigInteger.valueOf(1));
        scoringRound.addScore("1","4", CallbackOption.YES);
        scoringRound.addScore("2","4", CallbackOption.YES);
        scoringRound.addScore("3","4", CallbackOption.YES);

        scoringRound.addHeadJudgeScore("5", BigInteger.valueOf(5));
        scoringRound.addHeadJudgeScore("6", BigInteger.valueOf(6));
        scoringRound.addHeadJudgeScore("7", BigInteger.valueOf(7));
        scoringRound.addHeadJudgeScore("8", BigInteger.valueOf(8));
        scoringRound.addHeadJudgeScore("9", BigInteger.valueOf(9));

        List<Result<CallbackOption, CallbackResult>> process = scoringRound.process();
        Assertions.assertEquals(9, process.size());

        validate(process.get(0), "4", CallbackResult.CALLBACK);
        validate(process.get(1), "3", CallbackResult.CALLBACK);
        validate(process.get(2), "2", CallbackResult.CALLBACK);
        validate(process.get(3), "1", CallbackResult.ALTERNATIVE);
    }

    private <T,R> void validate(Result<T, R> result, String number, R callbackResult) {
        Assertions.assertEquals(number, result.person().id());
        Assertions.assertEquals(callbackResult, result.result());
    }
}