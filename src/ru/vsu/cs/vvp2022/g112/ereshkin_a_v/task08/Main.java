package ru.vsu.cs.vvp2022.g112.ereshkin_a_v.task08;

import ru.vsu.cs.util.ArrayUtils;
import ru.vsu.cs.util.SwingUtils;

import java.io.PrintStream;
import java.util.Locale;

public class Main {
    public static class InputArgs {
        public String inputFile;
        public String outputFile;
        public boolean error;
        public boolean help;
        public boolean window;
    }

    public static InputArgs parseCmdArgs(String[] args) {
        InputArgs inputArgs = new InputArgs();
        // Нет аргументов
        if (args.length == 0){
            inputArgs.help = true;
            inputArgs.error = true;
            return inputArgs;
        }
        // Вывести справку
        if (args[0].equals("--help")) {
            inputArgs.help = true;
        }
        // Запустить оконный интерфейс
        if (args[0].equals("--window")) {
            inputArgs.window = true;
            //return inputArgs;
        }
        if (args.length == 2) {
            inputArgs.inputFile = args[1];
            //return inputArgs;
        }
        if (args.length > 2) {
            inputArgs.outputFile = args[2];
        }
        return inputArgs;
    }

    public static void winMain() {
        //SwingUtils.setLookAndFeelByName("Windows");
        Locale.setDefault(Locale.ROOT);
        // Для красоты
        //FlatLightLaf.setup();

        //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        SwingUtils.setDefaultFont("Microsoft Sans Serif", 18);

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new FrameMain().setVisible(true));
    }

    public static void main(String[] args) throws Exception {
        InputArgs params = parseCmdArgs(args);
        if (params.help) {
            PrintStream out = params.error ? System.err : System.out;
            out.printf("Основное использование программы:%n<cmd> args <input-file> (<output-file>)");
            out.printf("%nПоказать справку:%n<cmd> -h");
            out.printf("%nЗапуститься с оконным интерфейсом:%n<cmd> -w %n");
            System.exit(params.error ? 1 : 0);
        }
        if (params.window) {
            winMain();
        } else {
            int[][] arr2 = ArrayUtils.readIntArray2FromFile(params.inputFile);
            if (arr2 == null) {
                System.err.printf("Can't read array from \"%s\"%n", params.inputFile);
                System.exit(2);
            }

            PrintStream out = (params.outputFile != null) ? new PrintStream(params.outputFile) : System.out;
            out.println(ArrayUtils.toString(arr2));
            out.close();
        }
    }
}
