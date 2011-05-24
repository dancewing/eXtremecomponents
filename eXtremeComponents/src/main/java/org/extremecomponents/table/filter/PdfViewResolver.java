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
package org.extremecomponents.table.filter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.avalon.framework.logger.ConsoleLogger;
import org.apache.avalon.framework.logger.Logger;
import org.apache.fop.apps.Driver;
import org.apache.fop.apps.Options;
import org.apache.fop.messaging.MessageHandler;
import org.extremecomponents.table.core.Preferences;
import org.xml.sax.InputSource;

/**
 * @author Jeff Johnston
 */
public class PdfViewResolver implements ViewResolver {
    private Logger log = null;
    private final static String USERCONFIG_LOCATION = "exportPdf.userconfigLocation";

    public void resolveView(ServletRequest request, ServletResponse response, Preferences preferences, Object viewData) throws Exception {
        InputStream is = new ByteArrayInputStream(((String) viewData).getBytes("UTF-8"));

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        Driver driver = new Driver(new InputSource(is), out);

        if (log == null) {
            log = new ConsoleLogger(ConsoleLogger.LEVEL_WARN);
            MessageHandler.setScreenLogger(log);
        }
        
        String userconfigLocation = preferences.getPreference(USERCONFIG_LOCATION);
        if (userconfigLocation != null) {
            InputStream input = this.getClass().getResourceAsStream(userconfigLocation);
            if (input != null) {
                new Options(input);
            }
        }
        
        driver.setLogger(log);
        driver.setRenderer(Driver.RENDER_PDF);
        driver.run();

        byte[] contents = out.toByteArray();
        response.setContentLength(contents.length);
        response.getOutputStream().write(contents);
    }
}
