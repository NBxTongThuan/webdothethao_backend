package com.tongthuan.webdothethao_backend.controller.publiccontroller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


@RestController
@RequestMapping("/api/vn/address")
@CrossOrigin(origins = "*")
public class AddressVNController {

    private final String EXTERNAL_API = "https://provinces.open-api.vn/api";



    @GetMapping("/provinces")
    public ResponseEntity<?> getProvinces(){
        RestTemplate restTemplate = new RestTemplate();
        try{
            ResponseEntity<String> response = restTemplate.getForEntity(EXTERNAL_API+"/p/",String.class);
            return ResponseEntity.ok(response.getBody());
        }catch (Exception e)
        {
            return ResponseEntity.status(500).body("Failed to fetch provinces");
        }
    }

    @GetMapping("/districts")
    public ResponseEntity<?> getDistricts(@RequestParam("provinceCode") String provinceCode)
    {
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(EXTERNAL_API+"/p/"+provinceCode+"?depth=2",String.class);
            return ResponseEntity.ok(response.getBody());
        }catch (Exception e)
        {
            return ResponseEntity.status(500).body("Failed to fetch provinces");
        }
    }

    @GetMapping("/wards")
    public ResponseEntity<?> getWards(@RequestParam("districtCode") String districtCode)
    {
        RestTemplate restTemplate =  new RestTemplate();
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(EXTERNAL_API+"/d/"+districtCode+"?depth=2",String.class);
            return ResponseEntity.ok(response.getBody());
        }catch (Exception e)
        {
            return ResponseEntity.status(500).body("Failed to fetch provinces");
        }
    }


}
