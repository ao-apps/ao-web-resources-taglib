/*
 * ao-web-resources-taglib - Web resource management in a JSP environment.
 * Copyright (C) 2021, 2022  AO Industries, Inc.
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
module com.aoapps.web.resources.taglib {
  exports com.aoapps.web.resources.taglib;
  // Direct
  requires com.aoapps.html.servlet; // <groupId>com.aoapps</groupId><artifactId>ao-fluent-html-servlet</artifactId>
  requires com.aoapps.lang; // <groupId>com.aoapps</groupId><artifactId>ao-lang</artifactId>
  requires com.aoapps.web.resources.registry; // <groupId>com.aoapps</groupId><artifactId>ao-web-resources-registry</artifactId>
  requires com.aoapps.web.resources.renderer; // <groupId>com.aoapps</groupId><artifactId>ao-web-resources-renderer</artifactId>
  requires com.aoapps.web.resources.servlet; // <groupId>com.aoapps</groupId><artifactId>ao-web-resources-servlet</artifactId>
  requires javax.servlet.api; // <groupId>javax.servlet</groupId><artifactId>javax.servlet-api</artifactId>
  requires javax.servlet.jsp.api; // <groupId>javax.servlet.jsp</groupId><artifactId>javax.servlet.jsp-api</artifactId>
} // TODO: Avoiding rewrite-maven-plugin-4.22.2 truncation
