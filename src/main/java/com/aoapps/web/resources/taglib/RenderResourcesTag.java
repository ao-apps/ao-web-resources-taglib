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

import com.aoapps.lang.Strings;
import com.aoapps.lang.io.NullWriter;
import com.aoapps.web.resources.registry.Group;
import com.aoapps.web.resources.registry.Registry;
import com.aoapps.web.resources.servlet.RegistryEE;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * Renders {@link Resource} tags for the currently active styles.
 */
abstract class RenderResourcesTag extends SimpleTagSupport {

  private boolean application;

  public void setApplication(boolean application) {
    this.application = application;
  }

  private boolean session = true;

  public void setSession(boolean session) {
    this.session = session;
  }

  private boolean request = true;

  public void setRequest(boolean request) {
    this.request = request;
  }

  private boolean page = true;

  public void setPage(boolean page) {
    this.page = page;
  }

  private boolean registered = true;

  public void setRegistered(boolean registered) {
    this.registered = registered;
  }

  private Set<Group.Name> activate;

  /**
   * Sets the comma and/or space-separated set of groups to activate.
   */
  public void setActivate(String activate) {
    if (activate == null) {
      this.activate = null;
    } else {
      Set<Group.Name> newActivates = new HashSet<>();
      for (String name : Strings.splitCommaSpace(activate)) {
        Group.Name group = new Group.Name(name);
        // Activations take priority over deactivate when set in both attributes
        if (deactivate != null
            && deactivate.remove(group)
            && deactivate.isEmpty()
        ) {
          deactivate = null;
        }
        newActivates.add(group);
      }
      this.activate = newActivates.isEmpty() ? null : newActivates;
    }
  }

  private Set<Group.Name> deactivate;

  /**
   * Sets the comma and/or space-separated set of groups to deactivate.
   */
  public void setDeactivate(String daeactivate) {
    if (daeactivate == null) {
      this.deactivate = null;
    } else {
      Set<Group.Name> newDeactivates = new HashSet<>();
      for (String name : Strings.splitCommaSpace(daeactivate)) {
        Group.Name group = new Group.Name(name);
        // Activations take priority over deactivate when set in both attributes
        if (activate == null || !activate.contains(group)) {
          newDeactivates.add(group);
        }
      }
      this.deactivate = newDeactivates.isEmpty() ? null : newDeactivates;
    }
  }

  @Override
  public void doTag() throws JspException, IOException {
    JspFragment body = getJspBody();
    if (body != null) {
      // Invoke body for any meta data, but discard any output
      body.invoke(NullWriter.getInstance());
    }

    Map<Group.Name, Boolean> activations;
    if (activate == null && deactivate == null) {
      activations = null;
    } else {
      activations = new HashMap<>();
      if (activate != null) {
        for (Group.Name group : activate) {
          assert !activations.containsKey(group);
          activations.put(group, true);
        }
      }
      if (deactivate != null) {
        for (Group.Name group : deactivate) {
          assert !activations.containsKey(group);
          activations.put(group, false);
        }
      }
    }

    PageContext pageContext = (PageContext) getJspContext();
    ServletContext servletContext = pageContext.getServletContext();
    HttpServletRequest httpRequest = (HttpServletRequest) pageContext.getRequest();

    doTag(
        registered,
        activations,
        application ? RegistryEE.Application.get(servletContext)            : null,
        session     ? RegistryEE.Session.get(httpRequest.getSession(false)) : null,
        request     ? RegistryEE.Request.get(servletContext, httpRequest)   : null,
        page        ? RegistryEE.Page.get(httpRequest)                      : null
    );
  }

  protected abstract void doTag(boolean registered, Map<Group.Name, Boolean> activations, Registry... registries) throws JspException, IOException;
}
