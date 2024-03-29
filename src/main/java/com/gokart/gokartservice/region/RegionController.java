package com.gokart.gokartservice.region;


import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.gokart.gokartservice.config.CustomErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@ApiResponse(responseCode = "500",
    content = @Content(schema = @Schema(implementation = CustomErrorResponse.class)))
@RequestMapping(value = RegionController.REGIONS_ENDPOINT, produces = APPLICATION_JSON_VALUE)
class RegionController {

  static final String REGIONS_ENDPOINT = "/api/v1/regions";
  private final RegionService regionService;

  @Operation(summary = "Return list of available regions")
  @ApiResponse(responseCode = "200",
      content = @Content(
          array = @ArraySchema(schema = @Schema(implementation = PhoneRegionResponse.class))))
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  List<PhoneRegionResponse> getPhoneRegions() {
    return regionService.getPhoneRegions();
  }

}
