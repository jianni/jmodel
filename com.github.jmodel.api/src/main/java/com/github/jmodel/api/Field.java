package com.github.jmodel.api;

public interface Field {

	public String getName();

	public void setName(String name);

	public String getValue();

	public void setValue(String value);

	public DataTypeEnum getDataType();

	public void setDataType(DataTypeEnum dataType);

	public String getFormatString();

	public void setFormatString(String formatString);

	public Entity getParentEntity();

	public void setParentEntity(Entity entity);

	public boolean isUsed();

	public void setUsed(boolean used);

	public Field clone();

}
