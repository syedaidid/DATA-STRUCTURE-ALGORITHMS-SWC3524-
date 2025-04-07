import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Main GUI class for algorithm visualization application
 * Features:
 * - Greedy Algorithm (TSP)
 * - Divide and Conquer
 * - Dynamic Programming (TSP)
 * - Backtracking (TSP)
 * - Array operations (Insert/Sort/Search)
 */
public class Gui implements ActionListener {
    // GUI Components
    private JFrame frame;
    private JPanel panel1, panelGreedy, panelDnc, panelDynamic, panelBacktracking, panelArray;
    private JLabel imageLabel, greedyOutput, dncOutput, dynamicOutput, backtrackingOutput, outputArray;
    private JButton greedyButton, dncButton, dynamicButton, backtrackingButton, 
    insertButton, resetButton, sortButton, searchButton;

    private int[] userArray; // Stores user-provided array for operations

    public Gui() {
        initializeGUI();
        setupEventListeners();
    }

    /**
     * Initializes all GUI components and layouts
     */
    private void initializeGUI() {
        // Main frame configuration
        frame = new JFrame();
        frame.setTitle("DROP");
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(910, 900);
        frame.setLocationRelativeTo(null);

        // Header panel with image
        panel1 = new JPanel();
        panel1.setBounds(0, 0, 900, 255);
        panel1.setBorder(new LineBorder(Color.BLACK));
        imageLabel = new JLabel(new ImageIcon("img.png")); // Application logo
        panel1.add(imageLabel);

        // Greedy Algorithm Panel
        panelGreedy = createAlgorithmPanel("Greedy Algorithm", 200, 255, 600, 100);
        greedyOutput = new JLabel("", SwingConstants.CENTER);
        greedyButton = new JButton("GO!");
        panelGreedy.add(greedyOutput);
        panelGreedy.add(greedyButton);

        // Divide and Conquer Panel
        panelDnc = createAlgorithmPanel("Divide and Conquer", 200, 355, 400, 100);
        dncOutput = new JLabel("", SwingConstants.CENTER);
        dncButton = new JButton("GO!");
        panelDnc.add(dncOutput);
        panelDnc.add(dncButton);

        // Dynamic Programming Panel
        panelDynamic = createAlgorithmPanel("Dynamic Programming", 200, 455, 600, 100);
        dynamicOutput = new JLabel("", SwingConstants.CENTER);
        dynamicButton = new JButton("GO!");
        panelDynamic.add(dynamicOutput);
        panelDynamic.add(dynamicButton);

        // Backtracking Panel
        panelBacktracking = createAlgorithmPanel("Backtracking", 200, 555, 600, 100);
        backtrackingOutput = new JLabel("", SwingConstants.CENTER);
        backtrackingButton = new JButton("GO!");
        panelBacktracking.add(backtrackingOutput);
        panelBacktracking.add(backtrackingButton);

        // Array Operations Panel
        panelArray = createAlgorithmPanel("Array Operations", 200, 655, 600, 100);
        outputArray = new JLabel("INSERT NEW ARRAY", SwingConstants.CENTER);

        JPanel panelArrayOperation = new JPanel(new GridLayout(0, 4));
        insertButton = new JButton("Insert");
        resetButton = new JButton("Reset");
        sortButton = new JButton("Sort");
        searchButton = new JButton("Search");
        searchButton.setVisible(false); // Only visible after sorting

        panelArrayOperation.add(insertButton);
        panelArrayOperation.add(resetButton);
        panelArrayOperation.add(sortButton);
        panelArrayOperation.add(searchButton);

        panelArray.add(outputArray);
        panelArray.add(panelArrayOperation);

        // Add all components to frame
        frame.add(panel1);
        frame.add(panelGreedy);
        frame.add(panelDnc);
        frame.add(panelDynamic);
        frame.add(panelBacktracking);
        frame.add(panelArray);

        frame.setVisible(true);
    }

    /**
     * Helper method to create standardized algorithm panels
     */
    private JPanel createAlgorithmPanel(String title, int x, int y, int w, int h) {
        JPanel panel = new JPanel();
        panel.setBounds(x, y, w, h);
        panel.setBorder(BorderFactory.createTitledBorder(title));
        panel.setLayout(new GridLayout(2, 0));
        return panel;
    }

    /**
     * Sets up event listeners for all interactive components
     */
    private void setupEventListeners() {
        greedyButton.addActionListener(this);
        dncButton.addActionListener(this);
        dynamicButton.addActionListener(this);
        backtrackingButton.addActionListener(this);
        insertButton.addActionListener(this);
        resetButton.addActionListener(this);
        sortButton.addActionListener(this);
        searchButton.addActionListener(this);
    }

    /**
     * Main event handler for all button actions
     */
    public void actionPerformed(ActionEvent e) {
        // Greedy TSP Algorithm
        if (e.getSource() == greedyButton) {
            String result = DeliveryRouteOptimization.greedyTSP(
                    DeliveryRouteOptimization.distanceMatrix, 
                    DeliveryRouteOptimization.locations
                );
            greedyOutput.setText(result);
        }
        // Backtracking TSP Algorithm
        else if (e.getSource() == backtrackingButton) {
            String result = DeliveryRouteOptimization.backtrackingTSP(
                    DeliveryRouteOptimization.distanceMatrix
                );
            backtrackingOutput.setText(result);
        }
        // Dynamic Programming TSP
        else if (e.getSource() == dynamicButton) {
            String result = DeliveryRouteOptimization.dynamicProgrammingTSP(
                    DeliveryRouteOptimization.distanceMatrix
                );
            dynamicOutput.setText(result);
        }
        // Divide and Conquer
        else if (e.getSource() == dncButton) {
            DivideAndConquer.main(new String[]{});
        }
        // Array Insert Operation
        else if (e.getSource() == insertButton) {
            handleArrayInsert();
        }
        // Array Reset
        else if (e.getSource() == resetButton) {
            handleArrayReset();
        }
        // Array Sort
        else if (e.getSource() == sortButton) {
            handleArraySort();
        }
        // Array Search
        else if (e.getSource() == searchButton) {
            handleArraySearch();
        }
    }

    /**
     * Handles array insertion with input validation
     */
    private void handleArrayInsert() {
        String input = JOptionPane.showInputDialog(
                null,
                "Enter numbers separated by commas:\nExample: 5,3,9,2",
                "Create New Array",
                JOptionPane.PLAIN_MESSAGE
            );

        if (input != null && !input.trim().isEmpty()) {
            try {
                userArray = Arrays.stream(input.split(","))
                .map(String::trim)
                .mapToInt(Integer::parseInt)
                .toArray();

                JOptionPane.showMessageDialog(
                    null,
                    "New array created:\n" + Arrays.toString(userArray),
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
                );

                outputArray.setText("Current Array: " + Arrays.toString(userArray));
                searchButton.setVisible(false); // Reset search until new sort

            } catch (NumberFormatException ex) {
                showErrorDialog("Invalid input. Only numbers allowed!\nExample: 5,3,9,2");
            }
        }
    }

    /**
     * Handles array reset with confirmation
     */
    private void handleArrayReset() {
        int confirm = JOptionPane.showConfirmDialog(
                null,
                "Are you sure you want to reset the array?",
                "Confirm Reset",
                JOptionPane.YES_NO_OPTION
            );

        if (confirm == JOptionPane.YES_OPTION) {
            userArray = null;
            outputArray.setText("Array reset successfully");
            searchButton.setVisible(false);
        }
    }

    /**
     * Handles array sorting and enables search
     */
    private void handleArraySort() {
        if (userArray == null || userArray.length == 0) {
            showErrorDialog("No array to sort! Please insert numbers first.");
            return;
        }

        DeliveryRouteOptimization.insertionSort(userArray);
        String sortedResult = Arrays.toString(userArray);

        outputArray.setText("Sorted: " + sortedResult);
        JOptionPane.showMessageDialog(null,
            "Array sorted successfully!",
            "Success",
            JOptionPane.INFORMATION_MESSAGE);

        searchButton.setVisible(true);
    }

    /**
     * Handles binary search on sorted array
     */
    private void handleArraySearch() {
        if (userArray == null || userArray.length == 0) {
            showErrorDialog("No array to search! Please insert numbers first.");
            return;
        }

        String input = JOptionPane.showInputDialog(
                null,
                "Enter number to search:",
                "Search Array",
                JOptionPane.QUESTION_MESSAGE
            );

        if (input == null || input.trim().isEmpty()) {
            showErrorDialog("Invalid input!");
            return;
        }

        try {
            int target = Integer.parseInt(input.trim());
            int result = DeliveryRouteOptimization.binarySearch(userArray, target);

            if (result != -1) {
                JOptionPane.showMessageDialog(null,
                    "Number " + target + " found at index: " + result,
                    "Search Result",
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null,
                    "Number " + target + " not found in the array.",
                    "Search Result",
                    JOptionPane.WARNING_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            showErrorDialog("Invalid number format! Please enter a valid integer.");
        }
    }

    /**
     * Utility method for showing error dialogs
     */
    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(
            null,
            message,
            "Error",
            JOptionPane.ERROR_MESSAGE
        );
    }

    /**
     * Checks if array is sorted (ascending)
     */
    private boolean isSorted(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] > arr[i + 1]) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        // Launch GUI on Event Dispatch Thread
        SwingUtilities.invokeLater(() -> new Gui());
    }
}