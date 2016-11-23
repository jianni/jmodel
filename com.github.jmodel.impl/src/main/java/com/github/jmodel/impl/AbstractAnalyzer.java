package com.github.jmodel.impl;

import java.util.List;
import java.util.Map;

import com.github.jmodel.api.Analyzer;
import com.github.jmodel.api.Array;
import com.github.jmodel.api.Entity;
import com.github.jmodel.api.Field;
import com.github.jmodel.api.Model;

public abstract class AbstractAnalyzer<T> implements Analyzer {

	protected void build(final Model sourceModel, final Map<String, Field> fieldPathMap,
			final Map<String, Model> modelPathMap, final T node, Boolean isConstruction) {
		if (isConstruction) {
			buildModel(sourceModel, fieldPathMap, modelPathMap, "", node);
		} else {
			setValueOfFields(sourceModel, fieldPathMap, modelPathMap, node);
		}
	}

	/**
	 * Set the value of fields for a model.
	 * 
	 * @param sourceModel
	 * @param fieldPathMap
	 * @param modelPathMap
	 * @param node
	 */
	protected void setValueOfFields(final Model sourceModel, final Map<String, Field> fieldPathMap,
			final Map<String, Model> modelPathMap, final T node) {
		if (sourceModel != null) {
			sourceModel.setModelPathMap(modelPathMap);
			sourceModel.setFieldPathMap(fieldPathMap);
			if (sourceModel instanceof Entity) {
				if (sourceModel.getModelPath() == null) {
					sourceModel.setFieldPathMap(fieldPathMap);
					sourceModel.setModelPath(sourceModel.getName());
				}

				List<Field> fields = ((Entity) sourceModel).getFields();
				if (fields != null) {
					for (Field field : fields) {
						setFieldValue(node, field);
						sourceModel.getFieldPathMap().put(sourceModel.getModelPath() + "." + field.getName(), field);
					}
				}
			}

			modelPathMap.put(sourceModel.getModelPath(), sourceModel);

			List<Model> subModels = sourceModel.getSubModels();
			if (subModels != null) {
				for (Model subModel : subModels) {
					subModel.setFieldPathMap(fieldPathMap);
					if (subModel instanceof Array) {
						subModel.setModelPath(sourceModel.getModelPath() + "." + subModel.getName() + "[]");
					} else {
						subModel.setModelPath(sourceModel.getModelPath() + "." + subModel.getName());
					}

					T subNode = getSubNode(node, subModel.getName());

					if (subModel instanceof Entity) {
						if (subNode != null) {
							setValueOfFields(subModel, fieldPathMap, modelPathMap, subNode);
						}
					} else if (subModel instanceof Array) {
						modelPathMap.put(subModel.getModelPath(), subModel);
						subModel.setModelPathMap(modelPathMap);
						subModel.setFieldPathMap(fieldPathMap);
						List<Model> subSubModels = subModel.getSubModels();

						if (subSubModels != null) {
							Model subSubModel = subSubModels.get(0);
							populateSubModel(subNode, subModel, subSubModel);
						}
					}
				}
			}
		}
	}

	/**
	 * Model will be built base on source object.
	 * 
	 * @param sourceModel
	 * @param fieldPathMap
	 * @param modelPathMap
	 * @param node
	 */
	protected abstract void buildModel(final Model sourceModel, final Map<String, Field> fieldPathMap,
			final Map<String, Model> modelPathMap, final String nodeName, final T node);

	/**
	 * Different ways to set value for different model, e.g. JSON, XML
	 * 
	 * @param sourceNode
	 * @param field
	 */
	protected abstract void setFieldValue(final T sourceNode, final Field field);

	protected abstract T getSubNode(final T node, final String subNodeName);

	protected abstract void populateSubModel(final T subNode, final Model subModel, final Model subSubModel);

}
