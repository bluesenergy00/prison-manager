package it.unina.prisonmanager.dto;

import java.time.LocalDate;

public record PersonDTO(
	String firstName,
	String lastName,
	LocalDate dateOfBirth,
	String personalCode,
	boolean isProvisionalCode,
	String nationality,
	String imagePath
) {}
