package it.unina.prisonmanager.model;

import java.time.Instant;
import java.util.Objects;

import it.unina.prisonmanager.utility.Check;

public class User extends Trackable
{
	private int id;
	private String username;
	private String passwordHash;
	private UserRole role;
	private boolean isActive;
	
	//private Person personalDetails;
	
	private static final int USERNAME_MINIMUM_LENGTH = 3;
	private static final int USERNAME_MAXIMUM_LENGTH = 15;
	
	public User() {}
	
	public User(
		int id, String username, String passwordHash, UserRole role,
		boolean isActive, Instant insertedAt, Instant updatedAt
	) {
		super(insertedAt, updatedAt);
		setId(id);
		setUsername(username);
		setPasswordHash(passwordHash);
		setRole(role);
		setActivityStatus(isActive);
		//setPersonalDetails(personalDetails);
	}
	
	@Override
	public void setId(int id) {
		this.id = Entity.requirePositiveId(id, "User ID can only be positive.");
	}
	
	private static String requireValidUsername(String username) {
		Objects.requireNonNull(username, "Username is NULL.");
		username = username.strip();
		int i = username.length();
		Check.requireInRange(
			i, USERNAME_MINIMUM_LENGTH, USERNAME_MAXIMUM_LENGTH, "Username needs to be between "
			+ USERNAME_MINIMUM_LENGTH + " and " + USERNAME_MAXIMUM_LENGTH + " characters long."
		);
		username = username.toLowerCase();
		boolean hasLowerCaseASCII = false;
		while (--i >= 0) {
			char c = username.charAt(i);
			if (c >= 'a' && c <= 'z') {
				hasLowerCaseASCII = true;
			} else if (c < '0' || c > '9') {
				throw new IllegalArgumentException(
					"Username can only contain A-Z letters and digits, no spaces."
				);
			}
		} if (!hasLowerCaseASCII) {
			throw new IllegalArgumentException(
				"Username needs to contain at least one A-Z letter."
			);
		} return username;
	}
	
	public void setUsername(String username) {
		this.username = requireValidUsername(username);
	}
	
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = Check.requireNonVoid(
			passwordHash, "Password hash is blank/NULL."
		);
	}
	
	public void setRole(UserRole role) {
		this.role = Objects.requireNonNull(role, "User role is NULL.");
	}
	
	public void setActivityStatus(boolean isActive) {
		this.isActive = isActive;
	}
	
	/*public void setPersonalDetails(Person personalDetails) {
		this.personalDetails = Objects.requireNonNull(
			personalDetails, "Person reference is NULL."
		);
	}*/
	
	@Override
	public int getId() {
		return this.id;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public String getPasswordHash() {
		return this.passwordHash;
	}
	
	public UserRole getRole() {
		return this.role;
	}
	
	public boolean isActive() {
		return this.isActive;
	}
	
	/*public Person getPerson() {
		return personalDetails;
	}*/
	
	@Override
	public String toString() {
		return "User {id=" + this.id
			+ ", username='" + this.username
			+ "\', role='" + this.role
			+ "\', isActive=" + this.isActive
			+ super.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} if (!isThisClass(obj)) {
			return false;
		} User tmp = (User) obj;
		return Objects.equals(this.username, tmp.username);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.username);
	}
}
