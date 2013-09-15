package org.exitsoft.common.freemarker.directive;

import java.io.IOException;
import java.util.Map;

import org.exitsoft.common.freemarker.TemplateDirectiveBodyOverrideWraper;
import org.exitsoft.common.freemarker.utils.DirectiveUtils;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 来自<a href="https://code.google.com/p/rapid-framework/">rapid-framework</a>的SuperDirective
 * 
 * 
 * @author badqiu
 *
 */
public class SuperDirective implements TemplateDirectiveModel {
	public final static String DIRECTIVE_NAME = "super";

	@SuppressWarnings("rawtypes")
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {

		TemplateDirectiveBodyOverrideWraper current = (TemplateDirectiveBodyOverrideWraper) env.getVariable(DirectiveUtils.OVERRIDE_CURRENT_NODE);
		if (current == null) {
			throw new TemplateException(
					"<@super/> direction must be child of override", env);
		}
		TemplateDirectiveBody parent = current.parentBody;
		if (parent == null) {
			throw new TemplateException("not found parent for <@super/>", env);
		}
		parent.render(env.getOut());

	}

}
