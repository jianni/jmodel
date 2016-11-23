package com.github.jmodel.api;

import java.util.Iterator;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;

import com.github.jmodel.spi.ModelEngineFactory;

public class ModelEngineFactoryService {

	private static ModelEngineFactoryService service;
	private ServiceLoader<ModelEngineFactory> loader;

	private ModelEngineFactoryService() {
		loader = ServiceLoader.load(ModelEngineFactory.class);
	}

	public static synchronized ModelEngineFactoryService getInstance() {
		if (service == null) {
			service = new ModelEngineFactoryService();
		}
		return service;
	}

	public ModelEngine get() {
		ModelEngine engine = null;

		try {
			Iterator<ModelEngineFactory> engineFactorys = loader.iterator();
			while (engine == null && engineFactorys.hasNext()) {
				ModelEngineFactory engineFactory = engineFactorys.next();
				engine = engineFactory.get();
			}
		} catch (ServiceConfigurationError serviceError) {
			engine = null;
			serviceError.printStackTrace();

		}
		return engine;
	}
}
