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
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import clientside.Player;
import networking.frontend.NetworkManagementPanel;

public class StartMenu extends JPanel{
	
	JFrame frame  = new JFrame("Game");
	JPanel panelCont = new JPanel();
	JPanel startMenu = new JPanel();
	JPanel serverConnect = new JPanel();
	
	
	

	CardLayout c1 = new CardLayout();
	
	public StartMenu() {
		//Very First Menu
		panelCont.setLayout(c1);;
		JLabel title = new JLabel();
		startMenu.setLayout(new BoxLayout(startMenu,BoxLayout.Y_AXIS));
		startMenu.setBackground(Color.black);
		startMenu.setPreferredSize(new Dimension(1200,800));
		
		//title
		title.setFont(new Font("arial", Font.BOLD,100));
		title.setSize(new Dimension(300,200));
		title.setForeground(Color.WHITE);
		title.setText("FNOLGO XV");
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		startMenu.add(title,startMenu);
		
		
		JButton playButton = new JButton("Play");
		playButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		playButton.setSize(new Dimension(100,50));
		startMenu.add(playButton,startMenu);
		
		JButton instructionButton = new JButton("Instructions");
		instructionButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		instructionButton.setPreferredSize(new Dimension(200,100));
		startMenu.add(instructionButton,startMenu);
		
		//Instructions Panel
		JPanel Instructions = new JPanel();
		Instructions.setPreferredSize(new Dimension(1200,800));
		Instructions.setLayout(new BoxLayout(Instructions,BoxLayout.Y_AXIS));
		Instructions.setBackground(Color.BLACK);
		
		JButton iback = new JButton("Back");
		iback.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JLabel howToPlay = new JLabel();
		JLabel iTitle = new JLabel();
		howToPlay.setForeground(Color.WHITE);
		iTitle.setForeground(Color.WHITE);
		iTitle.setFont(new Font("arial", Font.BOLD,75));
		iTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
		howToPlay.setFont(new Font("arial",Font.PLAIN,25));
		howToPlay.setAlignmentX(Component.LEFT_ALIGNMENT);

		Instructions.add(iTitle);
		Instructions.add(howToPlay);
		Instructions.add(iback);
		iTitle.setText("INSTRUCTIONS:");
		howToPlay.setText("WASD - movement");
		
		JPanel characterSelection = new JPanel();
		JButton characterSelectionButton = new JButton("Select Character");
		JButton cBack = new JButton("back");
		JLabel testText = new JLabel("HElloooo");
		testText.setForeground(Color.WHITE);
		characterSelection.add(testText);
		
		characterSelection.add(cBack);
		characterSelection.setLayout(new BoxLayout(characterSelection,BoxLayout.Y_AXIS));
		characterSelection.setPreferredSize(new Dimension(1200,800));
		characterSelection.add(characterSelectionButton);
		characterSelection.setBackground(Color.BLACK);
		
		try {
			Image Brute = ImageIO.read(new File("data/Brute/WW-Default.png"));
			Image Mage = ImageIO.read(new File("data/Mage/Mage.png"));
			Image Ranger = ImageIO.read(new File("data/Ranger/Ranger.png"));
			Brute = Brute.getScaledInstance(100,100,Image.SCALE_DEFAULT);
			Mage = Mage.getScaledInstance(100,100,Image.SCALE_DEFAULT);
			Ranger = Ranger.getScaledInstance(100,100,Image.SCALE_DEFAULT);
						
			JLabel bruteLabel = new JLabel(new ImageIcon(Brute));
			bruteLabel.setAlignmentY(Component.LEFT_ALIGNMENT);
			bruteLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
			
			JLabel mageLabel = new JLabel(new ImageIcon(Mage));
			mageLabel.setAlignmentY(Component.LEFT_ALIGNMENT);
			mageLabel.setAlignmentX(Component.LEFT_ALIGNMENT);


			JLabel rangerLabel = new JLabel(new ImageIcon(Ranger));
			rangerLabel.setAlignmentY(Component.LEFT_ALIGNMENT);
			rangerLabel.setAlignmentX(Component.LEFT_ALIGNMENT);


			characterSelection.add(bruteLabel);
			characterSelection.add(mageLabel);
			characterSelection.add(rangerLabel);
		} catch(IOException ex) {
			ex.printStackTrace();
		}
		
		
		GamePanel game = new GamePanel(false);
		NetworkManagementPanel nmp = new NetworkManagementPanel("Chat", 16, game);
		
		c1.show(panelCont, "1");
		
		panelCont.add(startMenu,"1");
		panelCont.add(Instructions,"1.5");
		panelCont.add(characterSelection, "2");
		panelCont.add(nmp, "3");
		
		//Interactions when the buttons are pressed
		playButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				c1.show(panelCont,"2");
			}
		});
		instructionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				c1.show(panelCont,"1.5");
			}
		});
		playButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				c1.show(panelCont,"2");
			}
		});
		cBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				c1.show(panelCont,"1");
			}
		});
		characterSelectionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				c1.show(panelCont,"3");
			}
		});
		
		frame.add(panelCont);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
	
		
	}
	
	
}
