/**********************
 * @author   Luke Tao
 * @due date  Wednesday, May 23rd
 * @description   This file creates the GUI interface for the user to customize and 
 * access the steganography features. This interface allows the user to choose whether
 * to encrypt or decrypt and asks for the required inputs for the program.
 *********************/

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.Arrays;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.imageio.*;
import javax.swing.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class Interface extends Component {
	private String name1;
	private String name2;
	private int lvl;
	private int key;
	private String named;
	private int w;
	private int h;
	public static void main(String[] args)
	{
		JOptionPane.showMessageDialog(null, " Please make sure every file that you are accessing is in the resource folder associated with this java file before usage. thanks. --Luke Tao");
		new Interface();
	} ///
	/**
	 * This constructs the GUI for the user inputs.
	 */
	public Interface()
	{
		JFrame frame = new JFrame("ENCRYPT");
		frame.setSize(100,120);
		frame.setLayout(new GridLayout(5,1,3,3)); //creates the layout for the main frame


		JPanel set1 = new JPanel(); //name1
		JPanel set2 = new JPanel(); //name2
		JPanel set3 = new JPanel(); //key
		JPanel set4 = new JPanel(); //lvl
		JPanel set5 = new JPanel(); //submit
		JTextField n1 = new JTextField(10);
		JTextField n2 = new JTextField(10);
		JTextField n3 = new JTextField(10);

		final JTextField value = new JTextField(5);
		JSlider n4 = new JSlider(JSlider.HORIZONTAL,0,8,1); //slider with 8 settings of scrambling.
		n4.setValue(5);
		JTextField disn4 = new JTextField(3); //display the option currently selected
		disn4.setText("5");
		n4.setMajorTickSpacing(1); 
		n4.setPaintTicks(true);
		n4.setPaintLabels(true); //add ticks

		JButton but = new JButton("Submit");
		JButton butchanged = new JButton("DECRYPT instead"); //setting 2
		JButton info = new JButton("?"); // an information key that explains the config
		JButton instruction = new JButton("Instruction");
		
		set1.add(new JLabel("Background Image Name(without .bmp extension):"), BorderLayout.WEST); //input file 1
		set1.add(n1, BorderLayout.CENTER);
 
		set2.add(new JLabel("Target Image Name(without .bmp extension):"), BorderLayout.WEST); //input file 2
		set2.add(n2, BorderLayout.CENTER);

		set3.add(new JLabel("Key ^(integer only)"), BorderLayout.WEST); //xor encryption
		set3.add(n3, BorderLayout.CENTER);
		
		set4.add(info);
		set4.add(disn4);
		set4.add(n4);

		set5.add(instruction);
		set5.add(but);
		set5.add(butchanged);
		
		frame.add(set1);
		frame.add(set2);
		frame.add(set3);
		frame.add(set4);
		frame.add(set5);

		n4.addChangeListener(new ChangeListener() //makes the textfield display the selected config
		{
			public void stateChanged(ChangeEvent e)
			{
				disn4.setText(Integer.toString(n4.getValue()));
			}
		});
		but.addActionListener(new ActionListener(){ //use the data inputed from the user and pass them to the method

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				name1 = n1.getText() + ".bmp";
				name2 = n2.getText() + ".bmp";
				key = Integer.valueOf(n3.getText());
				lvl = n4.getValue();
				Crypto.encode(name1, name2, key, lvl);
				
				JOptionPane.showMessageDialog(null, "Success, the encrypted image can be found on your desktop named 'output.bmp'");
			}
		});
		info.addActionListener(new ActionListener(){ //provide the user with more information regarding the option

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JOptionPane.showMessageDialog(null, "this slider determines the level of scrambling of the images with 0 being most destructive to the target and 8 being the most destructive to the background");
			}
		});
		
		instruction.addActionListener(new ActionListener(){ //provide the user with more information regarding the option

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JOptionPane.showMessageDialog(null, "Welcome! This program allows you to encrypt an image inside another."
						+ "For the fields above, the background image represents the image that will be in the background and will remain after"
						+ "\n" + "the encryption. The target image represents the file that will be hidden into the background image. The key^ represents the "
						+ "key that could encrypt your image and background"+ "\n" + "image further at the cost of temporarly altering the images(Use 0 for default and no xor effect). The slider determines "
						+ "\n" + "how much the data is scrambled but more detail can be found in the ? key beside it. :D");
			}
		});
		JFrame dcode = new JFrame("DECRYPT"); //secondary frame for decryption
		dcode.setSize(100,120);
		dcode.setLayout(new GridLayout(6,1,3,3));
		JPanel d1 = new JPanel(); //name
		JPanel d2 = new JPanel(); //dimensionx
		JPanel d3 = new JPanel(); //dimensiony
		JPanel d4 = new JPanel(); //key
		JPanel d5 = new JPanel(); //lvl
		JPanel d6 = new JPanel(); //submit

		JTextField t1 = new JTextField(10);
		JTextField t2 = new JTextField(10);
		JTextField t3 = new JTextField(10);
		JTextField t4 = new JTextField(10);
		JSlider sliderb = new JSlider(JSlider.HORIZONTAL,0,8,1);
		sliderb.setMajorTickSpacing(1);
		sliderb.setPaintTicks(true);
		sliderb.setPaintLabels(true);
		sliderb.setValue(5);
		JTextField t5 = new JTextField(3);
		t5.setText("5");

		JButton butf = new JButton("Submit");
		JButton infof = new JButton("?");
		JButton encry = new JButton("ENCRYPT INSTEAD");
		JButton instruction2 = new JButton("Instruction");

		d1.add(new JLabel("File Name(Without .bmp extension: "), BorderLayout.WEST);
		d1.add(t1);

		d2.add(new JLabel("width of target image"), BorderLayout.WEST);
		d2.add(t2);

		d3.add(new JLabel("height of target image"), BorderLayout.WEST);
		d3.add(t3);

		d4.add(new JLabel("key^ (integer only)"), BorderLayout.WEST);
		d4.add(t4);

		d5.add(infof);
		d5.add(t5);
		d5.add(sliderb);
		
		d6.add(instruction2);
		d6.add(butf);
		d6.add(encry);
		
		dcode.add(d1);
		dcode.add(d2);
		dcode.add(d3);
		dcode.add(d4);
		dcode.add(d5);
		dcode.add(d6);
		
		butf.addActionListener(new ActionListener() //input the data from the user to the method
				{

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						named = t1.getText() + ".bmp";
						w = Integer.valueOf(t2.getText());
						h = Integer.valueOf(t3.getText());
						key = Integer.valueOf(t4.getText());					
						lvl = sliderb.getValue();
						Crypto.decode(named, w, h, key, lvl);
						JOptionPane.showMessageDialog(null, "Sucess, the file has been placed on your desktop named 'decode.bmp'");
					}
			
				});
		infof.addActionListener(new ActionListener() //display more information regarding the info
				{

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						JOptionPane.showMessageDialog(null, "Please select the level of scrambling when encrypting");
					}
			
				});
		instruction2.addActionListener(new ActionListener() //display more information regarding the info
				{

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						JOptionPane.showMessageDialog(null, "Welcome!"
								+ "\n"+ "The file name represents the file the target image is hidden in. "
								+ "The width and height represents the dimensions of the target image which were displayed when encrypted"
								+ "\n"+"The key must match the key you entered when encrypting(0 by default). The slider must match the setting when you "
								+ "were encrypting but more information regarding it can be found in the ? button beside it");
					}
			
				});
		encry.addActionListener(new ActionListener() //switch frame.
		{

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dcode.setVisible(false);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
				frame.setResizable(false);
			}
	
		});
		
		butchanged.addActionListener(new ActionListener(){ //switch frame
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				dcode.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				dcode.pack();
				dcode.setLocationRelativeTo(null);
				dcode.setVisible(true);
				dcode.setResizable(false);
				
			}
		});
		sliderb.addChangeListener(new ChangeListener() //display the value selected
		{
			public void stateChanged(ChangeEvent e)
			{
				t5.setText(Integer.toString(sliderb.getValue()));
			}
		});


		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //standard config for frames 
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setResizable(false);
	}


}
