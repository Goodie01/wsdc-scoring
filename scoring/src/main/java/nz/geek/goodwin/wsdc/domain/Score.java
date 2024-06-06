package nz.geek.goodwin.wsdc.domain;

import java.math.BigDecimal;

/**
 * @author thomas.goodwin
 */
public record Score(String givenBy, String givenTo, boolean displayableScore, BigDecimal score) {
}
