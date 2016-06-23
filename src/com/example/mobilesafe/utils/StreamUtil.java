package com.example.mobilesafe.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * ��ȡ���Ĺ�����
 * @author Administrator
 *
 */
public class StreamUtil {
	/**
	 * ����ת�����ַ���
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
