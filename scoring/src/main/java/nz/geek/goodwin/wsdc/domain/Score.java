package nz.geek.goodwin.wsdc.domain;

import java.math.BigDecimal;

/**
 * @author thomas.goodwin
 */
public record Score<T>(String givenBy, String givenTo, boolean displayableScore, T score) {
}
