package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import util.UserRole;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {

    private int userId;
    private String username;
    private String contactNumber;
    private String email;
    private String passwordHash;
    private UserRole role;

    public User( String username, String contactNumber,String email, String passwordHash, UserRole role) {

        this.username = username;
        this.contactNumber = contactNumber;
        this.email=email;
        this.passwordHash = passwordHash;
        this.role = role;

    }



}