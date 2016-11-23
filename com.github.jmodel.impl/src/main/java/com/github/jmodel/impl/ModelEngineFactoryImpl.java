package com.github.jmodel.impl;

import com.github.jmodel.api.ModelEngine;
import com.github.jmodel.spi.ModelEngineFactory;

public class ModelEngineFactoryImpl implements ModelEngineFactory {

	@Override
	public ModelEngine get() {
		return new ModelEngineImpl();
	}
}
