package com.github.jmodel.impl;

import java.util.Locale;
import java.util.ResourceBundle;

import com.github.jmodel.api.Analyzer;
import com.github.jmodel.api.AnalyzerFactoryService;
import com.github.jmodel.api.FormatChecker;
import com.github.jmodel.api.FormatCheckerFactoryService;
import com.github.jmodel.api.FormatEnum;
import com.github.jmodel.api.IllegalException;
import com.github.jmodel.api.Model;
import com.github.jmodel.api.ModelEngine;

public class ModelEngineImpl implements ModelEngine {

	private ResourceBundle messages;

	public Model construct(Object sourceObj, FormatEnum format) {
		return construct(sourceObj, format, Locale.getDefault());
	}

	public Model construct(Object sourceObj, FormatEnum format, Locale currentLocale) {
		Analyzer analyzer = getAnalyzer(sourceObj, format, currentLocale);
		Model model = analyzer.process(new EntityImpl(), sourceObj, true);
		return model;
	}

	@Override
	public Model fill(Model sourceModel, Object sourceObj, FormatEnum format) {
		return fill(sourceModel, sourceObj, format, Locale.getDefault());
	}

	@Override
	public Model fill(Model sourceModel, Object sourceObj, FormatEnum format, Locale currentLocale) {
		Analyzer analyzer = getAnalyzer(sourceObj, format, currentLocale);
		Model model = analyzer.process(sourceModel, sourceObj, false);
		return model;
	}

	private Analyzer getAnalyzer(Object sourceObj, FormatEnum format, Locale currentLocale) {
		messages = ResourceBundle.getBundle("com.github.jmodel.api.MessagesBundle", currentLocale);

		if (sourceObj == null) {
			throw new IllegalException(messages.getString("SRC_IS_NULL"));
		}

		FormatChecker formatChecker = FormatCheckerFactoryService.getInstance().getFormatChecker(format);
		if (!formatChecker.isValid(sourceObj)) {
			throw new IllegalException(String.format(currentLocale, messages.getString("SRC_FMT_NOT_SUPPORT"),
					sourceObj.getClass().getName(), format));
		}

		AnalyzerFactoryService analyzerFactoryService = AnalyzerFactoryService.getInstance();
		Analyzer analyzer = analyzerFactoryService.getAnalyzer(format, null);
		return analyzer;
	}
}
