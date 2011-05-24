/*
 * Copyright 2004 original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.extremecomponents.table.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.extremecomponents.table.context.Context;

/**
 * @author Jeff Johnston
 */
public class TableProperties implements Preferences {
    private static Log logger = LogFactory.getLog(TableProperties.class);

    private static TableProperties instance;

    public final static String EXTREMETABLE_PROPERTIES = "extremetable.properties";

    private Properties properties = new Properties();

    public void init(Context context, String preferencesLocation) {
        try {
            properties.load(this.getClass().getResourceAsStream(EXTREMETABLE_PROPERTIES));
            if (StringUtils.isNotBlank(preferencesLocation)) {
                InputStream input = this.getClass().getResourceAsStream(preferencesLocation);
                if (input != null) {
                    properties.load(input);
                }
            }
        } catch (IOException e) {
            if (logger.isErrorEnabled()) {
                logger.error("Could not load the eXtremeTable preferences.", e);
            }
        }
    }

    /**
     * Get the default property.
     */
    public String getPreference(String name) {
        return (String) properties.get(name);
    }

    public static TableProperties getInstance(Context context, String preferencesLocation) {
        if (instance == null) {
            instance = new TableProperties();
            instance.init(context, preferencesLocation);
        }
        return instance;
    }
}
