/**
 * Copyright (c) 2015, biezhi 王爵 (biezhi.me@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.blade.render;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import blade.kit.log.Logger;

import com.blade.Blade;
import com.blade.context.BladeWebContext;

/**
 * JSP Render, Default Render
 *
 * @author	<a href="mailto:biezhi.me@gmail.com" target="_blank">biezhi</a>
 * @since	1.0
 */
public final class JspRender implements Render {
	
	private static final Logger LOGGER = Logger.getLogger(JspRender.class);
	
	private String webRoot;
	
	public JspRender() {
		webRoot = Blade.me().webRoot();
	}
	
	@Override
	public void render(ModelAndView modelAndView, Writer writer) {
		HttpServletRequest servletRequest = BladeWebContext.request().raw();
		HttpServletResponse servletResponse = BladeWebContext.response().raw();
		
		try {
			Map<String, Object> model = modelAndView.getModel();
			String viewPath = webRoot + modelAndView.getView();
			
			if (null != model && !model.isEmpty()) {
				Set<String> keys = model.keySet();
				for (String key : keys) {
					servletRequest.setAttribute(key, model.get(key));
				}
			}
			servletRequest.getRequestDispatcher(viewPath).forward(servletRequest, servletResponse);
		} catch (ServletException e) {
			e.printStackTrace();
			LOGGER.error(e);
		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.error(e);
		}
	}
    
}
