package com.github.jmodel.api;

public interface Analyzer {

	public <T> Model process(Model sourceModel, T sourceObject, Boolean isConstruction);
}
