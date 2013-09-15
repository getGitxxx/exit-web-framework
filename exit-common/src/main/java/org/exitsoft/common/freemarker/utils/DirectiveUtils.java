package org.exitsoft.common.freemarker.utils;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.exitsoft.common.freemarker.TemplateDirectiveBodyOverrideWraper;
import org.exitsoft.common.freemarker.directive.BlockDirective;
import org.exitsoft.common.freemarker.directive.ExtendsDirective;
import org.exitsoft.common.freemarker.directive.OverrideDirective;
import org.exitsoft.common.freemarker.directive.SuperDirective;

import freemarker.core.Environment;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModelException;

/**
 * 来自<a href="https://code.google.com/p/rapid-framework/">rapid-framework</a>的DirectiveUtils
 * 
 * 
 * @author badqiu
 *
 */
public class DirectiveUtils {
	public static String BLOCK = "__ftl_override__";
	public static String OVERRIDE_CURRENT_NODE = "__ftl_override_current_node";
	
	public static String getOverrideVariableName(String name) {
		return BLOCK + name;
	}
	
	public static void exposeRapidMacros(Configuration conf) {
		conf.setSharedVariable(BlockDirective.DIRECTIVE_NAME, new BlockDirective());
		conf.setSharedVariable(ExtendsDirective.DIRECTIVE_NAME, new ExtendsDirective());
		conf.setSharedVariable(OverrideDirective.DIRECTIVE_NAME, new OverrideDirective());
		conf.setSharedVariable(SuperDirective.DIRECTIVE_NAME, new SuperDirective());
	}
	
	@SuppressWarnings("rawtypes")
	public static String getRequiredParam(Map params,String key) throws TemplateException {
		Object value = params.get(key);
		if(value == null || StringUtils.isEmpty(value.toString())) {
			throw new TemplateModelException("not found required parameter:"+key+" for directive");
		}
		return value.toString();
	}
	
	@SuppressWarnings("rawtypes")
	public static String getParam(Map params,String key,String defaultValue) throws TemplateException {
		Object value = params.get(key);
		return value == null ? defaultValue : value.toString();
	}
	
	public static TemplateDirectiveBodyOverrideWraper getOverrideBody(Environment env, String name) throws TemplateModelException {
		TemplateDirectiveBodyOverrideWraper value = (TemplateDirectiveBodyOverrideWraper)env.getVariable(DirectiveUtils.getOverrideVariableName(name));
		return value;
	}
	
	public static void setTopBodyForParentBody(Environment env,
			TemplateDirectiveBodyOverrideWraper topBody,
			TemplateDirectiveBodyOverrideWraper overrideBody) {
		TemplateDirectiveBodyOverrideWraper parent = overrideBody;
		while(parent.parentBody != null) {
			parent = parent.parentBody;
		}
		parent.parentBody = topBody;
	}
}
