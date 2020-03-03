/*
 * ao-web-resources-taglib - Web resource management in a JSP environment.
 * Copyright (C) 2020  AO Industries, Inc.
 *     support@aoindustries.com
 *     7262 Bull Pen Cir
 *     Mobile, AL 36695
 *
 * This file is part of ao-web-resources-taglib.
 *
 * ao-web-resources-taglib is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * ao-web-resources-taglib is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with ao-web-resources-taglib.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.aoindustries.web.resources.taglib;

import com.aoindustries.html.servlet.HtmlEE;
import com.aoindustries.io.NullWriter;
import com.aoindustries.util.StringUtility;
import com.aoindustries.web.resources.registry.Group;
import com.aoindustries.web.resources.renderer.Renderer;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class StylesTag extends SimpleTagSupport {

	private boolean global = true;
	public void setGlobal(boolean global) {
		this.global = global;
	}

	private Set<String> groups;
	public void setGroups(String groups) {
		if(groups == null) {
			this.groups = null;
		} else {
			this.groups = new LinkedHashSet<>(StringUtility.splitStringCommaSpace(groups));
		}
	}

	// TODO: Nested GroupTag
	public void addGroup(String group) {
		if(group != null) {
			if(groups == null) groups = new LinkedHashSet<>();
			groups.add(Group.checkName(group));
		}
	}

	private String indent;
	public void indent(String indent) {
		this.indent = StringUtility.nullIfEmpty(indent);
	}

	@Override
	public void doTag() throws JspException, IOException {
		JspFragment body = getJspBody();
		if(body != null) {
			// Invoke body for any meta data, but discard any output
			body.invoke(NullWriter.getInstance());
		}

		// Add global group, when enabled
		if(global) addGroup(Group.GLOBAL);

		PageContext pageContext = (PageContext)getJspContext();
		ServletContext servletContext = pageContext.getServletContext();
		HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();

		Renderer.get(servletContext).renderStyles(
			request,
			(HttpServletResponse)pageContext.getResponse(),
			HtmlEE.get(servletContext, request, pageContext.getOut()),
			groups,
			indent
		);
	}
}
