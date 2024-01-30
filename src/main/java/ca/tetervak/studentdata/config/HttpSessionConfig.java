package ca.tetervak.studentdata.config;

import jakarta.servlet.http.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Based on the example created by JavaDeveloperZone on 13-11-2017.
 */
@Configuration
@Slf4j
public class HttpSessionConfig {

    @Bean
    public HttpSessionListener httpSessionListener() {
        return new HttpSessionListener() {

            @Override
            public void sessionCreated(HttpSessionEvent se) {
                // This method will be called when session created
                log.trace("sessionCreated() is called");
                log.debug("newSessionId = " + se.getSession().getId());
            }

            @Override
            public void sessionDestroyed(HttpSessionEvent se) {
                // This method will be automatically called when session destroyed
                log.trace("sessionDestroyed() is called");
                log.debug("destroyedSessionId = " + se.getSession().getId());
            }
        };
    }

    @Bean
    public HttpSessionAttributeListener httpSessionAttributeListener() {
        return new HttpSessionAttributeListener() {
            @Override
            public void attributeAdded(HttpSessionBindingEvent se) {
                // This method will be automatically called when session attribute added
                log.trace("attributeAdded() is called");
                log.debug("sessionId = " + se.getSession().getId());
                log.debug("addedAttributeName = " + se.getName());
                log.debug("addedAttributeValue = " + se.getValue());
            }

            @Override
            public void attributeRemoved(HttpSessionBindingEvent se) {
                // This method will be automatically called when session attribute removed
                log.trace("attributeRemoved() is called");
                log.debug("sessionId = " + se.getSession().getId());
            }

            @Override
            public void attributeReplaced(HttpSessionBindingEvent se) {
                // This method will be automatically called when session attribute replace
                log.trace("attributeReplaced() is called");
                HttpSession session = se.getSession();
                String attributeName = se.getName();
                log.debug("sessionId = " + session.getId());
                log.debug("replacedAttributeName = " + attributeName);
                log.debug("replacedAttributeOldValue = " + se.getValue());
                log.debug("replacedAttributeNewValue = " + session.getAttribute(attributeName));
            }
        };
    }
}