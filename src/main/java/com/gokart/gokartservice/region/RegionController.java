package com.gokart.gokartservice.region;


import com.gokart.gokartservice.user.api.v1.model.PhoneRegionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = RegionController.REGIONS_ENDPOINT, produces = APPLICATION_JSON_VALUE)
public class RegionController {

    static final String REGIONS_ENDPOINT = "/api/v1/regions";
    private final RegionService regionService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    List<PhoneRegionResponse> getPhoneRegions() {
        return regionService.getPhoneRegions();
    }

}
