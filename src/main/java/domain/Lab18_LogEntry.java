package domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Lab18_LogEntry {
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.uuuu HH:mm:ss");
	private final String description;
	private final LocalDateTime timestamp;
	
	public Lab18_LogEntry (String description, LocalDateTime timestamp) {
		this.description = description;
		this.timestamp = timestamp;
	}
	
	@Override
	public String toString () {
		return timestamp.format(formatter) + ": " + description;
	}
}
