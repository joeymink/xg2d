package com.github.walknwind.xg2d;

import java.io.OutputStream;
import java.io.PrintStream;

import com.github.walknwind.xg2d.xgmml.Attribute;
import com.github.walknwind.xg2d.xgmml.Edge;
import com.github.walknwind.xg2d.xgmml.Graph;
import com.github.walknwind.xg2d.xgmml.Node;

public class DotWriter {

	private PrintStream output;
	private boolean isDirected = false;

	public DotWriter(OutputStream output)
	{
		this.output = new PrintStream(output);
	}
	
	void write(Object xgmmlObj)
	{
		if (xgmmlObj instanceof Graph)
		{
			Graph xGraph = (Graph)xgmmlObj;
			
			if(xGraph.getDirected().equals("1"))
				isDirected = true;
			
			if (isDirected)
				output.print("digraph");
			else output.print("graph");
			output.println(" graphname" /*+ xGraph.getLabel()*/ + " {"); // TODO: figure out graph label issue
		}
		else if (xgmmlObj instanceof Node)
		{
			Node xNode = (Node)xgmmlObj;
			output.print(xNode.getId() + " [label=\"" + xNode.getLabel() + "\"");
			if (xNode.getAttributes() != null)
			{
				for (Attribute xAttr : xNode.getAttributes())
				{
					output.print(" " + xAttr.getName() + "=\"" + xAttr.getValue() + "\"");
				}
			}
			output.println("];");
		}
		else if (xgmmlObj instanceof Edge)
		{
			Edge xEdge = (Edge)xgmmlObj;
			output.println(
					xEdge.getSource()
					+ " " + (isDirected?"->":"--")
					+ " " + xEdge.getTarget()
					+ " [label=\"" + xEdge.getLabel() + "\"];");
		}
	}
	
	public void finish()
	{
		output.println("}");
	}
}
