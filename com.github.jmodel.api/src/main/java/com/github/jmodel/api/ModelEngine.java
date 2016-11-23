package com.github.jmodel.api;

import java.util.Locale;

public interface ModelEngine {

	/**
	 * Build a generic model.
	 * 
	 * @param sourceObj
	 * @param format
	 * @return
	 */
	public Model construct(Object sourceObj, FormatEnum format);

	public Model construct(Object sourceObj, FormatEnum format, Locale currentLocale);

	/**
	 * Set the value of fields for an existing generic model.
	 * 
	 * @param sourceObj
	 * @param format
	 * @return
	 */
	public Model fill(Model sourceModel, Object sourceObj, FormatEnum format);

	public Model fill(Model sourceModel, Object sourceObj, FormatEnum format, Locale currentLocale);

}
