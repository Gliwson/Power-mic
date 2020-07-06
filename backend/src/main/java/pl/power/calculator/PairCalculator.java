package pl.power.calculator;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PairCalculator<L,B> {

    private L key;
    private B value;

    public PairCalculator(L key, B value) {
        this.key = key;
        this.value = value;
    }
}
