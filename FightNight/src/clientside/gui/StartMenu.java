package clientside.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.OverlayLayout;

import gameplay.avatars.Brute;
import gameplay.avatars.Mage;
import gameplay.avatars.Ranger;
import networking.frontend.NetworkManagementPanel;
import processing.awt.PSurfaceAWT;

/**
 * Initiates a Start Menu. Creates a Java.awt start menu where character
 * selection, instructions, and the server connection is located
 * 
 * @author Jason Zhu
 *
 */
public class StartMenu extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8716227526013378891L;
	private JFrame frame = new JFrame("FNOLGO XV");
	private JPanel panelCont = new JPanel();
	private JPanel startMenu = new JPanel();

	private PSurfaceAWT surf; // These are the "portals" through which the PApplets draw on the canvas

	private CardLayout c1 = new CardLayout();


	/**
	 * 
	 * @param game
	 *            An instance of the game panel class. This is what the start menu
	 *            is connected to in which the game runs.
	 */
	public StartMenu(GamePanel game) {

		// FIRST SCREEN
		panelCont.setLayout(c1);
		JLabel title = new JLabel();
		startMenu.setLayout(new BoxLayout(startMenu, BoxLayout.Y_AXIS));
		startMenu.setOpaque(false);
		startMenu.setPreferredSize(new Dimension(1200, 800));

//
//		ImageIcon backgroundImage = new ImageIcon("Background"); 
//		JLabel background = new JLabel();
//
//		background.setIcon(backgroundImage);	System.out.println(background);
//		startMenu.add(background);

		// TITLE LABEL
		title.setFont(new Font("gabriola", Font.BOLD, 100));
		title.setSize(new Dimension(300, 200));
		title.setForeground(Color.WHITE);
		title.setText("FNOLGO XV");
		title.setAlignmentX(Component.CENTER_ALIGNMENT);

		// PLAY BUTTON
		JButton playButton = new JButton("Play");
		playButton.setFont(new Font("gabriola", Font.BOLD, 25));
		playButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		// playButton.setSize(new Dimension(100, 50));

		// INSTRUCTION BUTTON
		JButton instructionButton = new JButton("Instructions");
		instructionButton.setFont(new Font("gabriola", Font.BOLD, 25));
		instructionButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		// instructionButton.setForeground(Color.);
		instructionButton.setOpaque(false);
		instructionButton.setContentAreaFilled(false);
		instructionButton.setBorderPainted(true);
		// instructionButton.setPreferredSize(new Dimension(200, 100));

		startMenu.add(title, startMenu);
		startMenu.add(playButton, startMenu);
		startMenu.add(instructionButton, startMenu);
		
		// BACKGROUND IMAGE
		JPanel backgroundPanel = new JPanel();
		backgroundPanel.setLayout(new OverlayLayout(backgroundPanel));
		backgroundPanel.setMaximumSize(new Dimension(1200,800));
		
		try {
			Image backgroundImage = ImageIO.read(new File("data/Background.jpg"));
			//backgroundImage = backgroundImage.getScaledInstance(1200, 800, Image.SCALE_DEFAULT);
			JLabel background = new JLabel(new ImageIcon(backgroundImage));
			backgroundPanel.add(startMenu);
			backgroundPanel.add(background);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		


		// ------------------
		// Instructions Panel
		// ------------------
		JPanel Instructions = new JPanel();
		Instructions.setPreferredSize(new Dimension(1200, 800));
		Instructions.setLayout(new BoxLayout(Instructions, BoxLayout.Y_AXIS));
		Instructions.setBackground(Color.BLACK);

		// BACK BUTTON
		JButton iback = new JButton("Back");
		iback.setAlignmentX(Component.LEFT_ALIGNMENT);

		// INSTRUCTIONS TEXT
		JTextArea howToPlay = new JTextArea();
		howToPlay.setForeground(Color.WHITE);
		howToPlay.setFont(new Font("gabriola", Font.PLAIN, 25));
		howToPlay.setAlignmentX(Component.LEFT_ALIGNMENT);
		howToPlay.setText(
				"The objective of the game is to be the last remaining Avatar on the map. You are in an arena with up to 16 \nother players, each "
						+ "with 4 lives. Use your abilities to take down your opponents and be the last one \nstanding in this contest of champions!\n\nCONTROLS:\n"
						+ "WASD - movement" + "\nSpace - Dash in direction of movement\n" + "Q- Shield\n"
						+ "Mouse - Aim\n" + "Left Mouse Button - Basic Attack\n"
						+ "Right Mouse Button - Ranged Attack\n" + "E - Ability 1\n" + "R - Ability 2\n"
						+ "F - Ability 3");
		howToPlay.setBackground(Color.BLACK);
		howToPlay.setSelectedTextColor(Color.WHITE);
		howToPlay.setEditable(false);
		// howToPlay.setText("HI");

		// TITLE LABEL
		JLabel iTitle = new JLabel();
		iTitle.setForeground(Color.WHITE);
		iTitle.setFont(new Font("gabriola", Font.BOLD, 75));
		iTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
		iTitle.setText("INSTRUCTIONS:");

		Instructions.add(iTitle);
		Instructions.add(howToPlay);
		Instructions.add(iback);

		// ------------------------
		// CHARACTER SELECTION PANEL
		// -------------------------
		JPanel characterSelection = new JPanel();
		JButton characterSelectionButton = new JButton("Select Character");
		characterSelection.setLayout(new BoxLayout(characterSelection, BoxLayout.Y_AXIS));
		characterSelection.setPreferredSize(new Dimension(1200, 800));
		characterSelection.setBackground(Color.BLACK);

		JButton cBack = new JButton("Back");

		JLabel selected = new JLabel();
		selected.setForeground(Color.WHITE);
		selected.setFont(new Font("gabriola", Font.PLAIN, 25));
		selected.setAlignmentX(Component.LEFT_ALIGNMENT);
		selected.setText("Selected: " + game.getPlayer().getAvatar().toString());

		try {
			Image Brute = ImageIO.read(new File("data/Brute/WW-Default.png"));
			Image Mage = ImageIO.read(new File("data/Mage/Mage.png"));
			Image Ranger = ImageIO.read(new File("data/Ranger/Ranger.png"));
			Brute = Brute.getScaledInstance(100, 100, Image.SCALE_DEFAULT);
			Mage = Mage.getScaledInstance(100, 100, Image.SCALE_DEFAULT);
			Ranger = Ranger.getScaledInstance(100, 100, Image.SCALE_DEFAULT);

			JLabel bruteLabel = new JLabel(new ImageIcon(Brute));
			bruteLabel.setAlignmentY(Component.LEFT_ALIGNMENT);
			bruteLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
			bruteLabel.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent arg0) {
					game.getPlayer().setAvatar(new Brute());
					selected.setText("Selected: " + game.getPlayer().getAvatar().toString());
				}
			});

			JLabel mageLabel = new JLabel(new ImageIcon(Mage));
			mageLabel.setAlignmentY(Component.LEFT_ALIGNMENT);
			mageLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
			mageLabel.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent arg0) {
					game.getPlayer().setAvatar(new Mage());
					selected.setText("Selected: " + game.getPlayer().getAvatar().toString());
				}
			});

			JLabel rangerLabel = new JLabel(new ImageIcon(Ranger));
			rangerLabel.setAlignmentY(Component.LEFT_ALIGNMENT);
			rangerLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
			rangerLabel.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent arg0) {
					game.getPlayer().setAvatar(new Ranger());
					selected.setText("Selected: " + game.getPlayer().getAvatar().toString());
				}
			});

			JPanel nameSelection = new JPanel();
			nameSelection.setLayout(new BoxLayout(nameSelection, BoxLayout.Y_AXIS));
			JTextField nameSelect = new JTextField(12);
			JLabel username = new JLabel();
			username.setFont(new Font("gabriola", Font.PLAIN, 16));
			username.setText("Username");
			username.setForeground(Color.WHITE);
			nameSelect.setAlignmentX(Component.LEFT_ALIGNMENT);
			nameSelect.setHorizontalAlignment(JTextField.CENTER);
			nameSelect.setText("Username");
			
			characterSelectionButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ae){
					if(!nameSelect.getText().equals("Username"))
						game.getPlayer().setUsername(nameSelect.getText());
					else
						game.getPlayer().setUsername("");
				}
			});
			
			nameSelection.setPreferredSize(new Dimension(1200, 800));
			nameSelection.add(username);
			nameSelection.add(characterSelectionButton);
			nameSelection.setBackground(Color.BLACK);
			
			nameSelect.setMaximumSize(new Dimension(150, 30));
			
			characterSelection.add(cBack);
			characterSelection.add(characterSelectionButton);
			characterSelection.add(selected);
			characterSelection.add(bruteLabel);
			characterSelection.add(mageLabel);
			characterSelection.add(rangerLabel);

			nameSelection.add(nameSelect);
			characterSelection.add(nameSelection);
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		NetworkManagementPanel nmp = new NetworkManagementPanel("FNOLGO", 16, game, this);
		frame.add(nmp);

		c1.show(panelCont, "1");

		panelCont.add(backgroundPanel, "1");
		panelCont.add(Instructions, "1.5");
		panelCont.add(characterSelection, "2");
		panelCont.add(nmp, "3");

		// Interactions when the buttons are pressed
		playButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				c1.show(panelCont, "2");
			}
		});
		iback.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				c1.show(panelCont, "1");
			}
		});
		instructionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				c1.show(panelCont, "1.5");
			}
		});
		playButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				c1.show(panelCont, "2");
			}
		});
		cBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				c1.show(panelCont, "1");
			}
		});
		characterSelectionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				c1.show(panelCont, "3");
			}
		});

		frame.add(panelCont);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setResizable(true);
		frame.setVisible(true);

	}

	public CardLayout getCardLayout() {
		return c1;
	}

	public JPanel getPanel() {
		return panelCont;
	}

	public void fixProcessingPanelSizes(Component match) {
		surf.setSize(match.getWidth(), match.getHeight());
	}


}
