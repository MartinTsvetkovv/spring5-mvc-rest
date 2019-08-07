package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.api.v1.model.VendorListDTO;
import guru.springfamework.services.VendorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Api()
@RestController
@RequestMapping(VendorController.BASE_URL)
public class VendorController {

    public static final String BASE_URL = "/api/v1/vendors";

    private final VendorService vendorService;

    @Autowired
    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }


    @ApiOperation(value = "View List of Vendors", notes = "Some API notes")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public VendorListDTO getAllVendors() {
        return new VendorListDTO(this.vendorService.getAllVendors());
    }

    @ApiOperation(value = "Create a new Vendor")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VendorDTO createNewVendor(@RequestBody VendorDTO vendorDTO) {
        return this.vendorService.createNewVendor(vendorDTO);
    }

    @ApiOperation(value = "Get vendor by ID")
    @GetMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO getVendorById(@PathVariable Long id) {
        return this.vendorService.getVendorById(id);
    }
    @ApiOperation(value = "Update Vendor")
    @PutMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO updateVendorById(@PathVariable Long id , @RequestBody VendorDTO vendorDTO){
        return this.vendorService.updateVendorById(id, vendorDTO);

    }
    @ApiOperation("Delete a Vendor")
    @DeleteMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public void deleteVendor(@PathVariable Long id){
        this.vendorService.deleteVendor(id);
    }
}
