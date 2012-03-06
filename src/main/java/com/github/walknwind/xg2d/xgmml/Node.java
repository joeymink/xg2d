package com.github.walknwind.xg2d.xgmml;

import java.util.ArrayList;
import java.util.Collection;

public class Node {
	private String label;
	private String id;
	private Collection<Attribute> attributes = new ArrayList<Attribute>();

	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Collection<Attribute> getAttributes() {
		return attributes;
	}
	public void setAttributes(Collection<Attribute> attributes) {
		this.attributes = attributes;
	}
	public void addAttribute(Attribute xAttribute) {
		attributes.add(xAttribute);
	}
}
