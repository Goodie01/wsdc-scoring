package nz.geek.goodwin.wsdc;

import nz.geek.goodwin.wsdc.domain.Judge;
import nz.geek.goodwin.wsdc.domain.Person;
import nz.geek.goodwin.wsdc.domain.Result;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.List;

/**
 * @author thomas.goodwin
 */
class RelativePlacementScoringTest {
    /**
     * 7 judge example from https://www.worldsdc.com/wp-content/uploads/2021/04/Relative-Placement-12-20-20-Rev-5-1-10-1.pdf
     */
    @Test
    void exampleFromWsdcWhitePaper() {
        RelativePlacementRound scoringRound = new RelativePlacementRound();

        scoringRound.setHeadJudge(new Judge("head","Head Judge"));
        scoringRound.setJudges(List.of(
                new Judge("1","Judge 1"),
                new Judge("2","Judge 2"),
                new Judge("3","Judge 3"),
                new Judge("4","Judge 4"),
                new Judge("5","Judge 5"),
                new Judge("6","Judge 6"),
                new Judge("7","Judge 7")
        ));

        scoringRound.setDancers(List.of(
                new Person("1","Dancer 1"),
                new Person("2","Dancer 2"),
                new Person("3","Dancer 3"),
                new Person("4","Dancer 4"),
                new Person("5","Dancer 5"),
                new Person("6","Dancer 6"),
                new Person("7","Dancer 7"),
                new Person("8","Dancer 8"),
                new Person("9","Dancer 9"),
                new Person("10","Dancer 10"),
                new Person("11","Dancer 11"),
                new Person("12","Dancer 12")
        ));

        scoringRound.addScore("1","1", BigInteger.valueOf(5));
        scoringRound.addScore("2","1", BigInteger.valueOf(12));
        scoringRound.addScore("3","1", BigInteger.valueOf(6));
        scoringRound.addScore("4","1", BigInteger.valueOf(12));
        scoringRound.addScore("5","1", BigInteger.valueOf(9));
        scoringRound.addScore("6","1", BigInteger.valueOf(12));
        scoringRound.addScore("7","1", BigInteger.valueOf(10));
        scoringRound.addHeadJudgeScore("1", BigInteger.valueOf(7));

        scoringRound.addScore("1","2", BigInteger.valueOf(10));
        scoringRound.addScore("2","2", BigInteger.valueOf(1));
        scoringRound.addScore("3","2", BigInteger.valueOf(12));
        scoringRound.addScore("4","2", BigInteger.valueOf(3));
        scoringRound.addScore("5","2", BigInteger.valueOf(7));
        scoringRound.addScore("6","2", BigInteger.valueOf(8));
        scoringRound.addScore("7","2", BigInteger.valueOf(7));
        scoringRound.addHeadJudgeScore("2", BigInteger.valueOf(5));

        scoringRound.addScore("1","3", BigInteger.valueOf(4));
        scoringRound.addScore("2","3", BigInteger.valueOf(7));
        scoringRound.addScore("3","3", BigInteger.valueOf(7));
        scoringRound.addScore("4","3", BigInteger.valueOf(9));
        scoringRound.addScore("5","3", BigInteger.valueOf(6));
        scoringRound.addScore("6","3", BigInteger.valueOf(3));
        scoringRound.addScore("7","3", BigInteger.valueOf(5));
        scoringRound.addHeadJudgeScore("3", BigInteger.valueOf(9));

        scoringRound.addScore("1","4", BigInteger.valueOf(2));
        scoringRound.addScore("2","4", BigInteger.valueOf(3));
        scoringRound.addScore("3","4", BigInteger.valueOf(5));
        scoringRound.addScore("4","4", BigInteger.valueOf(1));
        scoringRound.addScore("5","4", BigInteger.valueOf(1));
        scoringRound.addScore("6","4", BigInteger.valueOf(2));
        scoringRound.addScore("7","4", BigInteger.valueOf(2));
        scoringRound.addHeadJudgeScore("4", BigInteger.valueOf(2));

        scoringRound.addScore("1","5", BigInteger.valueOf(6));
        scoringRound.addScore("2","5", BigInteger.valueOf(6));
        scoringRound.addScore("3","5", BigInteger.valueOf(9));
        scoringRound.addScore("4","5", BigInteger.valueOf(4));
        scoringRound.addScore("5","5", BigInteger.valueOf(2));
        scoringRound.addScore("6","5", BigInteger.valueOf(7));
        scoringRound.addScore("7","5", BigInteger.valueOf(8));
        scoringRound.addHeadJudgeScore("5", BigInteger.valueOf(6));

        scoringRound.addScore("1","6", BigInteger.valueOf(11));
        scoringRound.addScore("2","6", BigInteger.valueOf(11));
        scoringRound.addScore("3","6", BigInteger.valueOf(3));
        scoringRound.addScore("4","6", BigInteger.valueOf(10));
        scoringRound.addScore("5","6", BigInteger.valueOf(4));
        scoringRound.addScore("6","6", BigInteger.valueOf(10));
        scoringRound.addScore("7","6", BigInteger.valueOf(11));
        scoringRound.addHeadJudgeScore("6", BigInteger.valueOf(12));

        scoringRound.addScore("1","7", BigInteger.valueOf(1));
        scoringRound.addScore("2","7", BigInteger.valueOf(4));
        scoringRound.addScore("3","7", BigInteger.valueOf(2));
        scoringRound.addScore("4","7", BigInteger.valueOf(2));
        scoringRound.addScore("5","7", BigInteger.valueOf(10));
        scoringRound.addScore("6","7", BigInteger.valueOf(9));
        scoringRound.addScore("7","7", BigInteger.valueOf(3));
        scoringRound.addHeadJudgeScore("7", BigInteger.valueOf(3));

        scoringRound.addScore("1","8", BigInteger.valueOf(9));
        scoringRound.addScore("2","8", BigInteger.valueOf(9));
        scoringRound.addScore("3","8", BigInteger.valueOf(11));
        scoringRound.addScore("4","8", BigInteger.valueOf(8));
        scoringRound.addScore("5","8", BigInteger.valueOf(5));
        scoringRound.addScore("6","8", BigInteger.valueOf(6));
        scoringRound.addScore("7","8", BigInteger.valueOf(9));
        scoringRound.addHeadJudgeScore("8", BigInteger.valueOf(10));

        scoringRound.addScore("1","9", BigInteger.valueOf(3));
        scoringRound.addScore("2","9", BigInteger.valueOf(2));
        scoringRound.addScore("3","9", BigInteger.valueOf(1));
        scoringRound.addScore("4","9", BigInteger.valueOf(7));
        scoringRound.addScore("5","9", BigInteger.valueOf(3));
        scoringRound.addScore("6","9", BigInteger.valueOf(1));
        scoringRound.addScore("7","9", BigInteger.valueOf(1));
        scoringRound.addHeadJudgeScore("9", BigInteger.valueOf(1));

        scoringRound.addScore("1","10", BigInteger.valueOf(8));
        scoringRound.addScore("2","10", BigInteger.valueOf(5));
        scoringRound.addScore("3","10", BigInteger.valueOf(8));
        scoringRound.addScore("4","10", BigInteger.valueOf(6));
        scoringRound.addScore("5","10", BigInteger.valueOf(12));
        scoringRound.addScore("6","10", BigInteger.valueOf(4));
        scoringRound.addScore("7","10", BigInteger.valueOf(6));
        scoringRound.addHeadJudgeScore("10", BigInteger.valueOf(8));

        scoringRound.addScore("1","11", BigInteger.valueOf(12));
        scoringRound.addScore("2","11", BigInteger.valueOf(8));
        scoringRound.addScore("3","11", BigInteger.valueOf(10));
        scoringRound.addScore("4","11", BigInteger.valueOf(11));
        scoringRound.addScore("5","11", BigInteger.valueOf(11));
        scoringRound.addScore("6","11", BigInteger.valueOf(11));
        scoringRound.addScore("7","11", BigInteger.valueOf(12));
        scoringRound.addHeadJudgeScore("11", BigInteger.valueOf(11));

        scoringRound.addScore("1","12", BigInteger.valueOf(7));
        scoringRound.addScore("2","12", BigInteger.valueOf(10));
        scoringRound.addScore("3","12", BigInteger.valueOf(4));
        scoringRound.addScore("4","12", BigInteger.valueOf(5));
        scoringRound.addScore("5","12", BigInteger.valueOf(8));
        scoringRound.addScore("6","12", BigInteger.valueOf(5));
        scoringRound.addScore("7","12", BigInteger.valueOf(4));
        scoringRound.addHeadJudgeScore("12", BigInteger.valueOf(4));

        List<Result<BigInteger, BigInteger>> process = scoringRound.process();
        Assertions.assertEquals(12, process.size());

        validate(process.get(0), "4", BigInteger.valueOf(1));
        validate(process.get(1), "9", BigInteger.valueOf(2));
        validate(process.get(2), "7", BigInteger.valueOf(3));
        validate(process.get(3), "12", BigInteger.valueOf(4));
        validate(process.get(4), "3", BigInteger.valueOf(5));
        validate(process.get(5), "5", BigInteger.valueOf(6));
        validate(process.get(6), "10", BigInteger.valueOf(7));
        validate(process.get(7), "2", BigInteger.valueOf(8));
        validate(process.get(8), "8", BigInteger.valueOf(9));
        validate(process.get(9), "6", BigInteger.valueOf(10));
        validate(process.get(10), "1", BigInteger.valueOf(11));
        validate(process.get(11), "11", BigInteger.valueOf(12));
    }
    /**
     * 5 judge example from https://www.worldsdc.com/wp-content/uploads/2021/04/Scoring-By-Hand_Example_12-20-20.pdf
     * (without judge #4 and #5)
     */
    @Test
    void exampleFromWsdcWhitepaperMinusJudges() {
        RelativePlacementRound scoringRound = new RelativePlacementRound();

        scoringRound.setHeadJudge(new Judge("head","Head Judge"));
        scoringRound.setJudges(List.of(
                new Judge("1","Judge 1"),
                new Judge("2","Judge 2"),
                new Judge("3","Judge 3"),
                new Judge("6","Judge 6"),
                new Judge("7","Judge 7")
        ));

        scoringRound.setDancers(List.of(
                new Person("1","Dancer 1"),
                new Person("2","Dancer 2"),
                new Person("3","Dancer 3"),
                new Person("4","Dancer 4"),
                new Person("5","Dancer 5"),
                new Person("6","Dancer 6"),
                new Person("7","Dancer 7"),
                new Person("8","Dancer 8"),
                new Person("9","Dancer 9"),
                new Person("10","Dancer 10"),
                new Person("11","Dancer 11"),
                new Person("12","Dancer 12")
        ));

        scoringRound.addScore("1","1", BigInteger.valueOf(5));
        scoringRound.addScore("2","1", BigInteger.valueOf(12));
        scoringRound.addScore("3","1", BigInteger.valueOf(6));
        scoringRound.addScore("6","1", BigInteger.valueOf(12));
        scoringRound.addScore("7","1", BigInteger.valueOf(10));
        scoringRound.addHeadJudgeScore("1", BigInteger.valueOf(7));

        scoringRound.addScore("1","2", BigInteger.valueOf(10));
        scoringRound.addScore("2","2", BigInteger.valueOf(1));
        scoringRound.addScore("3","2", BigInteger.valueOf(12));
        scoringRound.addScore("6","2", BigInteger.valueOf(8));
        scoringRound.addScore("7","2", BigInteger.valueOf(7));
        scoringRound.addHeadJudgeScore("2", BigInteger.valueOf(5));

        scoringRound.addScore("1","3", BigInteger.valueOf(4));
        scoringRound.addScore("2","3", BigInteger.valueOf(7));
        scoringRound.addScore("3","3", BigInteger.valueOf(7));
        scoringRound.addScore("6","3", BigInteger.valueOf(3));
        scoringRound.addScore("7","3", BigInteger.valueOf(5));
        scoringRound.addHeadJudgeScore("3", BigInteger.valueOf(9));

        scoringRound.addScore("1","4", BigInteger.valueOf(2));
        scoringRound.addScore("2","4", BigInteger.valueOf(3));
        scoringRound.addScore("3","4", BigInteger.valueOf(5));
        scoringRound.addScore("6","4", BigInteger.valueOf(2));
        scoringRound.addScore("7","4", BigInteger.valueOf(2));
        scoringRound.addHeadJudgeScore("4", BigInteger.valueOf(2));

        scoringRound.addScore("1","5", BigInteger.valueOf(6));
        scoringRound.addScore("2","5", BigInteger.valueOf(6));
        scoringRound.addScore("3","5", BigInteger.valueOf(9));
        scoringRound.addScore("6","5", BigInteger.valueOf(7));
        scoringRound.addScore("7","5", BigInteger.valueOf(8));
        scoringRound.addHeadJudgeScore("5", BigInteger.valueOf(6));

        scoringRound.addScore("1","6", BigInteger.valueOf(11));
        scoringRound.addScore("2","6", BigInteger.valueOf(11));
        scoringRound.addScore("3","6", BigInteger.valueOf(3));
        scoringRound.addScore("6","6", BigInteger.valueOf(10));
        scoringRound.addScore("7","6", BigInteger.valueOf(11));
        scoringRound.addHeadJudgeScore("6", BigInteger.valueOf(12));

        scoringRound.addScore("1","7", BigInteger.valueOf(1));
        scoringRound.addScore("2","7", BigInteger.valueOf(4));
        scoringRound.addScore("3","7", BigInteger.valueOf(2));
        scoringRound.addScore("6","7", BigInteger.valueOf(9));
        scoringRound.addScore("7","7", BigInteger.valueOf(3));
        scoringRound.addHeadJudgeScore("7", BigInteger.valueOf(3));

        scoringRound.addScore("1","8", BigInteger.valueOf(9));
        scoringRound.addScore("2","8", BigInteger.valueOf(9));
        scoringRound.addScore("3","8", BigInteger.valueOf(11));
        scoringRound.addScore("6","8", BigInteger.valueOf(6));
        scoringRound.addScore("7","8", BigInteger.valueOf(9));
        scoringRound.addHeadJudgeScore("8", BigInteger.valueOf(10));

        scoringRound.addScore("1","9", BigInteger.valueOf(3));
        scoringRound.addScore("2","9", BigInteger.valueOf(2));
        scoringRound.addScore("3","9", BigInteger.valueOf(1));
        scoringRound.addScore("6","9", BigInteger.valueOf(1));
        scoringRound.addScore("7","9", BigInteger.valueOf(1));
        scoringRound.addHeadJudgeScore("9", BigInteger.valueOf(1));

        scoringRound.addScore("1","10", BigInteger.valueOf(8));
        scoringRound.addScore("2","10", BigInteger.valueOf(5));
        scoringRound.addScore("3","10", BigInteger.valueOf(8));
        scoringRound.addScore("6","10", BigInteger.valueOf(4));
        scoringRound.addScore("7","10", BigInteger.valueOf(6));
        scoringRound.addHeadJudgeScore("10", BigInteger.valueOf(8));

        scoringRound.addScore("1","11", BigInteger.valueOf(12));
        scoringRound.addScore("2","11", BigInteger.valueOf(8));
        scoringRound.addScore("3","11", BigInteger.valueOf(10));
        scoringRound.addScore("6","11", BigInteger.valueOf(11));
        scoringRound.addScore("7","11", BigInteger.valueOf(12));
        scoringRound.addHeadJudgeScore("11", BigInteger.valueOf(11));

        scoringRound.addScore("1","12", BigInteger.valueOf(7));
        scoringRound.addScore("2","12", BigInteger.valueOf(10));
        scoringRound.addScore("3","12", BigInteger.valueOf(4));
        scoringRound.addScore("6","12", BigInteger.valueOf(5));
        scoringRound.addScore("7","12", BigInteger.valueOf(4));
        scoringRound.addHeadJudgeScore("12", BigInteger.valueOf(4));

        List<Result<BigInteger, BigInteger>> process = scoringRound.process();
        Assertions.assertEquals(12, process.size());

        validate(process.get(0), "9", BigInteger.valueOf(1));
        validate(process.get(1), "4", BigInteger.valueOf(2));
        validate(process.get(2), "7", BigInteger.valueOf(3));
        validate(process.get(3), "3", BigInteger.valueOf(4));
        validate(process.get(4), "12", BigInteger.valueOf(5));
        validate(process.get(5), "10", BigInteger.valueOf(6));
        validate(process.get(6), "5", BigInteger.valueOf(7));
        validate(process.get(7), "2", BigInteger.valueOf(8));
        validate(process.get(8), "8", BigInteger.valueOf(9));
        validate(process.get(9), "1", BigInteger.valueOf(10));
        validate(process.get(10), "6", BigInteger.valueOf(11));
        validate(process.get(11), "11", BigInteger.valueOf(12));
    }
    /**
     * 5 judge example from https://www.worldsdc.com/wp-content/uploads/2021/04/Scoring-By-Hand_Example_12-20-20.pdf
     * (without judge #2 and #3)
     */
    @Test
    void exampleFromWsdcWhitepaperMinusJudges2() {
        RelativePlacementRound scoringRound = new RelativePlacementRound();

        scoringRound.setHeadJudge(new Judge("head","Head Judge"));
        scoringRound.setJudges(List.of(
                new Judge("1","Judge 1"),
                new Judge("4","Judge 4"),
                new Judge("5","Judge 5"),
                new Judge("6","Judge 6"),
                new Judge("7","Judge 7")
        ));

        scoringRound.setDancers(List.of(
                new Person("1","Dancer 1"),
                new Person("2","Dancer 2"),
                new Person("3","Dancer 3"),
                new Person("4","Dancer 4"),
                new Person("5","Dancer 5"),
                new Person("6","Dancer 6"),
                new Person("7","Dancer 7"),
                new Person("8","Dancer 8"),
                new Person("9","Dancer 9"),
                new Person("10","Dancer 10"),
                new Person("11","Dancer 11"),
                new Person("12","Dancer 12")
        ));

        scoringRound.addScore("1","1", BigInteger.valueOf(5));
        scoringRound.addScore("4","1", BigInteger.valueOf(12));
        scoringRound.addScore("5","1", BigInteger.valueOf(9));
        scoringRound.addScore("6","1", BigInteger.valueOf(12));
        scoringRound.addScore("7","1", BigInteger.valueOf(10));
        scoringRound.addHeadJudgeScore("1", BigInteger.valueOf(7));

        scoringRound.addScore("1","2", BigInteger.valueOf(10));
        scoringRound.addScore("4","2", BigInteger.valueOf(3));
        scoringRound.addScore("5","2", BigInteger.valueOf(7));
        scoringRound.addScore("6","2", BigInteger.valueOf(8));
        scoringRound.addScore("7","2", BigInteger.valueOf(7));
        scoringRound.addHeadJudgeScore("2", BigInteger.valueOf(5));

        scoringRound.addScore("1","3", BigInteger.valueOf(4));
        scoringRound.addScore("4","3", BigInteger.valueOf(9));
        scoringRound.addScore("5","3", BigInteger.valueOf(6));
        scoringRound.addScore("6","3", BigInteger.valueOf(3));
        scoringRound.addScore("7","3", BigInteger.valueOf(5));
        scoringRound.addHeadJudgeScore("3", BigInteger.valueOf(9));

        scoringRound.addScore("1","4", BigInteger.valueOf(2));
        scoringRound.addScore("4","4", BigInteger.valueOf(1));
        scoringRound.addScore("5","4", BigInteger.valueOf(1));
        scoringRound.addScore("6","4", BigInteger.valueOf(2));
        scoringRound.addScore("7","4", BigInteger.valueOf(2));
        scoringRound.addHeadJudgeScore("4", BigInteger.valueOf(2));

        scoringRound.addScore("1","5", BigInteger.valueOf(6));
        scoringRound.addScore("4","5", BigInteger.valueOf(4));
        scoringRound.addScore("5","5", BigInteger.valueOf(2));
        scoringRound.addScore("6","5", BigInteger.valueOf(7));
        scoringRound.addScore("7","5", BigInteger.valueOf(8));
        scoringRound.addHeadJudgeScore("5", BigInteger.valueOf(6));

        scoringRound.addScore("1","6", BigInteger.valueOf(11));
        scoringRound.addScore("4","6", BigInteger.valueOf(10));
        scoringRound.addScore("5","6", BigInteger.valueOf(4));
        scoringRound.addScore("6","6", BigInteger.valueOf(10));
        scoringRound.addScore("7","6", BigInteger.valueOf(11));
        scoringRound.addHeadJudgeScore("6", BigInteger.valueOf(12));

        scoringRound.addScore("1","7", BigInteger.valueOf(1));
        scoringRound.addScore("4","7", BigInteger.valueOf(2));
        scoringRound.addScore("5","7", BigInteger.valueOf(10));
        scoringRound.addScore("6","7", BigInteger.valueOf(9));
        scoringRound.addScore("7","7", BigInteger.valueOf(3));
        scoringRound.addHeadJudgeScore("7", BigInteger.valueOf(3));

        scoringRound.addScore("1","8", BigInteger.valueOf(9));
        scoringRound.addScore("4","8", BigInteger.valueOf(8));
        scoringRound.addScore("5","8", BigInteger.valueOf(5));
        scoringRound.addScore("6","8", BigInteger.valueOf(6));
        scoringRound.addScore("7","8", BigInteger.valueOf(9));
        scoringRound.addHeadJudgeScore("8", BigInteger.valueOf(10));

        scoringRound.addScore("1","9", BigInteger.valueOf(3));
        scoringRound.addScore("4","9", BigInteger.valueOf(7));
        scoringRound.addScore("5","9", BigInteger.valueOf(3));
        scoringRound.addScore("6","9", BigInteger.valueOf(1));
        scoringRound.addScore("7","9", BigInteger.valueOf(1));
        scoringRound.addHeadJudgeScore("9", BigInteger.valueOf(1));

        scoringRound.addScore("1","10", BigInteger.valueOf(8));
        scoringRound.addScore("4","10", BigInteger.valueOf(6));
        scoringRound.addScore("5","10", BigInteger.valueOf(12));
        scoringRound.addScore("6","10", BigInteger.valueOf(4));
        scoringRound.addScore("7","10", BigInteger.valueOf(6));
        scoringRound.addHeadJudgeScore("10", BigInteger.valueOf(8));

        scoringRound.addScore("1","11", BigInteger.valueOf(12));
        scoringRound.addScore("4","11", BigInteger.valueOf(11));
        scoringRound.addScore("5","11", BigInteger.valueOf(11));
        scoringRound.addScore("6","11", BigInteger.valueOf(11));
        scoringRound.addScore("7","11", BigInteger.valueOf(12));
        scoringRound.addHeadJudgeScore("11", BigInteger.valueOf(11));

        scoringRound.addScore("1","12", BigInteger.valueOf(7));
        scoringRound.addScore("4","12", BigInteger.valueOf(5));
        scoringRound.addScore("5","12", BigInteger.valueOf(8));
        scoringRound.addScore("6","12", BigInteger.valueOf(5));
        scoringRound.addScore("7","12", BigInteger.valueOf(4));
        scoringRound.addHeadJudgeScore("12", BigInteger.valueOf(4));

        List<Result<BigInteger, BigInteger>> process = scoringRound.process();
        Assertions.assertEquals(12, process.size());

        validate(process.get(0), "4", BigInteger.valueOf(1));
        validate(process.get(1), "9", BigInteger.valueOf(2));
        validate(process.get(2), "7", BigInteger.valueOf(3));
        validate(process.get(3), "3", BigInteger.valueOf(4));
        validate(process.get(4), "12", BigInteger.valueOf(5));
        validate(process.get(5), "5", BigInteger.valueOf(6));
        validate(process.get(6), "10", BigInteger.valueOf(7));
        validate(process.get(7), "2", BigInteger.valueOf(8));
        validate(process.get(8), "8", BigInteger.valueOf(9));
        validate(process.get(9), "6", BigInteger.valueOf(10)); //This fails because I'm not doing THE NEXT placement level (Eg
        validate(process.get(10), "1", BigInteger.valueOf(11));
        validate(process.get(11), "11", BigInteger.valueOf(12));
    }

    private <T,R> void validate(Result<T, R> result, String number, R callbackResult) {
        Assertions.assertEquals(number, result.person().id());
        Assertions.assertEquals(callbackResult, result.result());
    }
}