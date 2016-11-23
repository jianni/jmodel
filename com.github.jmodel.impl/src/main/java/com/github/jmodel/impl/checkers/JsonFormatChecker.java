package com.github.jmodel.impl.checkers;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.jmodel.api.FormatChecker;

public class JsonFormatChecker implements FormatChecker {

	public boolean isValid(Object sourceObject) {
		if (sourceObject instanceof String || sourceObject instanceof JsonNode || sourceObject instanceof File
				|| sourceObject instanceof InputStream || sourceObject instanceof byte[]
				|| sourceObject instanceof Reader || sourceObject instanceof URL) {
			return true;
		}
		return false;
	}

}
