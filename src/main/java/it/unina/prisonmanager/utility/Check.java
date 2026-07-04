package it.unina.prisonmanager.utility;

import java.util.Objects;
import java.util.regex.Pattern;

public interface Check
{
	public static double requireInRange(
		double value, double min, double max
	) {
		return requireInRange(
			value, min, max, "Value needs to be between "
			+ min + " and " +  max + '.'
		);
	}
	public static double requireInRange(
		double value, double min, double max, String message
	) {
		if (min > max) {
			return requireInRange(value, max, min, message);
		} if (value < min || value > max) {
			throw new IllegalArgumentException(message);
		} return value;
	}
	
	public static String requireNonVoid(String string) {
		return requireNonVoid(string, "String is either blank or NULL.");
	}
	
	public static String requireNonVoid(String string, String message) {
		Objects.requireNonNull(string, message);
		if (string.isBlank()) {
			throw new IllegalArgumentException(message);
		} return string;
	}
	
	public static String requirePatternMatch(String string, Pattern toMatch) {
		return requirePatternMatch(string, toMatch, "String does not match the given pattern.");
	}
	
	public static String requirePatternMatch(
		String string, Pattern toMatch, String message
	) {
		if (toMatch != null && !toMatch.matcher(string).matches()) {
			throw new IllegalArgumentException(message);
		} return string;
	}
}
