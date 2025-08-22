/*
 * ao-web-resources-taglib - Web resource management in a JSP environment.
 * Copyright (C) 2020, 2021, 2022, 2023  AO Industries, Inc.
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
 * along with ao-web-resources-taglib.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.aoapps.web.resources.taglib;

import com.aoapps.html.servlet.DocumentEE;
import com.aoapps.web.resources.registry.Group;
import com.aoapps.web.resources.registry.Registry;
import com.aoapps.web.resources.renderer.Renderer;
import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * Renders the <code>&lt;link&gt;</code> tags for the currently active styles.
 */
public class RenderStylesTag extends RenderResourcesTag {

  @Override
  protected void doTag(boolean registered, Map<Group.Name, Boolean> activations, Registry... registries) throws JspException, IOException {
    PageContext pageContext = (PageContext) getJspContext();
    ServletContext servletContext = pageContext.getServletContext();
    HttpServletRequest httpRequest = (HttpServletRequest) pageContext.getRequest();
    HttpServletResponse httpResponse = (HttpServletResponse) pageContext.getResponse();

    Renderer.get(servletContext).renderStyles(
        httpRequest,
        httpResponse,
        new DocumentEE(
            servletContext,
            httpRequest,
            httpResponse,
            pageContext.getOut(),
            false, // Do not add extra newlines to JSP
            false  // Do not add extra indentation to JSP
        ),
        registered,
        activations,
        registries
    );
  }
}
