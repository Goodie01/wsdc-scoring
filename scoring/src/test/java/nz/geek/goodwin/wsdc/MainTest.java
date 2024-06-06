package nz.geek.goodwin.wsdc;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import nz.geek.goodwin.wsdc.domain.CallbackResult;
import nz.geek.goodwin.wsdc.domain.Judge;
import nz.geek.goodwin.wsdc.domain.Person;
import nz.geek.goodwin.wsdc.domain.Result;

/**
 * @author thomas.goodwin
 */
class MainTest {
    @Test
    void test() {
        ScoringRound scoringRound = new ScoringRound();

        scoringRound.setJudges(List.of(
                new Judge("head","Head Judge", true),
                new Judge("1","Judge 1", false),
                new Judge("2","Judge 2", false),
                new Judge("3","Judge 3", false),
                new Judge("4","Judge 4", false),
                new Judge("5","Judge 5", false)
        ));

        scoringRound.setDancers(List.of(
                new Person("1","Dancer 1"),
                new Person("2","Dancer 2"),
                new Person("3","Dancer 3"),
                new Person("4","Dancer 4"),
                new Person("5","Dancer 5")
        ));

        scoringRound.addScore("head","1", BigDecimal.valueOf(1));
        scoringRound.addScore("1","1", BigDecimal.valueOf(10));
        scoringRound.addScore("2","1", BigDecimal.valueOf(10));
        scoringRound.addScore("3","1", BigDecimal.valueOf(10));
        scoringRound.addScore("4","1", BigDecimal.valueOf(10));
        scoringRound.addScore("5","1", BigDecimal.valueOf(10));

        scoringRound.addScore("head","2", BigDecimal.valueOf(2));
        scoringRound.addScore("1","2", BigDecimal.valueOf(10));
        scoringRound.addScore("2","2", BigDecimal.valueOf(10));
        scoringRound.addScore("3","2", BigDecimal.valueOf(10));
        scoringRound.addScore("4","2", BigDecimal.valueOf(10));
        scoringRound.addScore("5","2", BigDecimal.valueOf(10));

        scoringRound.addScore("head","3", BigDecimal.valueOf(3));
        scoringRound.addScore("1","3", BigDecimal.valueOf(10));
        scoringRound.addScore("2","3", BigDecimal.valueOf(10));
        scoringRound.addScore("3","3", BigDecimal.valueOf(10));
        scoringRound.addScore("4","3", BigDecimal.valueOf(10));
        scoringRound.addScore("5","3", BigDecimal.valueOf(10));

        scoringRound.addScore("head","4", BigDecimal.valueOf(4));
        scoringRound.addScore("1","4", BigDecimal.valueOf(10));
        scoringRound.addScore("2","4", BigDecimal.valueOf(10));
        scoringRound.addScore("3","4", BigDecimal.valueOf(10));
        scoringRound.addScore("4","4", BigDecimal.valueOf(10));
        scoringRound.addScore("5","4", BigDecimal.valueOf(10));

        scoringRound.addScore("head","5", BigDecimal.valueOf(5));
        scoringRound.addScore("1","5", BigDecimal.valueOf(10));
        scoringRound.addScore("2","5", BigDecimal.valueOf(10));
        scoringRound.addScore("3","5", BigDecimal.valueOf(10));
        scoringRound.addScore("4","5", BigDecimal.valueOf(10));
        scoringRound.addScore("5","5", BigDecimal.valueOf(10));

        List<Result> process = scoringRound.process();
        Assertions.assertEquals(5, process.size());

        validate(process.get(0), "1", CallbackResult.CALLBACK);
        validate(process.get(1), "2", CallbackResult.CALLBACK);
        validate(process.get(2), "3", CallbackResult.CALLBACK);
        validate(process.get(3), "4", CallbackResult.ALTERNATIVE);
        validate(process.get(4), "5", CallbackResult.NOT_AVAILABLE);
    }

    private void validate(Result result, String number, CallbackResult callbackResult) {
        Assertions.assertEquals(number, result.person().id());
        Assertions.assertEquals(callbackResult, result.result());
    }
}