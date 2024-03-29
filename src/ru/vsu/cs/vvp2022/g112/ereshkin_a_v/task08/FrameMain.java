package ru.vsu.cs.vvp2022.g112.ereshkin_a_v.task08;

import ru.vsu.cs.util.ArrayUtils;
import ru.vsu.cs.util.JTableUtils;
import ru.vsu.cs.util.SwingUtils;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

import static ru.vsu.cs.vvp2022.g112.ereshkin_a_v.task08.Task.doesMatrixIncludeSpiralPattern;


public class FrameMain extends JFrame {
	private JPanel panelMain;
	private JTable tableInput;
	private JButton buttonLoadInputFromFile;
	private JButton buttonRandomInput;
	private JButton buttonSaveInputInfoFile;
	private JButton buttonSaveOutputIntoFile;
	private JButton buttonCheckPattern;
	private JLabel patternCheckResultLabel;

	private final JFileChooser fileChooserOpen;
	private final JFileChooser fileChooserSave;


	public FrameMain() {
		this.setTitle("FrameMain");
		this.setContentPane(panelMain);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();

		JTableUtils.initJTableForArray(tableInput, 40, true, true, true, true);
		tableInput.setRowHeight(25);

		FileFilter filter = new FileNameExtensionFilter("Text files", "txt");

		fileChooserOpen = new JFileChooser();
		fileChooserOpen.setCurrentDirectory(new File("."));
		fileChooserOpen.addChoosableFileFilter(filter);

		fileChooserSave = new JFileChooser();
		fileChooserSave.setCurrentDirectory(new File("."));
		fileChooserSave.addChoosableFileFilter(filter);

		fileChooserSave.setAcceptAllFileFilterUsed(false);
		fileChooserSave.setDialogType(JFileChooser.SAVE_DIALOG);
		fileChooserSave.setApproveButtonText("Save");

		JMenuBar menuBarMain = new JMenuBar();
		setJMenuBar(menuBarMain);

		JMenu menuLookAndFeel = new JMenu();
		menuLookAndFeel.setText("Вид");
		menuBarMain.add(menuLookAndFeel);
		SwingUtils.initLookAndFeelMenu(menuLookAndFeel);

		JTableUtils.writeArrayToJTable(tableInput, new int[][]{
				{0, 1, 2, 3, 4},
				{15, 16, 17, 18, 5},
				{14, 23, 24, 19, 6},
				{13, 22, 21, 20, 7},
				{12, 11, 10, 9, 8}
		});

		this.pack();


		buttonLoadInputFromFile.addActionListener(actionEvent -> {
			try {
				if (fileChooserOpen.showOpenDialog(panelMain) == JFileChooser.APPROVE_OPTION) {
					int[][] arr = ArrayUtils.readIntArray2FromFile(fileChooserOpen.getSelectedFile().getPath());
					JTableUtils.writeArrayToJTable(tableInput, arr);
				}
			} catch (Exception e) {
				SwingUtils.showErrorMessageBox(e);
			}
		});
		buttonRandomInput.addActionListener(actionEvent -> {
			try {
				int[][] matrix = ArrayUtils.createRandomIntMatrix(
						tableInput.getRowCount(), tableInput.getColumnCount(), 100);
				JTableUtils.writeArrayToJTable(tableInput, matrix);
			} catch (Exception e) {
				SwingUtils.showErrorMessageBox(e);
			}
		});
		buttonSaveInputInfoFile.addActionListener(actionEvent -> {
			try {
				if (fileChooserSave.showSaveDialog(panelMain) == JFileChooser.APPROVE_OPTION) {
					int[][] matrix = JTableUtils.readIntMatrixFromJTable(tableInput);
					String file = fileChooserSave.getSelectedFile().getPath();
					if (!file.toLowerCase().endsWith(".txt")) {
						file += ".txt";
					}
					ArrayUtils.writeArrayToFile(file, matrix);
				}
			} catch (Exception e) {
				SwingUtils.showErrorMessageBox(e);
			}
		});
		buttonSaveOutputIntoFile.addActionListener(actionEvent -> {
			try {
				if (fileChooserSave.showSaveDialog(panelMain) == JFileChooser.APPROVE_OPTION) {
					boolean result = Boolean.parseBoolean(patternCheckResultLabel.getText());
					String file = fileChooserSave.getSelectedFile().getPath();
					if (!file.toLowerCase().endsWith(".txt")) {
						file += ".txt";
					}
					Utils.writeResultToFile(file, result);
				}
			} catch (Exception e) {
				SwingUtils.showErrorMessageBox(e);
			}
		});
		buttonCheckPattern.addActionListener(actionEvent -> {
			try {
				int[][] matrix = JTableUtils.readIntMatrixFromJTable(tableInput);
				assert matrix != null;
				patternCheckResultLabel.setText(doesMatrixIncludeSpiralPattern(matrix) ? "Соответствует паттерну" : "Не соответствует паттерну");
			} catch (Exception e) {
				SwingUtils.showErrorMessageBox(e);
			}
		});
	}
}
