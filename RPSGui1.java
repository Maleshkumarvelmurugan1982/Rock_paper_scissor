import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class RPSGui1 extends JFrame implements ActionListener {

    private int wins = 0, losses = 0, draws = 0;
    private int rock = 0, paper = 0, scissor = 0;

    private JLabel systemChoiceLabel, resultLabel, statsLabel;
    private JLabel systemImageLabel, playerImageLabel;
    private JButton rockButton, paperButton, scissorButton, exitButton;

    private Random rand = new Random();

    private ImageIcon rockIcon = new ImageIcon("rock.png");
    private ImageIcon paperIcon = new ImageIcon("paper.png");
    private ImageIcon scissorIcon = new ImageIcon("scissor.png");

    public RPSGui1() {
        setTitle("Rock Paper Scissors");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center it

        // Layout
        setLayout(new BorderLayout());

        // Top Panel
        JPanel titlePanel = new JPanel();
        JLabel title = new JLabel("Rock, Paper, Scissors!");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        titlePanel.add(title);
        add(titlePanel, BorderLayout.NORTH);

        // Image Panel
        JPanel imagePanel = new JPanel(new GridLayout(1, 2));
        playerImageLabel = new JLabel();
        systemImageLabel = new JLabel();

        playerImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        systemImageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        imagePanel.add(playerImageLabel);
        imagePanel.add(systemImageLabel);

        add(imagePanel, BorderLayout.CENTER);

        // Info Panel
        JPanel infoPanel = new JPanel(new GridLayout(3, 1));
        systemChoiceLabel = new JLabel("System hasn't played yet.", SwingConstants.CENTER);
        resultLabel = new JLabel("Make your move.", SwingConstants.CENTER);
        statsLabel = new JLabel(statsText(), SwingConstants.CENTER);

        infoPanel.add(systemChoiceLabel);
        infoPanel.add(resultLabel);
        infoPanel.add(statsLabel);

        add(infoPanel, BorderLayout.SOUTH);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        rockButton = new JButton("Rock");
        paperButton = new JButton("Paper");
        scissorButton = new JButton("Scissor");
        exitButton = new JButton("Exit");

        rockButton.addActionListener(this);
        paperButton.addActionListener(this);
        scissorButton.addActionListener(this);
        exitButton.addActionListener(e -> System.exit(0));

        buttonPanel.add(rockButton);
        buttonPanel.add(paperButton);
        buttonPanel.add(scissorButton);
        buttonPanel.add(exitButton);

        add(buttonPanel, BorderLayout.NORTH);

        setVisible(true);
    }

    private String statsText() {
        int total = wins + losses + draws;
        int score = (wins * 2) - losses - draws;
        String mostChosen = "None";
        int max = Math.max(rock, Math.max(paper, scissor));

        if (max > 1) {
            if (max == rock) mostChosen = "rock";
            else if (max == paper) mostChosen = "paper";
            else mostChosen = "scissor";
        }

        return "<html>Total Games: " + total +
               "<br>Wins: " + wins +
               " | Losses: " + losses +
               " | Draws: " + draws +
               "<br>Score: " + score +
               "<br>System picked " + mostChosen + " most (" + max + " times)</html>";
    }

    private ImageIcon getIcon(String choice) {
        return switch (choice) {
            case "rock" -> rockIcon;
            case "paper" -> paperIcon;
            case "scissor" -> scissorIcon;
            default -> null;
        };
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String[] choices = {"rock", "paper", "scissor"};
        int systemIndex = rand.nextInt(3);
        String systemChoice = choices[systemIndex];

        int playerChoice = -1;
        String playerChoiceStr = "";

        if (e.getSource() == rockButton) {
            playerChoice = 0;
            playerChoiceStr = "rock";
        } else if (e.getSource() == paperButton) {
            playerChoice = 1;
            playerChoiceStr = "paper";
        } else if (e.getSource() == scissorButton) {
            playerChoice = 2;
            playerChoiceStr = "scissor";
        }

        // Count system choices
        switch (systemChoice) {
            case "rock" -> rock++;
            case "paper" -> paper++;
            case "scissor" -> scissor++;
        }

        String result;
        if (playerChoice == systemIndex) {
            result = "Draw!";
            draws++;
        } else if ((playerChoice == 0 && systemIndex == 2) ||
                   (playerChoice == 1 && systemIndex == 0) ||
                   (playerChoice == 2 && systemIndex == 1)) {
            result = "You Won!";
            wins++;
        } else {
            result = "You Lost!";
            losses++;
        }

        // Update labels and images
        systemChoiceLabel.setText("System chose: " + systemChoice);
        resultLabel.setText(result);
        statsLabel.setText(statsText());

        playerImageLabel.setIcon(getIcon(playerChoiceStr));
        systemImageLabel.setIcon(getIcon(systemChoice));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RPSGui1::new);
    }
}
