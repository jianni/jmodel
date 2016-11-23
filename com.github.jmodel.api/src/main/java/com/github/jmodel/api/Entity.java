package com.github.jmodel.api;

import java.util.List;

public interface Entity extends Model {

	public List<Field> getFields();
	
	public void setFields(List<Field> fields);
	
	public boolean isUsed();
	
	public void setUsed(boolean used);

}
