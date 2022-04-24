/*
 * ao-web-resources-taglib - Web resource management in a JSP environment.
 * Copyright (C) 2020, 2021, 2022  AO Industries, Inc.
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

package com.aoapps.web.resources.taglib.book;

import com.aoapps.lang.validation.ValidationException;
import com.semanticcms.tagreference.TagReferenceInitializer;

public class AoWebResourcesTldInitializer extends TagReferenceInitializer {

  public AoWebResourcesTldInitializer() throws ValidationException {
    super(
        Maven.properties.getProperty("documented.name") + " Reference",
        "Taglib Reference",
        "/web-resources/taglib",
        "/ao-web-resources.tld",
        true,
        Maven.properties.getProperty("documented.javadoc.link.javase"),
        Maven.properties.getProperty("documented.javadoc.link.javaee"),
        // Self
        "com.aoapps.web.resources.taglib", Maven.properties.getProperty("project.url") + "apidocs/com.aoapps.web.resources.taglib/"
    );
  }
}
