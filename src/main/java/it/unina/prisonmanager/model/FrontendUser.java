package it.unina.prisonmanager.model;

import java.time.Instant;
import java.util.Objects;

public class FrontendUser extends Trackable
{
	private int id;
	private String username;
	private String passwordHash;
	private UserRole role;
	private boolean isActive;
	
	public static final int USERNAME_MINIMUM_LENGTH = 3;
	public static final int USERNAME_MAXIMUM_LENGTH = 15;
	public static final int PASSWORD_MINIMUM_LENGTH = 8;
	public static final int PASSWORD_MAXIMUM_LENGTH = 120;
	
	public FrontendUser() {}
	
	public FrontendUser(
		int id, String username, String passwordHash, UserRole role,
		boolean isActive, Instant insertedAt, Instant updatedAt
	) {
		super(insertedAt, updatedAt);
		setId(id);
		setUsername(username);
		setPasswordHash(passwordHash);
		setRole(role);
		setActivityStatus(isActive);
	}
	
	public void setId(int id) {
		if (id <= 0) {
			throw new IllegalArgumentException("User ID can only be positive.");
		} this.id = id;
	}
	
	private static String requireValidUsername(String username) {
		Objects.requireNonNull(username, "Username is NULL.");
		username = username.trim();
		int i = username.length();
		if (i < USERNAME_MINIMUM_LENGTH || i > USERNAME_MAXIMUM_LENGTH) {
			throw new IllegalArgumentException(
				"Username needs to be between " + USERNAME_MINIMUM_LENGTH
				+ " and " + USERNAME_MAXIMUM_LENGTH + " characters long."
			);
		} username = username.toLowerCase();
		boolean hasLowerCaseASCII = false;
		while (--i >= 0) {
			char c = username.charAt(i);
			if (c >= 'a' && c <= 'z') {
				hasLowerCaseASCII = true;
			} else if (c < '0' || c > '9') {
				throw new IllegalArgumentException(
					"Username can only contain ASCII letters and digits, no spaces."
				);
			}
		} if (!hasLowerCaseASCII) {
			throw new IllegalArgumentException(
				"Username needs to contain at least one ASCII letter."
			);
		} return username;
	}
	
	public static String requireValidPassword(String password) {
		Objects.requireNonNull(password, "Password is NULL.");
		int i = password.length();
		if (i < PASSWORD_MINIMUM_LENGTH || i > PASSWORD_MAXIMUM_LENGTH) {
			throw new IllegalArgumentException(
				"Password needs to be between " + PASSWORD_MINIMUM_LENGTH
				+ " and " + PASSWORD_MAXIMUM_LENGTH + " characters long."
			);
		} boolean hasLowerCase = false;
		boolean hasUpperCase = false;
		boolean hasDigit = false;
		while ((!hasLowerCase || !hasUpperCase || !hasDigit) && --i >= 0) {
			char c = password.charAt(i);
			if (Character.isLowerCase(c)) {
				hasLowerCase = true;
			} else if (Character.isUpperCase(c)) {
				hasUpperCase = true;
			} else if (c >= '0' && c <= '9') {
				hasDigit = true;
			}
		} if (i < 0) {
			throw new IllegalArgumentException(
				"Password needs lower case, upper case and digit characters."
			);
		} return password;
	}
	
	public void setUsername(String username) {
		this.username = requireValidUsername(username);
	}
	
	public void setPasswordHash(String passwordHash) {
		Objects.requireNonNull(passwordHash, "Password hash is NULL.");
		if (passwordHash.isBlank()) {
			throw new IllegalArgumentException("Password hash is blank.");
		} this.passwordHash = passwordHash;
	}
	
	public void setRole(UserRole role) {
		this.role = Objects.requireNonNull(role, "User role is NULL.");
	}
	
	public void setActivityStatus(boolean isActive) {
		this.isActive = isActive;
	}
	
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
	
	@Override
	public String getExtensionDetails() {
		return "FrontendUser {id=" + this.id
			+ ", username='" + this.username
			+ "\', role='" + this.role
			+ "\', isActive=" + this.isActive;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} if (obj == null || getClass() != obj.getClass()) {
			return false;
		} FrontendUser tmp = (FrontendUser) obj;
		return Objects.equals(this.username, tmp.username);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.username);
	}
}
