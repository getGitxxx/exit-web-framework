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

public class OverrideDirective implements TemplateDirectiveModel {
	public final static String DIRECTIVE_NAME = "override";

	@SuppressWarnings("rawtypes")
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		
		String name = DirectiveUtils.getRequiredParam(params, "name");
		String overrideVariableName = DirectiveUtils.getOverrideVariableName(name);

		TemplateDirectiveBodyOverrideWraper override = DirectiveUtils.getOverrideBody(env, name);
		TemplateDirectiveBodyOverrideWraper current = new TemplateDirectiveBodyOverrideWraper(body, env);
		
		if (override == null) {
			env.setVariable(overrideVariableName, current);
		} else {
			DirectiveUtils.setTopBodyForParentBody(env, current, override);
		}
	}
}
