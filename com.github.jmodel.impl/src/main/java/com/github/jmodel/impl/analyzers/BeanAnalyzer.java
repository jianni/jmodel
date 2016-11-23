package com.github.jmodel.impl.analyzers;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.github.jmodel.api.Field;
import com.github.jmodel.api.Model;
import com.github.jmodel.impl.AbstractAnalyzer;

public class BeanAnalyzer extends AbstractAnalyzer<Object> {

	@Override
	public <T> Model process(Model sourceModel, T sourceObject, Boolean isConstruction) {
		build(sourceModel, new HashMap<String, Field>(), new HashMap<String, Model>(), sourceObject, isConstruction);
		return sourceModel;
	}

	@Override
	protected void setFieldValue(Object sourceNode, Field field) {
		try {
			Method m = sourceNode.getClass().getMethod("get" + StringUtils.capitalize(field.getName()));
			field.setValue((String) m.invoke(sourceNode));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected Object getSubNode(Object node, String subNodeName) {
		try {
			Method m = node.getClass().getMethod("get" + StringUtils.capitalize(subNodeName));
			return m.invoke(node);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void populateSubModel(Object subNode, Model subModel, Model subSubModel) {
		if (subNode instanceof List<?>) {
			List<?> subNodeList = (List<?>) subNode;
			for (int i = 0; i < subNodeList.size(); i++) {
				Object subSubJsonNode = subNodeList.get(i);

				Model clonedSubSubModel = null;
				if (i == 0) {
					clonedSubSubModel = subSubModel;
				} else {
					clonedSubSubModel = subSubModel.clone();
					subModel.getSubModels().add(clonedSubSubModel);
				}
				clonedSubSubModel
						.setModelPath(subModel.getModelPath() + "." + clonedSubSubModel.getName() + "[" + i + "]");
				clonedSubSubModel.setFieldPathMap(subModel.getFieldPathMap());

				setValueOfFields(clonedSubSubModel, subModel.getFieldPathMap(), subModel.getModelPathMap(),
						subSubJsonNode);
			}
		}

	}

	@Override
	protected void buildModel(Model sourceModel, Map<String, Field> fieldPathMap, Map<String, Model> modelPathMap,
			String nodeName, Object node) {
		// TODO Auto-generated method stub

	}

}
