package com.github.jmodel.spi;

import com.github.jmodel.api.FormatChecker;
import com.github.jmodel.api.FormatEnum;

public interface FormatCheckerFactory {

	public FormatChecker getFormatChecker(FormatEnum fromFormat);
}
