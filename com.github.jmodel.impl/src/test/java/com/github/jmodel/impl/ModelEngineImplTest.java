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

	private InputStream sourceObj;

	@Before
	public void setUp() throws Exception {
		sourceObj = getClass().getResourceAsStream("data.json");
	}

	@After
	public void tearDown() throws Exception {
		if (sourceObj != null) {
			try {
				sourceObj.close();
			} catch (Exception e) {

			}
		}
	}

	@Test
	public void testConstruct() {
		Model model = mEngine.construct(sourceObj, FormatEnum.JSON);
		if (model == null) {
			fail("Not yet implemented");
		}
	}

	@Test
	public void testFill() {
		fail("Not yet implemented");
	}

}
