package de.mehtrick.jnario2jvior;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.apache.commons.io.FileUtils;

public class Jnario2Jvior {

	/**
	 * Generates jvior.yml files from jnario feature classes
	 * Clears the generation folder before generating!
	 * @param pathToJnarioFiles Path to search recursivly for feature classes
	 * @param genDir Dir where the jvior files will be generated. <b>The gendir will be cleared before generation!!</b>
	 */
	public void convertToJvior(String pathToJnarioFiles, String genDir) {
		cleanGenDir(genDir);
		Collection<File> jnarioFeatureFiles = findJnarioFiles(pathToJnarioFiles);
		jnarioFeatureFiles.stream().forEach(j -> YMLConverter.convertToYml(j, genDir));
	}

	private void cleanGenDir(String genDir) {
		try {
			File genFolder = new File(genDir);
			if (genFolder.isDirectory()) {
				FileUtils.forceDelete(genFolder);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private Collection<File> findJnarioFiles(String pathToJnarioFiles) {
		return FileUtils.listFiles(new File(pathToJnarioFiles), new String[] { "feature" }, true);
	}

}
