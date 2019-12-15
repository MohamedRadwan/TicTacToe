package view;

import controller.TicTacToeController;
import model.BoardListener;
import model.Status;
import model.TicTacToeEvent;
import model.TicTacToeModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

/**
 * The view for the tictactoe game.
 */
public class TicTacToeView extends JFrame implements ActionListener, BoardListener {
    /**
     * Menu Bar which contains options for the user
     */
    private JMenuBar menuBar;
    /**
     * Display's the current status of the game.
     */
    private JTextField statusDisplay;
    /**
     * JMenuItem used to save the game.
     */
    private JMenuItem save;
    /**
     * JMenuItem used to load a saved game.
     */
    private JMenuItem load;
    /**
     * JMenuItem used to save the game.
     */
    private JMenuItem reset;
    /**
     * JMenuItem used to quit the game.
     */
    private JMenuItem quit;
    /**
     * An array of buttons that will be added to the board.
     */
    private JButton[][] buttons;
    /**
     * A model object.
     */
    private TicTacToeModel model;
    /**
     * A controller object.
     */
    private TicTacToeController controller;
    /**
     * Used to save and load the game.
     */
    private JFileChooser jFileChooser;

    /**
     * Instantiates a new Tic tac toe view.
     */
    public TicTacToeView() {
        this.setTitle("TicTacToe");
        this.model = new TicTacToeModel();
        this.model.addListeners(this);
        this.controller = new TicTacToeController(model);

        this.menuBar = new JMenuBar();
        this.statusDisplay = new JTextField();
        this.statusDisplay.setEditable(false);
        this.statusDisplay.setFont(new Font("Arial", Font.PLAIN, 16));

        this.statusDisplay.setText(model.getStatus().toString() + " (O ---- Turn)");

        this.load = new JMenuItem("Load");
        this.save = new JMenuItem("Save");
        this.reset = new JMenuItem("Reset");
        this.quit = new JMenuItem("Quit");

        this.menuBar.add(statusDisplay);
        this.menuBar.add(reset);
        this.menuBar.add(load);
        this.menuBar.add(save);
        this.menuBar.add(quit);

        this.setJMenuBar(menuBar);
        this.setLayout(new GridLayout(TicTacToeModel.SIZE, TicTacToeModel.SIZE));

        this.buttons = new JButton[TicTacToeModel.SIZE][TicTacToeModel.SIZE];
        for (int y = 0; y < TicTacToeModel.SIZE; y++) {
            for (int x = 0; x < TicTacToeModel.SIZE; x++) {
                this.buttons[x][y] = new JButton();
                this.buttons[x][y].setOpaque(false);
                this.buttons[x][y].setContentAreaFilled(false);
                this.buttons[x][y].setFocusPainted(false);
                this.buttons[x][y].setFont(new Font("Arial", Font.PLAIN, 160));
                add(buttons[x][y]);
                final int xCopy = x;
                final int yCopy = y;
                buttons[x][y].addActionListener(e -> {
                    controller.move(xCopy, yCopy);
                });
            }
        }
        this.setMinimumSize(new Dimension(600, 600));
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);


        this.load.addActionListener(this);
        this.save.addActionListener(this);
        this.reset.addActionListener(this);
        this.quit.addActionListener(this);
        this.jFileChooser = new JFileChooser(".");

    }

    /**
     * Starts the TicTacToe Game.
     *
     * @param args The command-line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(TicTacToeView::new);
    }

    /**
     * updates the view when a new game is being loaded.
     */
    private void updateView() {
        for (int y = 0; y < TicTacToeModel.SIZE; y++) {
            for (int x = 0; x < TicTacToeModel.SIZE; x++) {
                buttons[x][y].setText(model.getValueOfGrid(x, y));
            }
        }
        if (model.getStatus() == Status.IN_GAME) {
            statusDisplay.setText(
                    this.model.getStatus().toString() + (model.isTurn() ? " (O ---- Turn)" : " (X ---- Turn)"));
            configureBoardButtons(true);
        } else {
            statusDisplay.setText(this.model.getStatus().toString());
            configureBoardButtons(false);
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == reset) {
            this.model = controller.reset();
            this.model.addListeners(this);
            updateView();
        } else if (e.getSource() == quit) {
            System.exit(0);
        } else if (e.getSource() == save) {
            if (jFileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                        new FileOutputStream(jFileChooser.getSelectedFile()))) {
                    objectOutputStream.writeObject(this.model);
                } catch (IOException exception) {
                    JOptionPane.showMessageDialog(this, "Error has occurred", "Attention",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } else if (e.getSource() == load) {
            if (jFileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                try (ObjectInputStream objectInputStream = new ObjectInputStream(
                        new FileInputStream(jFileChooser.getSelectedFile()))) {
                    this.model = controller.load((TicTacToeModel) objectInputStream.readObject());
                    this.model.newBoardListenerList();
                    this.model.addListeners(this);
                    updateView();
                } catch (IOException | ClassNotFoundException exception) {
                    JOptionPane.showMessageDialog(this, "A Problem has occurred. Please Check if you have selected the correct file.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    @Override
    public void handleTicTacToeEvent(TicTacToeEvent event) {
        // updates the text on the button.
        buttons[event.getX()][event.getY()].setText(model.getValueOfGrid(event.getX(), event.getY()));
        // Checks the status of the game.
        if (event.getStatus() != Status.IN_GAME) {
            this.statusDisplay.setText(event.getStatus().toString());
            configureBoardButtons(false);
        } else {
            statusDisplay.setText(event.getStatus().toString() + (event.getTurn() ? " (O ---- Turn)" : " (X ---- Turn)"));
        }
    }

    /**
     * Enables or disables the buttons based on the passed condition.
     *
     * @param condition
     */
    private void configureBoardButtons(boolean condition) {
        for (int y = 0; y < TicTacToeModel.SIZE; y++) {
            for (int x = 0; x < TicTacToeModel.SIZE; x++) {
                this.buttons[x][y].setEnabled(condition);
            }
        }
    }

}
