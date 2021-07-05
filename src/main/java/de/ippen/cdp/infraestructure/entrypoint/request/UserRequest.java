package de.ippen.cdp.infraestructure.entrypoint.request;

import lombok.*;

@Getter
@Setter
@Builder(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    private String firstName;
    private String lastName;

    @Override
    public String toString() {
        return "UserRequest{" +
                " firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
