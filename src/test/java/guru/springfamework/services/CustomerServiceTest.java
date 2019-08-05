package guru.springfamework.services;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.api.v1.model.mapper.CustomerMapper;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CustomerRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class CustomerServiceTest {
    public static final String CUSTOMER_URL = "/api/v1/customer/1";
    public static final String CUSTOMER_FIRST_NAME = "Mitko";
    private List<Customer> customers;
    private CustomerService customerService;
    private CustomerDTO customerDTO;

    //@Mock
    private CustomerRepository customerRepository;

    @Before
    public void setUp() {
        //MockitoAnnotations.initMocks(this);
        this.customerRepository = mock(CustomerRepository.class);
        this.customerService = new CustomerServiceImpl(CustomerMapper.INSTANCE, this.customerRepository);
        customerDTO = new CustomerDTO();
        customerDTO.setFirstName(CUSTOMER_FIRST_NAME);


    }

    @Test
    public void getAllCustomersFromRepo() {
        this.customers = Arrays.asList(new Customer(), new Customer());

        when(this.customerRepository.findAll()).thenReturn(this.customers);

        assertEquals(2, this.customers.size());

    }

    @Test
    public void createNewCustomer() {

        Customer savedCustomer = new Customer();
        savedCustomer.setId(1L);
        savedCustomer.setFirstName(customerDTO.getFirstName());
        savedCustomer.setLastName(customerDTO.getLastName());

        when(this.customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        CustomerDTO newCustomer = this.customerService.createNewCustomer(customerDTO);

        assertEquals(customerDTO.getFirstName(), savedCustomer.getFirstName());
        assertEquals(CUSTOMER_URL, newCustomer.getCustomerUrl());
    }

    @Test
    public void saveCustomerByDTO() {
        Customer savedCustomer = new Customer();
        savedCustomer.setFirstName(customerDTO.getFirstName());
        savedCustomer.setLastName(customerDTO.getLastName());
        savedCustomer.setId(1L);

        when(this.customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        CustomerDTO savedDTO = this.customerService.saveCustomerByDTO(1L, customerDTO);

        assertEquals(customerDTO.getFirstName(), savedCustomer.getFirstName());
        assertEquals(CUSTOMER_URL, savedDTO.getCustomerUrl());

    }

    @Test
    public void deleteCustomerTest() {
        Long id = 1L;

        this.customerRepository.deleteById(id);

        verify(this.customerRepository, times(1)).deleteById(id);

    }
}