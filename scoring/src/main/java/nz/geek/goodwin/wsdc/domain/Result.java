package nz.geek.goodwin.wsdc.domain;

import java.util.List;

/**
 * @author thomas.goodwin
 */
public record Result(Person person, List<Score> scores, CallbackResult result) {
}
