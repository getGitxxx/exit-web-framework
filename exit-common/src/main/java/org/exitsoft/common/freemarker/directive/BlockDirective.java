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
 * 来自<a href="https://code.google.com/p/rapid-framework/">rapid-framework</a>的BlockDirective
 * 
 * 
 * @author badqiu
 *
 */
public class BlockDirective implements TemplateDirectiveModel {
	public final static String DIRECTIVE_NAME = "block";

	@SuppressWarnings("rawtypes")
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {

		String name = DirectiveUtils.getRequiredParam(params, "name");
		TemplateDirectiveBodyOverrideWraper overrideBody = DirectiveUtils.getOverrideBody(env, name);
		
		if (overrideBody == null) {
			if (body != null) {
				body.render(env.getOut());
			}
		} else {
			DirectiveUtils.setTopBodyForParentBody(env,new TemplateDirectiveBodyOverrideWraper(body, env),overrideBody);
			overrideBody.render(env.getOut());
		}
	}

}
