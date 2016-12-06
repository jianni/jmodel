package com.github.jmodel.api;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class ModelHelper {

	private final static SimpleDateFormat dateFormat = new SimpleDateFormat();

	private final static String BUNDLE_NAME = "com.github.jmodel.api.MessagesBundle";

	public static Date getDate(String fieldValue) {
		return getDate(fieldValue, null);
	}

	public static Date getDate(String fieldValue, String pattern) {
		try {
			if (pattern == null) {
				return dateFormat.parse(fieldValue);
			}
			return new SimpleDateFormat(pattern).parse(fieldValue);
		} catch (ParseException e) {
			throw new IllegalException("failed to parse date string");
		}
	}

	public static String getFieldValue(Field field) {
		if (field == null) {
			return "";
		}
		return field.getValue();
	}

	public static <T1, T2> String calc(final T1 leftOperant, final T2 rightOperant, final OperationEnum operation,
			final Locale currentLocale) {
		ResourceBundle messages = ResourceBundle.getBundle(BUNDLE_NAME, currentLocale);
		switch (operation) {
		case PLUS: {
			if (leftOperant instanceof String || rightOperant instanceof String) {
				return String.valueOf(leftOperant) + String.valueOf(rightOperant);
			} else if (leftOperant instanceof Integer && rightOperant instanceof Integer) {
				return String.valueOf((Integer) leftOperant + (Integer) rightOperant);
			}
			return null;
		}
		case MULTIPLY: {
			return String.valueOf(Integer.valueOf((String) leftOperant) * Integer.valueOf((String) rightOperant));
		}
		default:
			throw new IllegalException(
					String.format(currentLocale, messages.getString("OPR_NOT_SUPPORT"), operation, "xxx"));
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Boolean predict(final Comparable leftOperant, final Comparable rightOperant,
			final OperationEnum operation, final Locale currentLocale) {
		ResourceBundle messages = ResourceBundle.getBundle(BUNDLE_NAME, currentLocale);
		if (leftOperant == null || rightOperant == null) {
			switch (operation) {
			case EQUALS: {
				return leftOperant == rightOperant;
			}
			case NOTEQUALS: {
				return leftOperant != rightOperant;
			}
			default:
				throw new IllegalException(
						String.format(currentLocale, messages.getString("OPR_NOT_SUPPORT"), operation, "NULL"));
			}
		}
		int compareResult = leftOperant.compareTo(rightOperant);
		switch (operation) {
		case EQUALS: {
			return compareResult == 0 ? true : false;
		}
		case NOTEQUALS: {
			return compareResult != 0 ? true : false;
		}
		case GT: {
			return compareResult > 0 ? true : false;
		}
		case GTE: {
			return (compareResult > 0 || compareResult == 0) ? true : false;
		}
		case LT: {
			return compareResult < 0 ? true : false;
		}
		case LTE: {
			return (compareResult < 0 || compareResult == 0) ? true : false;
		}
		default:
			throw new IllegalException(
					String.format(currentLocale, messages.getString("OPR_NOT_SUPPORT"), operation, "Comparable"));
		}
	}

	public static Boolean predict(final String leftOperant, final List<String> rightOperant,
			final OperationEnum operation, final Locale currentLocale) {
		ResourceBundle messages = ResourceBundle.getBundle(BUNDLE_NAME, currentLocale);
		switch (operation) {
		case IN: {
			return rightOperant.contains(leftOperant);
		}
		case NOTIN: {
			return !rightOperant.contains(leftOperant);
		}
		default:
			throw new IllegalException(
					String.format(currentLocale, messages.getString("OPR_NOT_SUPPORT"), operation, "String and List"));
		}

	}

	public static Boolean predict(final Boolean leftOperant, final Boolean rightOperant, final OperationEnum operation,
			final Locale currentLocale) {
		ResourceBundle messages = ResourceBundle.getBundle(BUNDLE_NAME, currentLocale);
		switch (operation) {
		case OR: {
			return Boolean.logicalOr(leftOperant, rightOperant);
		}
		case AND: {
			return Boolean.logicalAnd(leftOperant, rightOperant);
		}
		default:
			throw new IllegalException(
					String.format(currentLocale, messages.getString("OPR_NOT_SUPPORT"), operation, "Boolean"));
		}
	}
}
