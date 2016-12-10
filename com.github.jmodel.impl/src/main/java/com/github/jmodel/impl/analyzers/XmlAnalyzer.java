package com.github.jmodel.impl.analyzers;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.github.jmodel.api.Field;
import com.github.jmodel.api.IllegalException;
import com.github.jmodel.api.Model;
import com.github.jmodel.impl.AbstractAnalyzer;

public class XmlAnalyzer extends AbstractAnalyzer<Element> {

	public <T> Model process(Model sourceModel, T sourceObject, Boolean isConstruction) {
		try {
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			Document document;
			if (sourceObject instanceof String) {
				document = builder.parse(new ByteArrayInputStream(((String) sourceObject).getBytes()));
			} else if (sourceObject instanceof InputStream) {
				document = builder.parse((InputStream) sourceObject);
			} else {
				throw new IllegalException("xxxx");
			}
			build(sourceModel, new HashMap<String, Field>(), new HashMap<String, Model>(),
					document.getDocumentElement(), isConstruction);
			return sourceModel;
		} catch (Exception e) {
			throw new IllegalException("xxxxx");
		}
	}

	@Override
	protected void setFieldValue(Element node, Field field) {
		Element fieldElement = getSubNode(node, field.getName());
		if (fieldElement != null) {
			field.setValue(fieldElement.getTextContent());
		}
	}

	@Override
	protected Element getSubNode(Element node, String subNodeName) {
		for (Node n = node.getFirstChild(); n != null; n = n.getNextSibling()) {
			if (n.getNodeType() == Node.ELEMENT_NODE && ((Element) n).getTagName().equals(subNodeName)) {
				return (Element) n;
			}
		}
		return null;
	}

	@Override
	protected void populateSubModel(Element subNode, Model subModel, Model subSubModel) {
		try {
			Node parentNode = subNode.getParentNode();
			XPath xpath = XPathFactory.newInstance().newXPath();
			XPathExpression expr1 = xpath.compile("//" + parentNode.getNodeName() + "/" + subModel.getName());
			NodeList childrenNode = (NodeList) expr1.evaluate(parentNode, XPathConstants.NODESET);

			int k = -1;
			for (int j = 0; j < childrenNode.getLength(); j++) {
				Node subSubNode = childrenNode.item(j);

				k++;
				Model clonedSubSubModel = null;
				if (k == 0) {
					clonedSubSubModel = subSubModel;
				} else {
					clonedSubSubModel = subSubModel.clone();
					subModel.getSubModels().add(clonedSubSubModel);
				}
				clonedSubSubModel
						.setModelPath(subModel.getModelPath() + "." + clonedSubSubModel.getName() + "[" + k + "]");
				clonedSubSubModel.setFieldPathMap(subModel.getFieldPathMap());

				setValueOfFields(clonedSubSubModel, subModel.getFieldPathMap(), subModel.getModelPathMap(),
						(Element) subSubNode);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void buildModel(Model sourceModel, Map<String, Field> fieldPathMap, Map<String, Model> modelPathMap,
			String nodeName, Element node) {
		// TODO Auto-generated method stub

	}

}
