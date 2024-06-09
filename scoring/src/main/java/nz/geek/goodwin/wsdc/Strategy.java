package nz.geek.goodwin.wsdc;

import java.math.BigDecimal;
import java.util.List;

import nz.geek.goodwin.wsdc.domain.InterimResult;
import nz.geek.goodwin.wsdc.domain.Judge;
import nz.geek.goodwin.wsdc.domain.Person;
import nz.geek.goodwin.wsdc.domain.Result;
import nz.geek.goodwin.wsdc.domain.Score;

/**
 * @author thomas.goodwin
 */
public interface Strategy<T, R> {
    void rankInterimResults(List<InterimResult<T>> interimResults);

    List<InterimResult<T>> produceInterimResults(List<Person> dancers, List<Score<T>> scores, List<Score<BigDecimal>> headJudgeScores, Judge headJudge);

    List<Result<T, R>> produceResults(List<InterimResult<T>> interimResults);
}
