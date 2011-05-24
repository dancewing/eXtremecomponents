/*
 * Copyright 2004 original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.extremecomponents.table.bean;

import java.util.HashMap;
import java.util.Map;

/*
 * @author Jeff Johnston
 */
public abstract class Attributes {
    private Map attr = new HashMap();

    public Object getAttribute(String key) {
        return attr.get(key);
    }

    public String getAttributeAsString(String key) {
        Object value = attr.get(key);
        if (value != null) {
            return String.valueOf(value);
        }

        return null;
    }

    public int getAttributeAsInt(String key) {
        Object value = attr.get(key);
        if (value != null) {
            return Integer.parseInt(String.valueOf(value));
        }

        return 0;
    }

    public void addAttribute(String key, Object value) {
        attr.put(key, value);
    }
}
