package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.api.v1.model.CustomerListDTO;
import guru.springfamework.services.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Api()
@RestController
@RequestMapping(CustomerController.BASE_URL)
public class CustomerController {

    private final CustomerService customerService;
    public static final String BASE_URL = "/api/v1/customers";

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @ApiOperation(value = "This will get the list of customers", notes = "There are some notes about the API")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CustomerListDTO getListOfCustomers() {
        return new CustomerListDTO(this.customerService.getAllCustomers());
    }

    @ApiOperation(value = "get customer by id")
    @GetMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO getCustomerById(@PathVariable Long id) {
        return this.customerService.getCustomerById(id);
    }

    @ApiOperation(value = "create a new customer")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDTO createCustomer(@RequestBody CustomerDTO customerDTO) {
        return this.customerService.createNewCustomer(customerDTO);
    }

    @ApiOperation(value = "update customer")
    @PutMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
        return this.customerService.saveCustomerByDTO(id, customerDTO);
    }

    @ApiOperation(value = "patch customer")
    @PatchMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO patchCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
        return this.customerService.patchCustomerByDTO(id, customerDTO);
    }

    @ApiOperation(value = "delete customer")
    @DeleteMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public void deleteCustomer(@PathVariable Long id) {
        this.customerService.deleteCustomerByID(id);
    }
}
