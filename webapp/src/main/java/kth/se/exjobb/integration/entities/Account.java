package kth.se.exjobb.integration.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;

/**
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

    public Account() {
    }

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public BigInteger getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
