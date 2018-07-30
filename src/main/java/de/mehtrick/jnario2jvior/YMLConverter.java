package de.mehtrick.jnario2jvior;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

class YMLConverter {

	private static final String NEW_LINE = System.lineSeparator();

	public static void convertToYml(File jnarioFeature, String genDir) {
		try {
			List<String> featureLines = CodeFromJnarioRemover.removeCode(jnarioFeature);
			String jviorLines = featureLines.stream().map(YMLConverter::structure)
					.collect(Collectors.joining(NEW_LINE));
			jviorLines = YMLConverter.indentToYmlFormat(jviorLines);
			createFile(jviorLines, jnarioFeature, genDir);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static void createFile(String jviorLines, File file, String genDir) throws IOException {
		String filename = file.getName().replace(".feature", ".yml");
		Path path = Paths.get(genDir + filename);
		File f = new File(path.toString());
		f.getParentFile().mkdirs();
		f.createNewFile();
		BufferedWriter writer = Files.newBufferedWriter(path);
		writer.write(jviorLines);
		writer.close();

	}

	private static String structure(String line) {
		if (line.startsWith(BDDKeywords.Feature.toString())) {
			// nothing
		} else if (line.startsWith(BDDKeywords.Background.toString())) {
			// nothing
		} else if (line.startsWith(BDDKeywords.Given.toString())) {
			line = line.replaceFirst("Given", "Given:" + NEW_LINE + "-");
		} else if (line.startsWith(BDDKeywords.When.toString())) {
			line = line.replaceFirst("When", "When:" + NEW_LINE + "-");
		} else if (line.startsWith(BDDKeywords.Then.toString())) {
			line = line.replaceFirst("Then", "Then:" + NEW_LINE + "-");
		} else if (line.startsWith(BDDKeywords.And.toString())) {
			line = line.replaceFirst("And", "-");
		} else if (line.startsWith(BDDKeywords.Scenario.toString())) {
			line = line.replaceFirst("Scenario", "- Scenario");
		}
		return line;
	}

	private static String indentToYmlFormat(String jviorLines) {
		jviorLines = jviorLines.replaceFirst("- Scenario", "Scenarios:\n- Scenario");
		jviorLines = indentBackground(jviorLines);
		jviorLines = indentScenarios(jviorLines);
		return jviorLines;

	}

	private static String indentScenarios(String jviorLines) {
		String[] lines = StringUtils.split(jviorLines, NEW_LINE);
		int startOfScenarioBlock = findStartOfScenarioBlock(lines);
		for (int i = startOfScenarioBlock; i < lines.length; i++) {
			if (lines[i].startsWith("- Scenario:")) {
				lines[i] = "\t" + lines[i];
			} else if (StringUtils.startsWithAny(lines[i], BDDKeywords.Given.toString(), BDDKeywords.When.toString(),
					BDDKeywords.Then.toString())) {
				lines[i] = "\t\t" + lines[i];
			} else if (StringUtils.startsWithAny(lines[i], "-")) {
				lines[i] = "\t\t\t" + lines[i];
			}
		}
		return StringUtils.join(lines, NEW_LINE);
	}

	private static int findStartOfScenarioBlock(String[] lines) {
		for (int i = 0; i < lines.length; i++) {
			if (lines[i].startsWith("Scenarios:")) {
				return i;
			}
		}
		return 0;

	}

	private static String indentBackground(String collect) {
		String[] lines = StringUtils.split(collect, NEW_LINE);
		for (int i = 0; i < lines.length && !lines[i].startsWith("Scenarios"); i++) {
			if (lines[i].startsWith("Given")) {
				lines[i] = "\t" + lines[i];
			} else if (lines[i].startsWith("-")) {
				lines[i] = "\t\t" + lines[i];
			}
		}
		return StringUtils.join(lines, NEW_LINE);
	}

}
