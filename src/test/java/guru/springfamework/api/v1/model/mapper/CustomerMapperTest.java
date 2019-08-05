package guru.springfamework.api.v1.model.mapper;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CustomerMapperTest {

    private CustomerMapper customerMapper;

    @Before
    public void setUp(){
        this.customerMapper = CustomerMapper.INSTANCE;

    }


    @Test
    public void customerToCustomerDTO() {
        Customer customer = new Customer();

        customer.setId(1L);
        customer.setFirstName("Mitko");
        customer.setLastName("Georgiev");

        CustomerDTO customerDTO = this.customerMapper.customerToCustomerDTO(customer);

        assertEquals("Mitko", customerDTO.getFirstName());
        assertEquals("Georgiev", customerDTO.getLastName());


    }
}