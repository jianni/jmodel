package com.github.jmodel.impl;

import com.github.jmodel.api.DataTypeEnum;
import com.github.jmodel.api.Entity;
import com.github.jmodel.api.Field;

public class FieldImpl implements Field {

	private String name;

	private String value;

	private DataTypeEnum dataType;

	private String formatString;

	private boolean isUsed = false;

	private Entity parentEntity;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
		setUsed(true);
	}

	public DataTypeEnum getDataType() {
		return dataType;
	}

	public void setDataType(DataTypeEnum dataType) {
		this.dataType = dataType;
	}

	public String getFormatString() {
		return formatString;
	}

	public void setFormatString(String formatString) {
		this.formatString = formatString;
	}

	public void setParentEntity(Entity parentEntity) {
		this.parentEntity = parentEntity;
	}

	public Entity getParentEntity() {
		return parentEntity;
	}

	public boolean isUsed() {
		return isUsed;
	}

	public void setUsed(boolean isUsed) {
		this.isUsed = isUsed;
	}

	public Field clone() {
		Field clonedField = new FieldImpl();
		clonedField.setName(this.getName() + "");
//		clonedField.setDataType(this.getDataType());
//		clonedField.setFormatString(this.getFormatString() + "");

		return clonedField;
	}

	@Override
	public String toString() {
		return "[name:" + name + ",value=" + value + "]";
	}

}
