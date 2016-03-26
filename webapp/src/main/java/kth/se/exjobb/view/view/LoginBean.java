package kth.se.exjobb.view.view;

import kth.se.exjobb.controller.Controller;
import kth.se.exjobb.util.GenericLogger;
import kth.se.exjobb.util.LogManager;

import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.Size;
import java.io.IOException;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;

/**
 * Managed bean representing the interface between the login page and the server.
 * ViewScope means that the bean will be active as long as the user is interacting with the same
 * JSF view.
 * @author Kim Hammar on 2016-03-22
 */
@GenericLogger
@Named(value = "loginBean")
@ViewScoped
public class LoginBean implements Serializable {
    @EJB
    private Controller contr;
    @Size(min = 3, max = 16, message = "username needs to be between"
            + " 3 and 16 characters long")
    private String username;
    @Size(min = 6, max = 30, message = "Password needs to be between"
            + " 6 and 16 characters long")
    private String password;

    /**
     * This method is called when the user clicks the "login" button.
     *
     * The method will validate the user's credentials and redirect to the
     * suitable page.
     *
     * @throws IOException              thrown when the specified URL cannot be found for the redirection
     * @throws NoSuchAlgorithmException thrown when the encryption phase in the model was invalid.
     */

    public void login() throws NoSuchAlgorithmException, IOException {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        boolean valid = contr.validateLogin(username, password);
        if (valid) {
            HttpSession session = contr.getSession();
            session.setAttribute("username", username);
            externalContext.redirect(externalContext.getRequestContextPath()
                    + "/authorized/index.xhtml"); 
        } else {
            failedLogin(externalContext);
        }
    }

    /**
     * This method will invalidate the user's session.
     *
     * @throws java.io.IOException thrown when the specified URL cannot be found for the redirection
     */
    public void logout() throws IOException {
        HttpSession session = contr.getSession();
        session.invalidate();
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.redirect
                (externalContext.getRequestContextPath() + "/login.xhtml");

    }

    private void failedLogin(ExternalContext externalContext) throws IOException {
        LogManager.log("FAILED LOGIN ATTEMPT", Level.INFO);
        externalContext.redirect
                (externalContext.getRequestContextPath() + "/loginerror.xhtml");
    }

    /**
     * getUsername
     *
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Updates username
     *
     * @param loginUsername new username
     */
    public void setUsername(String loginUsername) {
        this.username = loginUsername;
    }

    /**
     * getPassword
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Updates password
     *
     * @param loginPassword new password
     */
    public void setPassword(String loginPassword) {
        this.password = loginPassword;
    }
}