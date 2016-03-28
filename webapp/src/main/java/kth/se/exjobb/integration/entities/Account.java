/*
* Royal Institute of Technology
* 2016 (c) Kim Hammar Marcus Blom
*/
package kth.se.exjobb.integration.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;

/**
 * Entity class representing a user account.
 *
 * @author Kim Hammar on 2016-03-25.
 */
@Entity
@NamedQueries({
        @NamedQuery(name="Account.findByUserName",
                query="SELECT p FROM Account p WHERE p.username = :username"),
})
public class Account implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private BigInteger id;
    @Column(unique = true)
    private String username;
    private String password;

    /**
     * Default class constructor
     */
    public Account() {
    }

    /**
     * Class constructor
     * @param username username of the account
     * @param password password of the account
     */
    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * getId
     *
     * @return id of the entity.
     */
    public BigInteger getId() {
        return id;
    }

    /**
     * getUsername
     *
     * @return username of the account
     */
    public String getUsername() {
        return username;
    }

    /**
     * getPassword
     *
     * @return password of the account
     */
    public String getPassword() {
        return password;
    }
}
