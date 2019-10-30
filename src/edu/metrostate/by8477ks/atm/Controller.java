package edu.metrostate.by8477ks.atm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Controller implements ActionListener {
    private View view;

    public Controller(View obj) {
        super();
        this.view = obj;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // button one action
        if (e.getSource() == view.b1) {

            // open and read file into memory as ArrayList
            try {
                processFiles();
            } catch (WrongFileTypeException wftex) {
                view.ta1.append("\n" + wftex.getMessage());
            } catch (NumberFormatException nfex) {
                view.ta1.append("\nFile has non-numbers,\nnumbers that are too large,\nor more than one per line.\n" + nfex.getMessage());
                System.out.println();
            } catch (FileNotFoundException fnfex) {
                view.ta1.append("\n!!! Unable to locate file.\n" + fnfex.getMessage());
                fnfex.printStackTrace();
            } catch (UnsupportedEncodingException useex) {
                view.ta1.append("\n!!! File encoding unsupported. Use UTF-8 file.\n" + useex.getMessage());
                useex.printStackTrace();
            } catch (P2Exceptions.ImproperHeaderFileException ex) {
                view.ta1.append("\n!!! Improper header.\n" + ex.getMessage());
            }
            view.ta1.append("\n--------------------------------");
//            view.scrollPane.getVerticalScrollBar().setValue(view.scrollPane.getVerticalScrollBar().getMaximum());
            view.ta1.setCaretPosition(view.ta1.getDocument().getLength());
        }
    }

    /**
     * Prompts user for file, merge sorts the integer  contents in descending order
     * and writes the sorted data to a new file in the same directory
     *
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException
     * @throws WrongFileTypeException
     */
    private void processFiles() throws FileNotFoundException, UnsupportedEncodingException, WrongFileTypeException, P2Exceptions.ImproperHeaderFileException {
        //JFileChooser only allows text files
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("*.txt", "txt", "text");
        chooser.setFileFilter(filter);


        // Prompt user for file with JFileChooser
        int returnVal = chooser.showOpenDialog(view.frame.getParent());
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File sourceFile = chooser.getSelectedFile();
            if (!filter.accept(sourceFile)) {
                throw new WrongFileTypeException("Wrong file type. Choose a .txt file.");
            }
            String[] fullName = separateNameAndExtension(sourceFile);
            String fileName = fullName[0];
            String extension = fullName[1];
            String fullPath = sourceFile.getPath();


            // isolate directory from filename
            String directory = fullPath.substring(0, fullPath.length() - (fileName + extension).length());

            //update view
            view.tf1.setText("Input file: " + fileName + extension);

            // Read header into memory
            ArrayList<Integer> header = readHeaderLine(sourceFile); // O(n)
            // convert header into int[]
            int[] intHeader = new int[header.size()];
            for (int i = 0; i < header.size(); i++) {
                intHeader[i] = header.get(i);
            }
            // Reader rest of file into memory
            ArrayList<Integer> listOfAmounts = readFileStartingAt(sourceFile, 1);
            // Convert ArrayList to int[] O(n)
            int[] intAmounts = new int[listOfAmounts.size()];
            for (int i = 0; i < listOfAmounts.size(); i++) {
                intAmounts[i] = listOfAmounts.get(i);
            }

            Timer timer = new Timer();
            timer.start();
            String outputFilename = recursiveCombinations(fileName, extension, directory, intHeader, intAmounts);
            timer.stop();
            view.ta1.append("\n" + outputFilename);
            try {
                view.ta1.append(String.format("\nRecursive: %dms", timer.read()));
            } catch (Exception e) {
                e.printStackTrace();
            }

            timer.start();
            outputFilename = dynamicCombinations(fileName, extension, directory, intHeader, intAmounts);
            timer.stop();
            view.ta1.append("\n" + outputFilename);
            try {
                view.ta1.append(String.format("\nDynamic: %dms", timer.read()));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private String recursiveCombinations(String fileName, String extension, String directory, int[] intHeader, int[] intAmounts) throws UnsupportedEncodingException, FileNotFoundException, P2Exceptions.ImproperHeaderFileException {
        ArrayList<CombinationEntry> recursivePairs = new ArrayList<>();
        RecursiveATM recursiveATM = new RecursiveATM();
        recursiveATM.setBills(intHeader);
        Timer fullRecursionTimer = new Timer();
        Timer recursiveTimer = new Timer();
        // use recursive ATM
        fullRecursionTimer.start();
        for (int amount : intAmounts) {
            recursiveTimer.start();
            CombinationEntry entry = new CombinationEntry(amount, recursiveATM.rCombinations(amount, 0));
            recursiveTimer.stop();
            try {
                entry.setDuration(recursiveTimer.read());
            } catch (Exception e) {
                e.printStackTrace();
            }
            recursivePairs.add(entry);
        }
        fullRecursionTimer.stop();

        try {
            System.out.printf("Full recursion time: %dms\n", fullRecursionTimer.read());
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Give the user feedback after successfully writing the sorted file
        return writeArrayToFile(directory, fileName + "_recursive" + extension, recursivePairs);
    }

    private String dynamicCombinations(String fileName, String extension, String directory, int[] intHeader, int[] intAmounts) throws UnsupportedEncodingException, FileNotFoundException {
        ArrayList<CombinationEntry> dynamicPairs = new ArrayList<>();
//        DynamicATM dynamicATM = new DynamicATM();

        Timer fullDynamicTimer = new Timer();
        Timer dynamicTimer = new Timer();
        // use recursive ATM
        fullDynamicTimer.start();
        for (int amount : intAmounts) {
            dynamicTimer.start();
            CombinationEntry entry = new CombinationEntry(amount, DynamicATM.dCombinations(amount, intHeader));
            dynamicTimer.stop();
            try {
                entry.setDuration(dynamicTimer.read());
            } catch (Exception e) {
                e.printStackTrace();
            }
            dynamicPairs.add(entry);
        }
        fullDynamicTimer.stop();

        try {
            System.out.printf("Full dynamic time: %dms\n", fullDynamicTimer.read());
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Give the user feedback after successfully writing the sorted file
        return writeArrayToFile(directory, fileName + "_dynamic" + extension, dynamicPairs);
    }

    /**
     * Returns an ArrayList of the values separated by spaces from the first line of the file
     *
     * @param userFile where the first line is a space delimited list of values
     * @return list of values from the first line of the file
     * @throws FileNotFoundException
     * @throws NumberFormatException
     */
    public static ArrayList<Integer> readHeaderLine(File userFile) throws FileNotFoundException, NumberFormatException {
        ArrayList<Integer> header = new ArrayList<Integer>();
        Scanner myReader = new Scanner(userFile);
        String firstLine = myReader.nextLine();
        Scanner scanHeader = new Scanner(firstLine);
        scanHeader.useDelimiter(" ");

        while (scanHeader.hasNext()) {
            String data = scanHeader.next();
            header.add(Integer.parseInt(data));
            // System.out.println(data); // view each element of the header
        }

        myReader.close();


        return header;
    }

    /**
     * Reads file into memory as an ArrayList O(n)
     *
     * @param userFile File
     * @return list of each of the lines in the file
     * @throws FileNotFoundException
     */
    public static ArrayList<Integer> readFileIntoMemory(File userFile) throws
            FileNotFoundException, NumberFormatException {
        Scanner myReader = new Scanner(userFile);
        ArrayList<Integer> lineItems = new ArrayList<Integer>();
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            lineItems.add(Integer.parseInt(data));
            // System.out.println(data); // view each line of data

        }
        myReader.close();
        return lineItems;
    }

    /**
     * Reads file into memory as an ArrayList O(n) starting at specified line
     *
     * @param userFile
     * @param startingLine
     * @return
     * @throws FileNotFoundException
     * @throws NumberFormatException
     */
    public static ArrayList<Integer> readFileStartingAt(File userFile, int startingLine) throws
            FileNotFoundException, NumberFormatException {
        Scanner myReader = new Scanner(userFile);
        ArrayList<Integer> lineItems = new ArrayList<Integer>();
        int lineCount = 0;
        while (myReader.hasNextLine()) {
            if (lineCount >= startingLine) {
                String data = myReader.nextLine();
                lineItems.add(Integer.parseInt(data));
                // System.out.println(data); // view each line of data
            } else {
                myReader.nextLine();
            }
            lineCount++;
        }
        myReader.close();
        return lineItems;
    }


    /**
     * Write array of integers to the file in the specified directory. O(n)
     *
     * @param directory String path to file
     * @param filename  String name of file
     * @param array     int[] data to be written to the file line by line
     * @return name of output file
     * @throws UnsupportedEncodingException
     */
    public static String writeArrayToFile(String directory, String filename, int[] array) throws
            UnsupportedEncodingException, FileNotFoundException {
        PrintWriter writer = new PrintWriter(directory + filename, "UTF-8");
        for (int i = 0; i < array.length; i++) {
            writer.println(array[i]);
        }
        writer.close();
        return String.format("Output file: %s", filename);
    }

    public static String writeArrayToFile(String directory, String filename, ArrayList<CombinationEntry> array) throws
            UnsupportedEncodingException, FileNotFoundException {
        PrintWriter writer = new PrintWriter(directory + filename, "UTF-8");
        for (CombinationEntry entry : array) {
            writer.println(String.format("%d %d %dms", entry.getAmount(), entry.getCombinations(), entry.getDuration()));
        }
        writer.close();
        return String.format("Output file: %s", filename);
    }

    /**
     * Extract the file extension from the filename if it begins with a period.
     * Based on code from: https://www.journaldev.com/842/java-get-file-extension
     *
     * @param file input file
     * @return [0] i is the filename, [1] is the extension (empty string if none)
     */
    private static String[] separateNameAndExtension(File file) {
        String[] fileName = new String[2];
        fileName[0] = file.getName();
        if (fileName[0].lastIndexOf(".") != -1 && fileName[0].lastIndexOf(".") != 0) {
            fileName[1] = fileName[0].substring(fileName[0].lastIndexOf("."));
            fileName[0] = fileName[0].substring(0, fileName[0].lastIndexOf("."));

        } else fileName[1] = "";
        return fileName;
    }

    /**
     * A generic exception to give contextual feedback to the user
     */
    class WrongFileTypeException extends Exception {
        public WrongFileTypeException(String s) {
            super(s);
        }
    }

}
