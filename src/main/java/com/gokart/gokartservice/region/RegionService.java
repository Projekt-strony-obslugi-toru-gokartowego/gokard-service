package com.gokart.gokartservice.region;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.gokart.gokartservice.user.api.v1.model.PhoneRegionResponse;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import lombok.SneakyThrows;

@Service
public class RegionService {

  PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();

  public List<PhoneRegionResponse> getPhoneRegions() {

    return phoneNumberUtil.getSupportedRegions().stream()
        .map(phoneCode -> new PhoneRegionResponse(phoneCode,
            new Locale("PL", phoneCode).getDisplayCountry()))
        .toList();
  }

  @SneakyThrows
  public void validatePhoneNumber(String phoneNumber, String phoneRegion) {
    Phonenumber.PhoneNumber parsedNumber =
        phoneNumberUtil.parse(phoneNumber, Objects.requireNonNullElse(phoneRegion, "PL"));
    if (!phoneNumberUtil.isValidNumber(parsedNumber)) {
      throw new RuntimeException("Provided phone number is not valid.");
    }
  }
}
