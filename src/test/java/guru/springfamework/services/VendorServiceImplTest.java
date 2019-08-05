package guru.springfamework.services;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.controllers.v1.VendorController;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class VendorServiceImplTest {

    private static final String VENDOR_URL = "/api/v1/vendor/1";
    private VendorService vendorService;
    private ModelMapper modelMapper;
    private VendorDTO vendorDTO;

    @Mock
    private VendorRepository vendorRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.modelMapper = new ModelMapper();
        this.vendorService = new VendorServiceImpl(this.vendorRepository, this.modelMapper);
        this.vendorDTO = new VendorDTO();
        this.vendorDTO.setName("Fruits");
    }

    @Test
    public void getAllVendors() {
        List<Vendor> vendors = Arrays.asList(new Vendor(), new Vendor());

        when(this.vendorRepository.findAll()).thenReturn(vendors);

        assertEquals(2, vendors.size());

    }
    @Test
    public void createNewVendor(){
        Vendor vendor = new Vendor();
        vendor.setId(1L);
        vendor.setName(vendorDTO.getName());

        when(this.vendorRepository.save(any(Vendor.class))).thenReturn(vendor);

        VendorDTO newVendor = this.vendorService.createNewVendor(vendorDTO);

        assertEquals(vendorDTO.getName(), vendor.getName());
        assertEquals(VENDOR_URL, newVendor.getVendorUrl());

    }
    @Test
    public void getVendorById(){
       Vendor vendor = new Vendor();
       vendor.setId(1L);
       vendor.setName(this.vendorDTO.getName());

       when(this.vendorRepository.findById(anyLong())).thenReturn(Optional.of(vendor));

        VendorDTO vendorById = this.vendorService.getVendorById(1L);

        assertEquals("Fruits", vendorById.getName());


    }
    @Test
    public void updateVendorById(){
        Vendor vendor = new Vendor();
        vendor.setName(this.vendorDTO.getName());
        vendor.setId(1L);

        when(this.vendorRepository.save(any(Vendor.class))).thenReturn(vendor);

        VendorDTO savedDTO = this.vendorService.updateVendorById(1L, this.vendorDTO);

        assertEquals(this.vendorDTO.getName(), savedDTO.getName());
        assertEquals(VENDOR_URL, savedDTO.getVendorUrl());

    }
    @Test
    public void deleteVendorById(){
        Long id = 1L;

        this.vendorRepository.deleteById(id);

        verify(this.vendorRepository, times(1)).deleteById(1L);
    }
}