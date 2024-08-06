package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Game;
import model.IndexTuple;


// The Graphical User Interface displayed to user.
public class GameGUI extends JFrame {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    private static final String JSON_STORE = "./data/board.json";
    private Game game;
    private JPanel welcomePanel;
    private JPanel turnPanel;
    private JPanel savePanel;
    private BoardGUI boardGUI;
    private TurnGUI turnGUI;
    private CountGUI whiteCountGUI;
    private CountGUI blackCountGUI;
    


    public GameGUI() {
        super();
        setSize(WIDTH,HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        setBackground(Color.BLACK); 
        setVisible(true);
        displayWelcoming();
    }

    // MODIFIES: this
    // EFFECTS: sets up loading and color.
    //          sets game and loads if necessary. 
    private void displayWelcoming() { 
        welcomePanel = new JPanel();
        JLabel welcomeLabel = new JLabel("Welcome to othello.");
        JLabel loadLabel = new JLabel("Would you like to start a new game or load a previous one?");
        welcomePanel.add(welcomeLabel); 
        welcomePanel.add(loadLabel);

        JButton newGameBtn = new JButton("New Game");
        JButton oldGameBtn = new JButton("Previous Game");
        newGameBtn.setActionCommand("New Game");
        oldGameBtn.setActionCommand("Previous Game");
        newGameBtn.addActionListener(new WelcomeActionListener());
        oldGameBtn.addActionListener(new WelcomeActionListener());
        welcomePanel.add(newGameBtn);
        welcomePanel.add(oldGameBtn);
        welcomePanel.setPreferredSize(new Dimension(400,400));
        welcomePanel.updateUI();

        this.add(welcomePanel);
        this.pack();
        this.setResizable(false);
        this.setVisible(true);
    }



    // MODIFIES: this
    // EFFECTS: displays Jlabel showing who's turn it is.
    private void displayTurnPanel() {
        this.getContentPane().remove(welcomePanel);
        turnPanel = new JPanel();
        JLabel colorLabel = new JLabel("What color would you like to be?");
        turnPanel.add(colorLabel);

        JButton blackBtn = new JButton("Black");
        JButton whiteBtn = new JButton("White");
        blackBtn.setActionCommand("Black");
        whiteBtn.setActionCommand("White");
        blackBtn.addActionListener(new ColorActionListener());
        whiteBtn.addActionListener(new ColorActionListener());
        turnPanel.add(blackBtn);
        turnPanel.add(whiteBtn);
        turnPanel.setPreferredSize(new Dimension(400,400));
        turnPanel.updateUI();

        this.add(turnPanel);
        this.pack();
        this.setResizable(false);
        this.setVisible(true);

    }


    // MODIFIES: this
    // EFFECTS: sets up all parts of the UI
    private void initializeGameDisplay(boolean isNewGame, char playerColor) {
        this.getContentPane().remove(welcomePanel);
        if (turnPanel != null) {
            this.getContentPane().remove(turnPanel);
        }  
        this.game = new Game(isNewGame);
        if (isNewGame) {
            this.game.setBot(playerColor);
        }
        this.setUpBoardGUI();
        this.setUpTurnGUI();
        this.setUpWhiteCountGUI();
        this.setUpBlackCountGUI();
        this.setUpSaveBtn();

        this.displayGameComponents();

        this.gameStep();

    }


        // MODIFIES: this
    // EFFECTS: aligns and displays Board, Turn
    //          and Count GUIs on JFrame
    private void displayGameComponents() {
        this.setSize(new Dimension(800,800));
        // this.getContentPane().remove(welcomePanel);

        this.boardGUI.setBounds(100,200, 600,600);

        this.turnGUI.setBounds(350,40,100,50);

        this.whiteCountGUI.setBounds(200,20,30,30);
        this.blackCountGUI.setBounds(600,20,30,30);

        this.savePanel.setBounds(0,0, 300,100);
    }



    // REQUIRES: this.game to be initialzied.
    // MODIFIES: this
    // EFFECTS: creates Jpanel BoardGUI
    private void setUpBoardGUI() {
        System.out.println("Here");
        boardGUI = new BoardGUI(this,this.game); // extends JPanel
        this.add(boardGUI);
        this.pack();
        this.setVisible(true);
    }


    // MODIFIES: this
    // EFFECTS: constructs turn string at 
    //          top  of game display
    private void setUpTurnGUI() {
        this.turnGUI = new TurnGUI();
        this.add(turnGUI);
        this.pack();
        this.setVisible(true);

    }



    // MODIFIES: this
    // EFFECTS: constructs the count display of 
    //          the white pieces
    private void setUpWhiteCountGUI() {
        this.whiteCountGUI = new CountGUI(Color.WHITE, Color.BLACK);
        this.add(whiteCountGUI);
        this.pack();
        this.setVisible(true);
    }
    


        // MODIFIES: this
    // EFFECTS: constructs the count display of 
    //          the black pieces
    private void setUpBlackCountGUI() {
        this.blackCountGUI = new CountGUI(Color.BLACK, Color.WHITE);
        this.add(this.blackCountGUI);
        this.pack();
        this.setVisible(true);
    }



    // MODIFIES: this
    // EFFECTS: creates saveBtn and adds evenListener to it.
    private void setUpSaveBtn() {
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new SaveActionListener());
        this.savePanel = new JPanel();
        this.savePanel.add(saveButton);
        this.getContentPane().add(this.savePanel);
        this.setVisible(true);
    }



    // REQUIRES: sections of game to the displayed
    // MODIFIES: this
    // EFFECTS: rerenders new board and counts.
    private void updateGame() {
        System.out.println("Updating game");
        this.boardGUI.drawDisks();
        this.blackCountGUI.setCount(this.game.getBlackDiskCount());
        this.whiteCountGUI.setCount(this.game.getWhiteDiskCount());
        this.turnGUI.setTurn(this.game.getTurn());
        this.update(getGraphics());

    }


    // REQUIRES: prior call to initializeGameDisplay(), game's bot to be set.
    // MODIFIES: this
    // EFFECTS: wait for board to be clicked if players turn. Called after each move is made.
    public void gameStep() {
        if (!this.game.isGameOver()) {

            this.updateGame();
            if (this.game.getTurn() == this.game.getPlayerColor()) {
                this.boardGUI.initializeListener();
            } else {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (Exception e) {
                    System.out.println("Timer timed out.");
                } finally {
                    IndexTuple botMove = this.game.makeBotMove();
                    System.out.println("Bot made move at row: " + botMove.getRow()
                             + " column " + botMove.getCol() + ".");
    
                    this.gameStep();
                }


            }
        } else {
            this.updateGame();
            this.turnGUI.setBounds(250,60,300,50);
            this.turnGUI.setGameOver(game.getWinnerStatement());
        }
        
    }

    // Event handler for Jbutton's in displayWelcoming()
    public class WelcomeActionListener implements ActionListener {
        @Override
        // REQUIRES: e.getCommand() == "New Game" || "Previous Game"
        // MODIFIES: this
        // EFFECTS: initalizes or loads game depending on user prompt
        public void actionPerformed(ActionEvent e) {
            String actionCommand = e.getActionCommand();

            if (actionCommand.equals("New Game")) {
                displayTurnPanel();
            } else {
                initializeGameDisplay(false,' ');
            }


        }
    }
    
    public class ColorActionListener implements ActionListener {


        // REQUIRES: e.getCommand() == "Black" || "White"
        // MODIFIES: this
        // EFFECTS: sets color of user depending on button clicked.
        @Override
        public void actionPerformed(ActionEvent e) {
            String actionCommand = e.getActionCommand();
        
            if (actionCommand.equals("Black")) {
                initializeGameDisplay(true, 'B');
            } else {
                initializeGameDisplay(true,'W');
            } 
        }
    }

    public class SaveActionListener implements ActionListener {
        
   
        // EFFECTS: saves game into JSON file. 
        //          player can continue playing after.
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!game.isGameOver()) {
                try {
                    System.out.println("Saving game.");
                    game.saveGame(JSON_STORE);
                } catch (FileNotFoundException exception) {
                    System.out.println(JSON_STORE + " is not appropriate file destination.");
                }
            }
        }

    }
}


