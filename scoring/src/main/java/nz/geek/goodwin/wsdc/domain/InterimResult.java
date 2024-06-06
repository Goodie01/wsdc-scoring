package nz.geek.goodwin.wsdc.domain;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author thomas.goodwin
 */
public record InterimResult(Person dancer, List<Score> scores, BigDecimal headJudgeScore, BigDecimal totalScore) {
}
