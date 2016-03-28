/*
* Royal Institute of Technology
* 2016 (c) Kim Hammar Marcus Blom
*/
package kth.se.exjobb.model;

import kth.se.exjobb.integration.DAO.DataAccessObject;
import kth.se.exjobb.integration.entities.Account;
import kth.se.exjobb.model.security.SHA512;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.validation.constraints.Size;
import java.security.NoSuchAlgorithmException;

/**
 * Class that handles login business logic and validation.
 *
 * @author Kim Hammar on 2016-03-25.
 */
@Stateless
public class LoginEJB {

    @EJB
    DataAccessObject dao;

    /**
     * Method to validate user credentials against the database.
     *
     * @param username username to validate
     * @param password password to validate
     * @return true if the login was successful otherwise false
     * @throws NoSuchAlgorithmException thrown when encryption fails
     */
    public boolean validateLogin(@Size(min=3, max=16) String username, @Size(min=6, max=30) String password)
            throws NoSuchAlgorithmException
    {
        Account userAccount = dao.getUserByName(username);
        if (userAccount == null)
            return false;
        else{
            return userAccount.getPassword().equals(SHA512.encrypt(password));
        }
    }
}
