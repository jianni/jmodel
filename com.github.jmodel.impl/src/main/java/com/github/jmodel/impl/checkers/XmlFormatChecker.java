package com.github.jmodel.impl.checkers;

import java.io.InputStream;

import com.github.jmodel.api.FormatChecker;

public class XmlFormatChecker implements FormatChecker {

	public boolean isValid(Object sourceObject) {
		if (sourceObject instanceof String || sourceObject instanceof InputStream) {
			return true;
		}
		return false;
	}

}
