package com.taotao.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.taotao.common.utils.FtpUtil;
import com.taotao.common.utils.IDUtils;
import com.taotao.service.PictureService;

/**
 * 图片上传服务
 * @author Admin
 *
 */
@Service
public class PictureServiceImpl implements PictureService {
	
	@Value("${FTP_ADDRESS}")
	private String FTP_ADDRESS;		//服务器IP地址
	@Value("${FTP_PORT}")	
	private Integer FTP_PORT;		//ftp
	@Value("${FTP_USER_NAME}")
	private String FTP_USER_NAME;	//用户名
	@Value("${FTP_PASSWORD}")
	private String FTP_PASSWORD;	//密码
	@Value("${FTP_BASE_PATH}")
	private String FTP_BASE_PATH;	//根路径
	@Value("${IMAGE_BASE_URL}")
	private String IMAGE_BASE_URL;	//图片服务器基础url
	

	@Override
	public Map UploadPicture(MultipartFile uploadFile) {
		Map resultMap = new HashMap<>();
		try {
			//生成一个新的文件名
			//取原始文件名
			String oldName = uploadFile.getOriginalFilename();
			//生成新文件名
			//UUID.randomUUID();
			String newName = IDUtils.genImageName();
			newName = newName + oldName.substring(oldName.lastIndexOf("."));
			//图片上传
			//文件路径
			String imagePath = new DateTime().toString("/yyyy/MM/dd");
			boolean result = FtpUtil.uploadFile(FTP_ADDRESS, FTP_PORT, FTP_USER_NAME, FTP_PASSWORD, FTP_BASE_PATH,
					imagePath, newName, uploadFile.getInputStream());
			//返回结果
			if(!result) {
				resultMap.put("error", 1);
				resultMap.put("message", "文件上传失败");
				return resultMap;
			}
			resultMap.put("error", 0);
			resultMap.put("url", IMAGE_BASE_URL + imagePath + "/" + newName);
			return resultMap;
			
		} catch (Exception e) {
			resultMap.put("error", 1);
			resultMap.put("message", "文件上传发生异常");
			return resultMap;
		}
	}

}
