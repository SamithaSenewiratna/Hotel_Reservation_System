package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Customer {

    private int CustomerID;
    private String name;
    private String address;
    private String contactDetails;
    private int loyaltyPoints;


    public Customer( String name, String address, String contactDetails, int loyaltyPoints) {

        this.name = name;
        this.address = address;
        this.contactDetails = contactDetails;
        this.loyaltyPoints = loyaltyPoints;
    }



}
