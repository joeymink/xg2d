package com.github.walknwind.xg2d;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main {
	public static void main(String[] args)
	{
		System.out.println("urr?");
		if (args.length != 1)
		{
			System.err.println("Must provide a single command line argument - path to an xgmml file to convert.");
			System.exit(1);
		}

		File file = new File(args[0]);
		if (!file.exists() || !file.isFile())
		{
			System.err.println(args[0] + "does not exist or is not a file.");
			System.exit(1);
		}

		File outputFile = new File(file.getParent() + File.separator + file.getName() + ".dot");
		
		FileOutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(outputFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		XgmmlConverter converter = new XgmmlConverter(outputStream);
		try {
			System.out.println("converting xgmml file...");
			converter.convert(new FileInputStream(file));
			outputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("xgmml file converted!");
	}
}
