package de.mehtrick.jnario2jvior;

import java.io.File;

public class JnarioToJviorException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JnarioToJviorException(File file, Throwable e) {
		super("Error while converting file " + file.getAbsolutePath(), e);
	}

}
