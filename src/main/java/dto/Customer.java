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
    private String email;
    private String contactDetails;
    private String address;


    public Customer( String name, String email, String contactDetails, String address) {

        this.name = name;
        this.email = email;
        this.contactDetails = contactDetails;
        this.address = address;
    }


    public String getId() {
        return String.valueOf(CustomerID);
    }

    public String getCustomerName() {

        return name;
    }
}
