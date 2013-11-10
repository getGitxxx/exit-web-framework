package org.exitsoft.showcase.service.foundation;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 系统自动调度类,负责在某些时间段中执行某些动作,在每个方法头都会加Scheduled注解,Scheduled注解中的cron含义为:
 * <pre>
 * "0 0 12 * * ?"    每天中午十二点触发
 * "0 15 10 * * ?"    每天早上10：15触发
 * "0 15 10 * * ? 2005"    2005年的每天早上10：15触发
 * "0 * 14 * * ?"    每天从下午2点开始到2点59分每分钟一次触发 
 * "0 0/5 14 * * ?"    每天从下午2点开始到2：55分结束每5分钟一次触发 
 * "0 0/5 14,18 * * ?"    每天的下午2点至2：55和6点至6点55分两个时间段内每5分钟一次触发 
 * "0 0-5 14 * * ?"    每天14:00至14:05每分钟一次触发
 * "0 10,44 14 ? 3 WED"    三月的每周三的14：10和14：44触发 
 * "0 15 10 ? * MON-FRI"    每个周一、周二、周三、周四、周五的10：15触发 
 * </pre>
 * @author vincent
 *
 */
@Component
public class SystemScheduled {

	private Logger logger = LoggerFactory.getLogger(SystemScheduled.class);
	
	//临时文件夹路径
	@Value("${file.upload.temp.path}")
	private String tempFilePath;

	/**
	 * 每天早上凌晨4点删除临时文件夹下所有文件
	 */
	@Scheduled(cron="0 55 15 * * ?")
	public void deleteTempUploadFile() {
		File tempDirectory = new File(tempFilePath);
		
		//如果获取的file对象不存在或者不是文件夹，就什么都不做
		if (!tempDirectory.exists() && !tempDirectory.isDirectory()) {
			logger.warn("找不临时文件夹或" + tempFilePath + "不是文件夹");
			return ;
		}

		logger.info("开始删除临时文件,位置:" + tempFilePath);
		
		try {
			//删除文件夹下所有文件
			FileUtils.cleanDirectory(tempDirectory);
		} catch (IOException e) {
			logger.error("删除临时文件过程中出错，原因:" + e.getMessage());
		}
		
		logger.info("完成删除除临时文件下的所有文件");
		
	}
	
}
