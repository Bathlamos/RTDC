/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Olivier Clermont, Jonathan Ermel, Mathieu Fortin-Boulay, Philippe Legault & Nicolas Ménard
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package rtdc.web.server.config;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import rtdc.core.config.JavaIOConfig;
import rtdc.core.config.Reader;
import rtdc.core.model.User;
import rtdc.web.server.model.AuthTokenFactory;
import rtdc.web.server.model.AuthenticationToken;
import rtdc.web.server.model.UserFactory;

import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

public class ApiConfig extends ResourceConfig {

    private static final Logger LOGGER = Logger.getLogger(ApiConfig.class.getSimpleName());

    public ApiConfig(@Context ServletContext servletContext){

        final String outerPath = servletContext.getRealPath("WEB-INF" + File.separator + "classes" + File.separator);

        System.out.println(outerPath);

        JavaIOConfig.setReader(new Reader() {
            @Override
            public InputStream getContent(String path) throws IOException {
                return new FileInputStream(outerPath + path);
            }
        });

        packages("rtdc.web.server.servlet;rtdc.web.server.filter");


        register(RolesAllowedDynamicFeature.class);
        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bindFactory(UserFactory.class).to(User.class);
                bindFactory(AuthTokenFactory.class).to(AuthenticationToken.class);
            }
        });

        LOGGER.info("API Config loaded.");
    }
}
