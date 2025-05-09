package entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import util.UserRole;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name="user")
public class UserEntity {

    @Id
    @Column(name ="id")
    private Integer userId;
    private String username;
    private String contactNumber;
    private String email;
    private String passwordHash;
    private String role;
}
