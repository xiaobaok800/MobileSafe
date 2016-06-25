package com.example.mobilesafe.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 读取流的工具类
 * @author Administrator
 *
 */
public class StreamUtil {
	/**
	 * 将流转换成字符串
	 * @param in
	 * @return
	 * @throws IOException 
	 */
	public static String readFromStream(InputStream in) throws IOException{
		ByteArrayOutputStream out=new ByteArrayOutputStream();
		int lenght;
		byte[] buffer=new byte[1024];
		while ((lenght=in.read(buffer))!=-1) {
			out.write(buffer, 0, lenght);
		}
		String reslut=out.toString();
		in.close();
		out.close();
		return reslut;
	}
}
