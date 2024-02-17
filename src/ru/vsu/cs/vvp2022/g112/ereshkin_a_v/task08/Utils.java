package ru.vsu.cs.vvp2022.g112.ereshkin_a_v.task08;

import org.apache.commons.cli.*;
import ru.vsu.cs.util.ArrayUtils;

import java.io.File;
import java.io.PrintStream;

import static ru.vsu.cs.vvp2022.g112.ereshkin_a_v.task08.Task.doesMatrixIncludeSpiralPattern;

public class Utils {
	public static final String WINDOWED_HT = "Запустить с оконным интерфейсом.";
	public static final String HELP_HT = "Показать справку.";
	public static final String TEST_HT = "Прогнать файлы из папки testIn и записать результаты в файлы с теми же названиями, но в папку testOut.";
	public static final String INPUT_HT = "Входной файл.";
	public static final String OUTPUT_HT = "Выходной файл.";
	public static final String IF_HT = "Входные файлы.";
	public static final String OF_HT = "Выходные файлы. " +
			"Очерёдность соответствует порядку получения входных файлов.";

	public static void writeResultToFile(String file, boolean result) {
		PrintStream ps;
		try {
			ps = new PrintStream(file);
		} catch (Exception e) {
			System.out.println("При записи в файл что-то пошло не так...");
			return;
		}
		System.out.println("Записан в файл следующий результат: " + result);
		ps.println(result);
		ps.close();
	}
	/*
	 * Объявление возможных аргументов командной строки
	 * */
	public static Options fillOptions(){
		Options options = new Options();
		Option windowed = Option.builder("w")
				.longOpt("windowed")
				.desc(WINDOWED_HT)
				.build();
		Option help = Option.builder("h")
				.longOpt("help")
				.desc(HELP_HT)
				.build();
		Option tests = Option.builder("t")
				.longOpt("tests")
				.desc(TEST_HT)
				.build();
		Option input = Option.builder("i")
				.longOpt("input-file")
				.hasArg()
				.desc(INPUT_HT)
				.build();
		Option output = Option.builder("o")
				.longOpt("output-file")
				.hasArg()
				.desc(OUTPUT_HT)
				.build();
		Option inputFiles = Option.builder("if")
				.longOpt("input-files")
				.hasArgs()
				.valueSeparator(',')
				.desc(IF_HT)
				.build();
		Option outputFiles = Option.builder("of")
				.longOpt("output-files")
				.hasArgs()
				.valueSeparator(',')
				.desc(OF_HT)
				.build();
		options.addOption(windowed);
		options.addOption(help);
		options.addOption(input);
		options.addOption(output);
		options.addOption(inputFiles);
		options.addOption(outputFiles);
		options.addOption(tests);

		return options;
	}
	public static void multipleFilesCheck(String[] inFiles, String[] outFiles){
		int overallCalculations = Math.min(inFiles.length, outFiles.length);
		int[][][] matrices = new int[overallCalculations][][];
		PrintStream[] psArr = new PrintStream[overallCalculations];
		for (int iFI = 0; iFI < outFiles.length && iFI < inFiles.length; iFI++) {
			System.out.println("Обрабатываю файл: " + inFiles[iFI]);
			matrices[iFI] = ArrayUtils.readIntArray2FromFile(inFiles[iFI]);
			if (matrices[iFI] == null) {
				System.err.printf("Не удалось прочитать тестовые массивы. %n%n");
				System.exit(1);
			}
			System.out.println("Входной массив: ");
			for (int j = 0; j < matrices[iFI].length; j++) {
				System.out.println(ArrayUtils.toString(matrices[iFI][j]));
			}
			String resultingPath = outFiles[iFI];
			try {
				psArr[iFI] = new PrintStream(resultingPath);
				psArr[iFI].close();
			} catch (Exception e){
				System.err.println(e.getMessage());
				System.exit(2);
			}
			boolean result = doesMatrixIncludeSpiralPattern(matrices[iFI]);
			System.out.println("Ответ: " + result);
			psArr[iFI].println(result);
			psArr[iFI].close();
			System.out.println();
			System.out.println("------------------------------");
			System.out.println();
		}
	}
	public static void runTests() {
		String workingDir = System.getProperty("user.dir");
		File inputFilesDir = new File(workingDir + "\\testIn\\");
		File outputFilesDir = new File(workingDir + "\\testOut\\");
		if (!inputFilesDir.exists()){
			try {
				inputFilesDir.mkdir();
			} catch (Exception e){
				System.err.println(e.getMessage());
				System.exit(10);
			}

		}
		if (!outputFilesDir.exists()){
			try {
				outputFilesDir.mkdir();
			} catch (Exception e){
				System.err.println(e.getMessage());
				System.exit(11);
			}
		}
		File[] inputFiles = inputFilesDir.listFiles();
		if (inputFiles == null) return;
		String[] inputFilesPaths = new String[inputFiles.length];
		String[] outputFilesPaths = new String[inputFiles.length];
		for (int i = 0; i < inputFiles.length; i++) {
			inputFilesPaths[i] = "testIn\\" + inputFiles[i].getName();
			outputFilesPaths[i] = "testOut\\" + inputFiles[i].getName();
			//System.out.println(inputFiles[i].getName());
		}

		multipleFilesCheck(inputFilesPaths, outputFilesPaths);

	}

	public static InputArgs parseCmdArgs(String[] args) {
		InputArgs inputArgs = new InputArgs();
		if (args == null) return inputArgs;
		Options options = fillOptions();
		/*
		 * Непосредственный парсинг.
		 * */
		CommandLine line;
		try {
			line = new DefaultParser().parse(options, args);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return inputArgs;
		}

		if (line.hasOption("h")) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("<cmd>", options);
			return inputArgs;
		}
		if (line.hasOption("w")) {
			System.out.println("Будет запущен оконный интерфейс");
			inputArgs.window = true;
		}
		if (line.hasOption("t")) {
			System.out.println("Тесты:");
			inputArgs.runTests = true;
		}
		if (line.hasOption("i") && line.hasOption("o")) {
			String inputFile = line.getOptionValue("i");
			String outputFile = line.getOptionValue("o");
			System.out.println("Будут обработаны следующие файлы: ");
			System.out.printf("%s - как входной. %n", inputFile);
			System.out.printf("%s - как выходной. %n", outputFile);
			inputArgs.inputFile = inputFile;
			inputArgs.outputFile = outputFile;
			inputArgs.runIndividualFileCheck = true;
		}
		if (line.hasOption("if") && line.hasOption("of")) {
			inputArgs.runIndividualFilesCheck = true;
			String[] inputFiles = line.getOptionValues("if");
			String[] outputFiles = line.getOptionValues("of");
			inputArgs.inputFiles = inputFiles;
			inputArgs.outputFiles = outputFiles;
		}
		return inputArgs;
	}
}
