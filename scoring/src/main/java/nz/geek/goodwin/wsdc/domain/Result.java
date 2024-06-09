package nz.geek.goodwin.wsdc.domain;

import java.util.List;

/**
 * @author thomas.goodwin
 */
public record Result<T,R>(Person person, List<Score<T>> scores, R result) {
}
