package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.domain.Vendor;
import guru.springfamework.services.VendorService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VendorControllerTest extends AbstractRestControllerTest {

    private VendorService vendorService;
    private VendorController vendorController;
    private MockMvc mockMvc;
    private VendorDTO vendorDTO1;

    @Before
    public void setUp() {
        this.vendorService = mock(VendorService.class);
        this.vendorController = new VendorController(this.vendorService);
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.vendorController).build();
        this.vendorDTO1 = new VendorDTO();
        this.vendorDTO1.setName("Fruits");
    }

    @Test
    public void getAllVendors() throws Exception {
        VendorDTO vendorDTO2 = new VendorDTO();
        vendorDTO2.setName("Meat");

        List<VendorDTO> vendors = Arrays.asList(vendorDTO1, vendorDTO2);

        when(this.vendorService.getAllVendors()).thenReturn(vendors);

        this.mockMvc.perform(get("/api/v1/vendors/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendors", hasSize(2)));

    }

    @Test
    public void createNewVendor() throws Exception {
        VendorDTO returnVendorDTO = new VendorDTO();
        returnVendorDTO.setName(this.vendorDTO1.getName());
        returnVendorDTO.setVendorUrl("/api/v1/vendors/1");

        when(this.vendorService.createNewVendor(this.vendorDTO1)).thenReturn(returnVendorDTO);

        this.mockMvc.perform(post("/api/v1/vendors/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJasonString(this.vendorDTO1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo("Fruits")))
                .andExpect(jsonPath("$.vendor_url", equalTo("/api/v1/vendors/1")));
    }

    @Test
    public void getVendorById() throws Exception {
        this.vendorDTO1.setVendorUrl(VendorController.BASE_URL + "/1");

        when(this.vendorService.getVendorById(anyLong())).thenReturn(this.vendorDTO1);

        this.mockMvc.perform(get(VendorController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("Fruits")));
    }

    @Test
    public void updateVendorById() throws Exception {
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(this.vendorDTO1.getName());
        vendorDTO.setVendorUrl(VendorController.BASE_URL + "/1");

        when(this.vendorService.updateVendorById(anyLong(), any(VendorDTO.class))).thenReturn(vendorDTO);

        this.mockMvc.perform(put(VendorController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJasonString(vendorDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("Fruits")))
                .andExpect(jsonPath("$.vendor_url", equalTo(VendorController.BASE_URL + "/1")));
    }

    @Test
    public void deleteVendorById() throws Exception {


        this.mockMvc.perform(delete(VendorController.BASE_URL+"/1")
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


        verify(this.vendorService).deleteVendor(anyLong());
    }
}