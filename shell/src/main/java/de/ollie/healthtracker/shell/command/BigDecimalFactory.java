package de.ollie.healthtracker.shell.command;

import static de.ollie.baselib.util.Check.ensure;

import jakarta.inject.Named;
import java.math.BigDecimal;

@Named
class BigDecimalFactory {

	BigDecimal create(String bigDecimalString) {
		ensure(bigDecimalString != null, "big decimal string cannot be null!");
		return new BigDecimal(bigDecimalString);
	}
}
