package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.api.v1.model.VendorListDTO;
import guru.springfamework.services.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(VendorController.BASE_URL)
public class VendorController {

    public static final String BASE_URL = "/api/v1/vendors";

    private final VendorService vendorService;

    @Autowired
    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public VendorListDTO getAllVendors() {
        return new VendorListDTO(this.vendorService.getAllVendors());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VendorDTO createNewVendor(@RequestBody VendorDTO vendorDTO) {
        return this.vendorService.createNewVendor(vendorDTO);
    }

    @GetMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO getVendorById(@PathVariable Long id) {
        return this.vendorService.getVendorById(id);
    }
    @PutMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO updateVendorById(@PathVariable Long id , @RequestBody VendorDTO vendorDTO){
        return this.vendorService.updateVendorById(id, vendorDTO);

    }
    @DeleteMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public void deleteVendor(@PathVariable Long id){
        this.vendorService.deleteVendor(id);
    }
}
