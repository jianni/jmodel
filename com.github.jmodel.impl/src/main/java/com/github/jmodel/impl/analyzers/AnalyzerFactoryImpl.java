package com.github.jmodel.impl.analyzers;

import com.github.jmodel.api.Analyzer;
import com.github.jmodel.api.FormatEnum;
import com.github.jmodel.spi.AnalyzerFactory;

public class AnalyzerFactoryImpl implements AnalyzerFactory {

	@Override
	public Analyzer getAnalyzer(FormatEnum format, String extendAnalyzerName) {
		switch (format) {
		case JSON:
			return new JsonAnalyzer();
		case XML:
			return new XmlAnalyzer();
		case BEAN:
			return new BeanAnalyzer();
		default:
			return null;
		}
	}

}
