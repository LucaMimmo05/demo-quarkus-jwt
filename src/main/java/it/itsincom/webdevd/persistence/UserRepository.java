package it.itsincom.webdevd.persistence;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;
import it.itsincom.webdevd.persistence.model.ApplicationUser;
import jakarta.enterprise.context.ApplicationScoped;
import org.mindrot.jbcrypt.BCrypt;

@ApplicationScoped
public class UserRepository implements PanacheRepositoryBase<ApplicationUser, Long> {
    public ApplicationUser authenticate(String username, String password) {
        ApplicationUser applicationUser = findByUsername(username);
        if (applicationUser != null) {
            boolean matches = BCrypt.checkpw(password, applicationUser.getPassword());
            if (matches) {
                return applicationUser;
            } else {
                return null;
            }
        }
        return null;
    }

    public ApplicationUser findByUsername(String username) {
        ApplicationUser applicationUser = find(
                "SELECT u from ApplicationUser u where " +
                "u.username = :username ",
                Parameters.with("username", username)
        ).firstResult();
        return applicationUser;
    }
}
