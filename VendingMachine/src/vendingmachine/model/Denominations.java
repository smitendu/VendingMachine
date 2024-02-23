package vendingmachine.model;

import java.util.HashMap;
import java.util.Map;

public enum Denominations {
	
	ONE_PENNY (0.01),
	TWO_PENCE(0.02),
	FIVE_PENCE(0.05),
	TEN_PENCE(0.10),
	TWENTY_PENCE(0.20),
	FIFTY_PENCE(0.50),
	ONE_POUND(1.00),
	TWO_POUNDS(2.00);
	
	private static final Map<Double, Denominations> BY_VALUE = new HashMap<>(values().length, 1);

	static {
		for (Denominations c : values()) BY_VALUE.put(c.getValueOfDenomination(), c);
	}

    public final Double value;

    private Denominations(Double value) {
        this.value = value;
    }
    public Double getValueOfDenomination() {
    	return this.value;
    }
    public static Denominations getDenomination(Double value) {
    	return BY_VALUE.get(value);
    }
}
