package nz.geek.goodwin.wsdc.domain;

import java.math.BigInteger;
import java.util.List;

/**
 * @author Goodie
 */
public record RelativePlacingInterimResult(Person person, Long countScore, Long sumScore, List<Score<BigInteger>> scores) {
}
