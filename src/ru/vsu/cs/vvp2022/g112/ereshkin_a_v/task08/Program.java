package ru.vsu.cs.vvp2022.g112.ereshkin_a_v.task08;

import ru.vsu.cs.util.ArrayUtils;
import ru.vsu.cs.util.SwingUtils;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Locale;

import static ru.vsu.cs.vvp2022.g112.ereshkin_a_v.task08.Task.doesMatrixIncludeSpiralPattern;
import static ru.vsu.cs.vvp2022.g112.ereshkin_a_v.task08.Utils.*;

public class Program {

	public static void winMain() {
		SwingUtils.setLookAndFeelByName("Windows");
		Locale.setDefault(Locale.ROOT);

		SwingUtils.setDefaultFont("Microsoft Sans Serif", 18);

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(() -> new FrameMain().setVisible(true));
	}

	public static void main(String[] args) {
		InputArgs params = parseCmdArgs(args);
		if (params.window) {
			winMain();
		}
		if (params.runTests){
			runTests();
		}
		if (params.runIndividualFilesCheck){
			multipleFilesCheck(params.inputFiles, params.outputFiles);
		}
		if (params.runIndividualFileCheck) {
			int[][] arr2 = ArrayUtils.readIntArray2FromFile(params.inputFile);
			if (arr2 == null) {
				System.err.printf("Не удалось прочитать массив из \"%s\"%n", params.inputFile);
				System.exit(2);
			}

			PrintStream out;
			try {
				out = (params.outputFile != null) ? new PrintStream(params.outputFile) : System.out;
			} catch (FileNotFoundException e) {
				System.err.println(e.getMessage());
				return;
			}
			out.println(doesMatrixIncludeSpiralPattern(arr2));
			out.close();
		}
	}
}
