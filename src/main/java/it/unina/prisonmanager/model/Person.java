package it.unina.prisonmanager.model;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;
import java.util.regex.Pattern;

import it.unina.prisonmanager.utility.Check;

public class Person extends Trackable
{
	private int id;
	private String firstName;
	private String lastName;
	private LocalDate dateOfBirth;
	private String personalCode;
	private boolean isProvisionalCode;
	private String imagePath;
	private String nationality;
	
	private static final Pattern PERSONAL_CODE_PATTERN = Pattern.compile("^[A-Z0-9\\-]{5,25}$");
	private static final Pattern VALID_NAME_PATTERN = Pattern.compile("^[\\p{L}]+([ \\-'][\\p{L}]+)*$");
	
	private static final int NAME_MAXIMUM_LENGTH = 80;
	private static final int NAME_MINIMUM_LENGTH = 1;

	public Person() {}
	
	/*public Person(Person person) {
		this(
			person.id, person.firstName, person.lastName,
			person.dateOfBirth, person.personalCode,
			person.isProvisionalCode, person.imagePath,
			person.nationality, person.getInsertInstant(),
			person.getUpdateInstant()
		);
	}
	
	//for INSERT
	public Person(
		String firstName, String lastName, LocalDate dateOfBirth,
		String personalCode, boolean isProvisionalCode, String nationality
	) {
		this(
			0, firstName, lastName, dateOfBirth,
			personalCode, isProvisionalCode, nationality,
			null, null, null
		);
	}*/
	
	public Person(
		int id, String firstName, String lastName, LocalDate dateOfBirth,
		String personalCode, boolean isProvisionalCode, String nationality,
		String imagePath, Instant insertedAt, Instant updatedAt
	) {
		super(insertedAt, updatedAt);
		setId(id);
		setFirstName(firstName);
		setLastName(lastName);
		setDateOfBirth(dateOfBirth);
		setPersonalCode(personalCode);
		setCodeStatus(isProvisionalCode);
		setNationality(nationality);
		setImagePath(imagePath);
	}

	@Override
	public void setId(int id) {
		this.id = Entity.requirePositiveId(
			id, "Person ID can only be positive."
		);
	}
	
	public static String requireValidName(String personName) {
		Objects.requireNonNull(personName, "Person name is NULL.");
		personName = personName.strip();
		Check.requireInRange(
			personName.length(), NAME_MINIMUM_LENGTH, NAME_MAXIMUM_LENGTH,
			"Person name needs to be between " + NAME_MINIMUM_LENGTH + " and "
			+ NAME_MAXIMUM_LENGTH + " characters long." 
		);
		return Check.requirePatternMatch(
			personName, VALID_NAME_PATTERN,
			"Person name has invalid characters or combinations. Try again."
		);
	}
	
	public void setFirstName(String firstName) {
		this.firstName = requireValidName(firstName);
	}
	
	public void setLastName(String lastName) {
		this.lastName = requireValidName(lastName);
	}
	
	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = Objects.requireNonNull(dateOfBirth, "Date of birth is NULL.");
	}
	
	public void setPersonalCode(String personalCode) {
		Objects.requireNonNull(personalCode, "Personal code is NULL.");
		personalCode = personalCode.strip().toUpperCase();
		this.personalCode = Check.requirePatternMatch(
			personalCode, PERSONAL_CODE_PATTERN,
			"Personal code has invalid characters or combinations,"
			+ " or has the wrong length. Try again."
		);
	}
	
	public void setCodeStatus(boolean isProvisionalCode) {
		this.isProvisionalCode = isProvisionalCode;
	}
	
	public void setImagePath(String imagePath) {
		if (imagePath.isBlank()) {
			imagePath = null;
		} this.imagePath = imagePath;
	}
	
	public void setNationality(String nationality) {
		this.nationality = Check.requireNonVoid(
			nationality, "Nationality is blank/NULL."
		);
	}
	
	@Override
	public int getId() {
		return id;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}
	
	public String getPersonalCode() {
		return personalCode;
	}

	public boolean isProvisionalCode() {
		return isProvisionalCode;
	}
	
	public String getImagePath() {
		return imagePath;
	}

	public String getNationality() {
		return nationality;
	}
	
	@Override
	public String toString() {
		return "Person {personId=" + this.id
			+ ", firstName='" + this.firstName
			+ "\', lastName='" + this.lastName
			+ "\', dateOfBirth='" + this.dateOfBirth
			+ "\', personalCode='" + this.personalCode
			+ "\', isProvisionalCode='" + this.isProvisionalCode
			+ ", nationality='" + this.nationality + '\''
			+ super.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} if (!(obj instanceof Person)) {
			return false;
		} Person tmp = (Person) obj;
		return Objects.equals(this.personalCode, tmp.personalCode);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.personalCode);
	}
}
