package com.github.jmodel.api;

import java.util.Iterator;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;

import com.github.jmodel.spi.FormatCheckerFactory;

public class FormatCheckerFactoryService {

	private static FormatCheckerFactoryService service;
	private ServiceLoader<FormatCheckerFactory> loader;

	private FormatCheckerFactoryService() {
		loader = ServiceLoader.load(FormatCheckerFactory.class);
	}

	public static synchronized FormatCheckerFactoryService getInstance() {
		if (service == null) {
			service = new FormatCheckerFactoryService();
		}
		return service;
	}

	public FormatChecker getFormatChecker(FormatEnum fromFormat) {
		FormatChecker formatChecker = null;

		try {
			Iterator<FormatCheckerFactory> formatCheckerFactorys = loader.iterator();
			while (formatChecker == null && formatCheckerFactorys.hasNext()) {
				FormatCheckerFactory formatCheckerFactory = formatCheckerFactorys.next();
				formatChecker = formatCheckerFactory.getFormatChecker(fromFormat);
			}
		} catch (ServiceConfigurationError serviceError) {
			formatChecker = null;
			serviceError.printStackTrace();

		}
		return formatChecker;
	}
}
