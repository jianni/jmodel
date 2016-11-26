package com.github.jmodel.impl;

import static org.junit.Assert.*;

import java.io.InputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.github.jmodel.api.FormatEnum;
import com.github.jmodel.api.Model;
import com.github.jmodel.api.ModelEngine;
import com.github.jmodel.api.ModelEngineFactoryService;

public class ModelEngineImplTest {

	private ModelEngine mEngine = ModelEngineFactoryService.getInstance().get();

	private Model model;

	@Before
	public void setUp() throws Exception {

	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void testConstruct() {
		InputStream sourceObj = getClass().getResourceAsStream("data.json");
		model = mEngine.construct(sourceObj, FormatEnum.JSON);
		if (model == null) {
			fail("Not yet implemented");
		}
		if (sourceObj != null) {
			try {
				sourceObj.close();
			} catch (Exception e) {

			}
		}
	}
}
