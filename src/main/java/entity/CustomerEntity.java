package entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customer")
public class CustomerEntity {
   @Id
   @Column(name ="customer_id")
    private int customerId;
    private String name;
    private String address;
    @Column(name = "contactDetails")
    private String contactDetails;
    private int loyaltyPoints;

}
