package com.example.mywork.util;

import java.io.IOException;
import java.io.InputStream;

public class IOManager {

	/**
	 * 			InputStream 2 String
	 * @param in
	 * @return
	 */
	public static String Stream2String ( InputStream in )
	{
		StringBuffer buffer = new StringBuffer();
		int n = 0;
		try {
			byte[] b = new byte[1024];
			while ((n = in.read(b)) != -1 )
			{
				buffer.append(new String(b, 0, n));
			}
			in.close();
			in = null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return buffer.toString().trim();
	}
	
}
