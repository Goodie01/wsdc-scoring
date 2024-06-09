package nz.geek.goodwin.wsdc.domain;

import java.math.BigDecimal;

/**
 * @author thomas.goodwin
 */
public enum CallbackOption {
    YES(BigDecimal.valueOf(10L)),
    ALT_1(BigDecimal.valueOf(4.5)),
    ALT_2(BigDecimal.valueOf(4.3)),
    ALT_3(BigDecimal.valueOf(4.2)),
    NO(BigDecimal.valueOf(0)),
    ;

    private final BigDecimal scoringValue;

    CallbackOption(BigDecimal scoringValue) {

        this.scoringValue = scoringValue;
    }

    public BigDecimal scoringValue() {
        return scoringValue;
    }
}
