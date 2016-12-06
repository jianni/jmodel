package com.github.jmodel.api;

import java.util.List;
import java.util.Map;

public interface Model {

	public String getName();
	
	public void setName(String name);
	
	public boolean isRecursive();
	
	public void setRecursive(boolean isRecursive);

	public Model getParentModel();
	
	public void setParentModel(Model parentModel);

	public String getModelPath();

	public void setModelPath(String modelPath);

	public Map<String, Field> getFieldPathMap();

	public void setFieldPathMap(Map<String, Field> fieldPathMap);

	public Map<String, Model> getModelPathMap();

	public void setModelPathMap(Map<String, Model> modelPathMap);

	public List<Model> getSubModels();
	
	public void setSubModels(List<Model> subModels);
	
	public Object getTargetBean();
	
	public void setTargetBean(Object targetBean);

	public Model clone();
}
