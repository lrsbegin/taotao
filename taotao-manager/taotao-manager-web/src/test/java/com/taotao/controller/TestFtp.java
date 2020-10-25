package com.taotao.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;

import com.taotao.common.utils.FtpUtil;

public class TestFtp {
	
	@Test
	public void testFtpClient() throws Exception {
		//创建一个FTPclient对象
		FTPClient ftpClient = new FTPClient();
		//创建ftp链接。默认端口21。
		ftpClient.connect("192.168.137.130", 21);
		//登录ftp服务器，使用用户名和密码
		ftpClient.login("ftpuser", "ftpuser");
		//上传文件
		//读取本地文件
		FileInputStream inputStream = new FileInputStream(new File("C:\\Users\\Admin\\Pictures\\images\\57169317.jpg"));
		//设置上传的路径
		ftpClient.changeWorkingDirectory("/home/ftpuser/www/images");
		//修改文件上传格式
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		//第一个参数：服务器文档名
		//第二个参数：上传文档的inputStream
		ftpClient.storeFile("hello.jpg", inputStream);
		//关闭连接
		ftpClient.logout();
	}

	@Test
	public void testUtils() throws Exception{
		FileInputStream inputStream = new FileInputStream(new File("C:\\Users\\Admin\\Pictures\\images\\57169317.jpg"));
		FtpUtil.uploadFile("192.168.137.130", 21, "ftpuser", "ftpuser", "/home/ftpuser/www/images", "2020/09/26", "hello.jpg", inputStream);
	}
}
