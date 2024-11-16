package Project4.sudoku;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SudokuGame {
    private static final int GRID_SIZE = 9;
    private static final int SUBGRID_SIZE = 3;

    public static void main(String[] args) {

        // Creating the main frame
        JFrame frame = new JFrame("Sudoku GamePlay");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null); //position frame in the middle


        // Panel with a grid layout: displays on top of the frame
        JPanel panel = new JPanel();
        //                              columns    rows
        panel.setLayout(new GridLayout(GRID_SIZE, GRID_SIZE));

        // Add the panel to the frame (to occupy the frame area)
        frame.add(panel, BorderLayout.CENTER);


        // Create a 2D array of text fields
        JTextField[][] cells = new JTextField[GRID_SIZE][GRID_SIZE];
        int[][] solution = new int[GRID_SIZE][GRID_SIZE]; // To store the solution

        // Fill in the initial grid with some predefined numbers 
        int[][] initialGrid = {
            {5, 3, 0, 0, 7, 0, 0, 0, 0},
            {6, 0, 0, 1, 9, 5, 0, 0, 0},
            {0, 9, 8, 0, 0, 0, 0, 6, 0},
            {8, 0, 0, 0, 6, 0, 0, 0, 3},
            {4, 0, 0, 8, 0, 3, 0, 0, 1},
            {7, 0, 0, 0, 2, 0, 0, 0, 6},
            {0, 6, 0, 0, 0, 0, 2, 8, 0},
            {0, 0, 0, 4, 1, 9, 0, 0, 5},
            {0, 0, 0, 0, 8, 0, 0, 7, 9}
        };

        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                cells[row][col] = new JTextField();
                cells[row][col].setHorizontalAlignment(JTextField.CENTER);
                cells[row][col].setFont(new Font("SansSerif", Font.BOLD, 20));
                if (initialGrid[row][col] != 0) {
                    cells[row][col].setText(String.valueOf(initialGrid[row][col]));
                    cells[row][col].setEditable(false);
                    cells[row][col].setBackground(Color.LIGHT_GRAY);
                }
                panel.add(cells[row][col]);
            }
        }



        // Add a button to check the solution
        JButton checkButton = new JButton("Check Solution Here");
        checkButton.setFocusable(false);
        frame.add(checkButton, BorderLayout.SOUTH);
        
        // Set the frame visibility
        frame.setVisible(true);

        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isSolutionCorrect(cells, initialGrid)) {
                    JOptionPane.showMessageDialog(frame, "Congratulations! The solution is correct.");
                } else {
                    JOptionPane.showMessageDialog(frame, "The solution is incorrect. Please try again.");
                }
            }
        });

    }

    private static boolean isSolutionCorrect(JTextField[][] cells, int[][] initialGrid) {
        int[][] currentGrid = new int[GRID_SIZE][GRID_SIZE];

        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                try {
                    if (!cells[row][col].getText().isEmpty()) {
                        currentGrid[row][col] = Integer.parseInt(cells[row][col].getText());
                    } else {
                        return false; // Empty cell detected
                    }
                } catch (NumberFormatException ex) {
                    return false; // Invalid input detected
                }
            }
        }

        return isValidSudoku(currentGrid);
    }

    private static boolean isValidSudoku(int[][] grid) {
        // Check rows and columns
        for (int i = 0; i < GRID_SIZE; i++) {
            boolean[] rowCheck = new boolean[GRID_SIZE];
            boolean[] colCheck = new boolean[GRID_SIZE];
            for (int j = 0; j < GRID_SIZE; j++) {
                // Check row
                if (grid[i][j] != 0) {
                    if (rowCheck[grid[i][j] - 1]) {
                        return false;
                    }
                    rowCheck[grid[i][j] - 1] = true;
                }

                // Check column
                if (grid[j][i] != 0) {
                    if (colCheck[grid[j][i] - 1]) {
                        return false;
                    }
                    colCheck[grid[j][i] - 1] = true;
                }
            }
        }

        // Check subgrids
        for (int row = 0; row < GRID_SIZE; row += SUBGRID_SIZE) {
            for (int col = 0; col < GRID_SIZE; col += SUBGRID_SIZE) {
                if (!isValidSubgrid(grid, row, col)) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean isValidSubgrid(int[][] grid, int startRow, int startCol) {
        boolean[] subgridCheck = new boolean[GRID_SIZE];
        for (int row = 0; row < SUBGRID_SIZE; row++) {
            for (int col = 0; col < SUBGRID_SIZE; col++) {
                int value = grid[startRow + row][startCol + col];
                if (value != 0) {
                    if (subgridCheck[value - 1]) {
                        return false;
                    }
                    subgridCheck[value - 1] = true;
                }
            }
        }
        return true;
    }
}
