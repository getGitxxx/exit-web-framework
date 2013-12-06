package org.exitsoft.common.spring.mail;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class JavaMailService {
	
	private static Logger logger = LoggerFactory.getLogger(JavaMailService.class);
	
	private JavaMailSender mailSender;
	private Configuration freemarkerConfiguration;
	private String mailEncoding;
	
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void setFreemarkerConfiguration(Configuration freemarkerConfiguration) {
		this.freemarkerConfiguration = freemarkerConfiguration;
	}

	public void setMailEncoding(String mailEncoding) {
		this.mailEncoding = mailEncoding;
	}

	public void sendMailByTemplate(String sendTo,String sendFrom,String subject,
			String templateName,Map<String, File> attachment,Map<String, ?> model) throws IOException, TemplateException {
		sendMail(sendTo, sendFrom, subject, getTemplateString(templateName,model), attachment);
	}
	
	public void sendMail(String sendTo,String sendFrom,String subject,String content,Map<String, File> attachment) {
		
		try {
			MimeMessage msg = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(msg, true, mailEncoding);

			helper.setTo(sendTo);
			helper.setFrom(sendFrom);
			helper.setSubject(subject);
			helper.setText(content, true);

			if (!MapUtils.isEmpty(attachment)) {
				for (Entry<String, File> entry : attachment.entrySet()) {
					helper.addAttachment(entry.getKey(), entry.getValue());
				}
			}

			mailSender.send(msg);
			logger.info("邮件发送成功");
		} catch (MessagingException e) {
			logger.error("构造邮件失败", e);
		} catch (Exception e) {
			logger.error("发送邮件失败", e);
		}
	}
	
	private String getTemplateString(String templateName,Map<String, ?> model) throws IOException, TemplateException {
		Template template = freemarkerConfiguration.getTemplate(templateName, mailEncoding);
		return FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
	}
}
