/*
* Royal Institute of Technology
* 2016 (c) Kim Hammar Marcus Blom
*/
package kth.se.exjobb.model;

import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Class that handles HTTPSessions.
 *
 * @author Kim Hammar on 2016-03-25.
 */
@Stateless
public class HttpSessionEJB
{

    FacesContext getFacesContext()
    {
        return FacesContext.getCurrentInstance();
    }

    /**
     * getSession
     * @return the http session of the user making the request.
     */
    public HttpSession getSession()
    {
        return (HttpSession) getFacesContext()
                .getExternalContext().getSession(false);
    }

    /**
     * getRequest
     * @return the current HTTP-request.
     */
    public HttpServletRequest getRequest()
    {
        return (HttpServletRequest) getFacesContext()
                .getExternalContext().getRequest();
    }

    /**
     * getUsername
     * @return username of the current HTTP-session.
     */
    public String getUserName()
    {
        HttpSession session = (HttpSession) getFacesContext()
                .getExternalContext().getSession(false);
        return session.getAttribute("username").toString();
    }

}
