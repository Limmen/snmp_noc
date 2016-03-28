/*
* Royal Institute of Technology
* 2016 (c) Kim Hammar Marcus Blom
*/
package kth.se.exjobb.view.view;

import kth.se.exjobb.util.GenericLogger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * LoginFilter that the servlet will issue before handling requests to /authorized/*
 *
 * @author Kim Hammar on 2016-03-25.
 */

@GenericLogger
public class LoginFilter implements Filter {

    /**
     * This method is called by the container each time a request is issued to
     * /authorized/* resources.
     * <p>
     * The method will check the http-session-parameters and then redirect the user
     * depending on it's permissions.
     *
     * @param servletRequest  HTTP-request that the server have received
     * @param servletResponse HTTP-response that the server will respond to the client.
     * @param chain           Can be used to invoke the next filter in the filter-chain.
     * @throws IOException      thrown when URL for redirection can not be found
     * @throws ServletException A general exception that the servlet-container can throw in case of
     *                          errors.
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse
            , FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;        
        if (request.getSession().getAttribute("username") == null) {
            response.sendRedirect(request.getContextPath() + "/login.xhtml");            
        }
        chain.doFilter(request, response);
    }

    /**
     * This method is called by the web container to indicate to the filter that
     * is it being placed into service.
     *
     * @param config optional initialization parameters
     * @throws ServletException A general exception that the servlet-container can throw in case of
     *                          errors.
     */
    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    /**
     * This method is called by the web container to indicate to a filter that it is being
     * taken out of service.
     */
    @Override
    public void destroy() {
    }
}