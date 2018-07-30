package de.mehtrick.jnario2jvior;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author mehtrick
 * Removes the self written lines of code from the jnario file
 */
class CodeFromJnarioRemover {

	public static List<String> removeCode(File file) throws IOException {
		return Files.readAllLines(file.toPath(),Charset.defaultCharset()).stream().map(l -> l.trim())
				.filter(CodeFromJnarioRemover::filterLinesWithKeywords).collect(Collectors.toList());
	}

	private static boolean filterLinesWithKeywords(String line) {
		return StringUtils.startsWithAny(line, BDDKeywords.convertValuesToString());
	}

}
