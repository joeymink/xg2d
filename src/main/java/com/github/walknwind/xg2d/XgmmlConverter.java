package com.github.walknwind.xg2d;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import com.github.walknwind.xg2d.xgmml.Attribute;
import com.github.walknwind.xg2d.xgmml.Edge;
import com.github.walknwind.xg2d.xgmml.Graph;
import com.github.walknwind.xg2d.xgmml.Node;

public class XgmmlConverter {
	private DotWriter dotWriter;

	public XgmmlConverter(OutputStream output)
	{
		dotWriter = new DotWriter(output);
	}
	
	public void convert(InputStream xgmmlInput)
	{
		try
		{
			XMLInputFactory inputFactory = XMLInputFactory.newInstance();
			XMLEventReader eventReader = inputFactory.createXMLEventReader(xgmmlInput);
			Graph xGraph;
			Node xNode = null;
			
			while(eventReader.hasNext())
			{
				XMLEvent event = eventReader.nextEvent();
				if (event.isStartElement())
				{
					StartElement startElement = event.asStartElement();
					if (startElement.getName().getLocalPart().equals("graph"))
					{
						xGraph = new Graph();
						Iterator<javax.xml.stream.events.Attribute> attributes = startElement.getAttributes();
						while (attributes.hasNext())
						{
							javax.xml.stream.events.Attribute attribute = attributes.next();
							String attrName = attribute.getName().toString();
							if (attrName.equals("label"))
								xGraph.setLabel(attribute.getValue());
							else if (attrName.equals("directed"))
								xGraph.setDirected(attribute.getValue());
						}
						dotWriter.write(xGraph);
					}
					else if (startElement.getName().getLocalPart().equals("node"))
					{
						xNode = new Node();
						Iterator<javax.xml.stream.events.Attribute> attributes = startElement.getAttributes();
						while (attributes.hasNext())
						{
							javax.xml.stream.events.Attribute attribute = attributes.next();
							if (attribute.getName().toString().equals("label"))
								xNode.setLabel(attribute.getValue());
							else if (attribute.getName().toString().equals("id"))
								xNode.setId(attribute.getValue());
						}
					}
					else if (startElement.getName().getLocalPart().equals("att"))
					{
						Attribute xAttribute = new Attribute();
						Iterator<javax.xml.stream.events.Attribute> attributes = startElement.getAttributes();
						while (attributes.hasNext())
						{
							javax.xml.stream.events.Attribute attribute = attributes.next();
							if (attribute.getName().toString().equals("name"))
								xAttribute.setName(attribute.getValue());
							else if (attribute.getName().toString().equals("type"))
								xAttribute.setType(attribute.getValue());
							else if (attribute.getName().toString().equals("value"))
								xAttribute.setValue(attribute.getValue());
						}
						xNode.addAttribute(xAttribute);
					}
					else if (startElement.getName().getLocalPart().equals("edge"))
					{
						Edge xEdge = new Edge();
						Iterator<javax.xml.stream.events.Attribute> attributes = startElement.getAttributes();
						while (attributes.hasNext())
						{
							javax.xml.stream.events.Attribute attribute = attributes.next();
							if (attribute.getName().toString().equals("label"))
								xEdge.setLabel(attribute.getValue());
							else if (attribute.getName().toString().equals("source"))
								xEdge.setSource(attribute.getValue());
							else if (attribute.getName().toString().equals("target"))
								xEdge.setTarget(attribute.getValue());
						}
						dotWriter.write(xEdge);
					}
				}
				else if (event.isEndElement())
				{
					EndElement endElement = event.asEndElement();
					String endElementName = endElement.getName().getLocalPart();
					if (endElementName.equals("node"))
					{
						dotWriter.write(xNode);
					}
				}
			}
			
			dotWriter.finish();
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
