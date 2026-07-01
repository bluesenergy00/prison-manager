package it.unina.prisonmanager.model;

import java.time.Instant;
import java.util.Objects;

public class User extends Trackable
{
	private static final int USERNAME_MINIMUM_LENGTH = 3;
	private static final int USERNAME_MAXIMUM_LENGTH = 15;
	
	private int id;
	private String username;
	private String passwordHash;
	private UserRole role;
	private boolean isActive;
	
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
	}
	
	public void setId(int id) {
		if (id <= 0) {
			throw new IllegalArgumentException("User ID can only be positive.");
		} this.id = id;
	}
	
	private static String requireValidUsername(String username) {
		Objects.requireNonNull(username, "Username is NULL.");
		username = username.strip();
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
		return "User {id=" + this.id
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
		} User tmp = (User) obj;
		return Objects.equals(this.username, tmp.username);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.username);
	}
}
