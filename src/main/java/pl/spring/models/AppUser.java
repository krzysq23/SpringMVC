package pl.spring.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class AppUser implements Serializable {
	
	private String id;
    private String login;
    private String firstName;
    private String surname;
    private String email;

    public void updateAppUser(AppUser appUser) {
        this.id = appUser.getId();
        this.login = appUser.getLogin();
        this.firstName = appUser.getFirstName();
        this.surname = appUser.getSurname();
        this.email = appUser.getEmail();
    }

}
