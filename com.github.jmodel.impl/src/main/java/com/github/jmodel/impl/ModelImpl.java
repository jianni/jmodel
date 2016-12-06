package com.github.jmodel.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.jmodel.api.Entity;
import com.github.jmodel.api.Field;
import com.github.jmodel.api.Model;

public abstract class ModelImpl implements Model {

	private String name;

	private boolean isRecursive;

	private String modelPath;

	private Model parentModel;

	private boolean isUsed = false;

	private List<Model> subModels = new ArrayList<Model>();

	private Map<String, Field> fieldPathMap = new HashMap<String, Field>();

	private Map<String, Model> modelPathMap = new HashMap<String, Model>();

	private Object targetBean;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isRecursive() {
		return isRecursive;
	}

	public void setRecursive(boolean isRecursive) {
		this.isRecursive = isRecursive;
	}

	public String getModelPath() {
		return modelPath;
	}

	public void setModelPath(String modelPath) {
		this.modelPath = modelPath;
	}

	public Model getParentModel() {
		return parentModel;
	}

	public void setParentModel(Model parentModel) {
		this.parentModel = parentModel;
	}

	public boolean isUsed() {
		return isUsed;
	}

	public void setUsed(boolean isUsed) {
		this.isUsed = isUsed;
	}

	public List<Model> getSubModels() {
		return subModels;
	}

	public void setSubModels(List<Model> subModels) {
		this.subModels = subModels;
	}

	public Model getSubModel(String modelName) {
		for (Model model : subModels) {
			if (model.getName().equals(modelName)) {
				return model;
			}
		}
		return null;
	}

	public Map<String, Field> getFieldPathMap() {
		return fieldPathMap;
	}

	public void setFieldPathMap(Map<String, Field> fieldPathMap) {
		this.fieldPathMap = fieldPathMap;
	}

	public Map<String, Model> getModelPathMap() {
		return modelPathMap;
	}

	public void setModelPathMap(Map<String, Model> modelPathMap) {
		this.modelPathMap = modelPathMap;
	}

	public Object getTargetBean() {
		return targetBean;
	}

	public void setTargetBean(Object targetBean) {
		this.targetBean = targetBean;
	}

	public void removeFieldByPath(String path) {
		Field field = fieldPathMap.get(path);
		Entity entity = field.getParentEntity();
		entity.getFields().remove(field);
	}

	public static void buildRelationForSubModel(final Model parentModel, final Model subModel) {
		// parentModel.getSubModels().add(subModel);
		subModel.setParentModel(parentModel);
		subModel.setFieldPathMap(parentModel.getFieldPathMap());
		subModel.setModelPathMap(parentModel.getModelPathMap());

		String parentModelPath = parentModel.getModelPath();
		if (subModel instanceof Entity) {
			subModel.setModelPath(parentModelPath.substring(0, parentModelPath.lastIndexOf("[")) + "["
					+ (parentModel.getSubModels().size() - 1) + "]");
			for (Field field : ((Entity) subModel).getFields()) {
				subModel.getFieldPathMap().put(subModel.getModelPath() + "." + field.getName(), field);
			}
		} else {
			subModel.setModelPath(parentModelPath + "." + subModel.getName() + "[]");
		}

		subModel.getModelPathMap().put(subModel.getModelPath(), subModel);

		List<Model> subSubModels = subModel.getSubModels();
		if (subSubModels != null) {
			for (Model subSubModel : subSubModels) {
				buildRelationForSubModel(subModel, subSubModel);
			}
		}
	}

	public abstract Model clone();

}
