package edu.rims.medi_track.dto;

public record AdminRegistrationDTO(String name,
                                   String email,
                                   String password,
                                   String address,
                                   Integer age) {
}
