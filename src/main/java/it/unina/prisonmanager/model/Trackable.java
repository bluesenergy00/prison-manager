package it.unina.prisonmanager.model;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public abstract class Trackable implements Entity
{
	private Instant insertedAt;
	private Instant updatedAt;
	
	public Trackable() {}
	
	public Trackable(Instant insertedAt, Instant updatedAt) {
		setInsertInstant(insertedAt);
		setUpdateInstant(updatedAt);
	}
	
	public void setInsertInstant(Instant insertedAt) {
		Objects.requireNonNull(insertedAt, "Insert instant is NULL.");
		requireNonFuture(insertedAt, "Insert instant cannot be in the future.");
		this.insertedAt = truncateToSeconds(insertedAt);
	}
	
	public void setUpdateInstant(Instant updatedAt) {
		requireNonFuture(updatedAt, "Update instant cannot be in the future.");
		this.updatedAt = truncateToSeconds(updatedAt);
	}
	
	protected static Instant truncateToSeconds(Instant instant) {
		return (instant == null) ? null : instant.truncatedTo(ChronoUnit.SECONDS);
	}
	
	protected static Instant requireNonFuture(Instant instant) {
		return requireNonFuture(instant, "Instant cannot be in the future.");
	}
	
	protected static Instant requireNonFuture(Instant instant, String message) {
		if (instant != null && instant.isAfter(Instant.now())) {
			throw new IllegalArgumentException(message);
		} return instant;
	}
	
	public Instant getInsertInstant() {
		return insertedAt;
	}
	
	public Instant getUpdateInstant() {
		return updatedAt;
	}
	
	@Override
	public String toString() {
		return ", insertedAt=" + insertedAt
			+ ", updatedAt=" + updatedAt + "}";
	}
	
	@Override
	public abstract boolean equals(Object obj);
	
	@Override
	public abstract int hashCode();
}
