package pl.spring.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@RequiredArgsConstructor
public class User implements Serializable {

    private String id;
    private String login;
    private String password;
    private String firstName;
    private String surname;
    private String email;
}