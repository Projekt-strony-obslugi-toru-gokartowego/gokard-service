package com.gokart.gokartservice.region;

import io.swagger.v3.oas.annotations.media.Schema;

record PhoneRegionResponse(//
    @Schema(description = "Phone region code which need to be provided during registration") //
    String phoneRegionCode, //
    @Schema(description = "Phone region description") //
    String phoneRegionDescription //
) {
}
