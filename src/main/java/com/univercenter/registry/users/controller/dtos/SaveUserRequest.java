package com.univercenter.registry.users.controller.dtos;

import java.util.Set;

public record SaveUserRequest(
        String name,
        String email,
        String password,
        Set<Phone> phones
) {
    public record Phone(String number, String cityCode, String countryCode){}
}
