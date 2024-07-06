package nz.geek.goodwin.wsdc.domain;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 * @author thomas.goodwin
 */
public record InterimResult<T>(Person dancer, List<Score<T>> scores, BigInteger headJudgeScore, BigDecimal totalScore) {
}
