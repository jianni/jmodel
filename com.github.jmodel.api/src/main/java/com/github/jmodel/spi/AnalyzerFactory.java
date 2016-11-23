package com.github.jmodel.spi;

import com.github.jmodel.api.Analyzer;
import com.github.jmodel.api.FormatEnum;

public interface AnalyzerFactory {

	public Analyzer getAnalyzer(FormatEnum fromFormat, String extendAnalyzerName);
}
