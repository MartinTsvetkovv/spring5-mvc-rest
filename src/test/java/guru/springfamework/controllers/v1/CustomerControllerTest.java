package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.services.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CustomerControllerTest extends AbstractRestControllerTest {

    private CustomerController customerController;
    private CustomerService customerService;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.customerService = mock(CustomerService.class);

        this.customerController = new CustomerController(this.customerService);

        this.mockMvc = MockMvcBuilders.standaloneSetup(this.customerController).build();
    }

    @Test
    public void getListOfCustomers() throws Exception {
        CustomerDTO customer = new CustomerDTO();

        customer.setFirstName("Bibi");
        customer.setLastName("Ivanova");

        CustomerDTO customer1 = new CustomerDTO();

        customer1.setFirstName("Mimi");
        customer1.setLastName("Penkova");

        List<CustomerDTO> customers = Arrays.asList(customer, customer1);

        when(this.customerService.getAllCustomers()).thenReturn(customers);

        this.mockMvc.perform(get(CustomerController.BASE_URL).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(2)));


    }

    @Test()
    public void getCustomerById() throws Exception {
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstName("Mitko");
        customer.setLastName("Pavlov");
        customer.setCustomerUrl("/api/v1/customer/1");

        when(this.customerService.getCustomerById(anyLong())).thenReturn(customer);

        this.mockMvc.perform(get("/api/v1/customers/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo("Mitko")));


    }

    @Test
    public void createNewCustomer() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("Mitko");
        customerDTO.setLastName("Pavlov");

        CustomerDTO returnCustomerDTO = new CustomerDTO();
        returnCustomerDTO.setFirstName(customerDTO.getFirstName());
        returnCustomerDTO.setLastName(customerDTO.getLastName());
        returnCustomerDTO.setCustomerUrl(CustomerController.BASE_URL + "/1");

        when(this.customerService.createNewCustomer(customerDTO)).thenReturn(returnCustomerDTO);

        mockMvc.perform(post(CustomerController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJasonString(customerDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstname", equalTo("Mitko")))
                .andExpect(jsonPath("$.customer_url", equalTo("/api/v1/customers/1")));
    }

    @Test
    public void testUpdateCustomer() throws Exception {
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstName("Fred");
        customer.setLastName("Flintstone");

        CustomerDTO returnDTO = new CustomerDTO();
        returnDTO.setFirstName(customer.getFirstName());
        returnDTO.setLastName(customer.getLastName());
        returnDTO.setCustomerUrl(CustomerController.BASE_URL + "/1");

        when(customerService.saveCustomerByDTO(anyLong(), any(CustomerDTO.class))).thenReturn(returnDTO);

        //when/then
        mockMvc.perform(put(CustomerController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJasonString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo("Fred")))
                .andExpect(jsonPath("$.lastname", equalTo("Flintstone")))
                .andExpect(jsonPath("$.customer_url", equalTo(CustomerController.BASE_URL + "/1")));
    }
}