/**********************
 * @author   Luke Tao
 * @due date  Wednesday, May 23rd
 * @description   This class contains the methods that will encrypt and 
 * decrypt the inputs from the user from the resource folder. The program offers the user with the choice
 * of level of encryption in 2 ways, xor gate and data scrambling using bit wise operators, which the users have
 * full access to control.
 
 *********************/

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.Arrays;
import javax.imageio.*;
import javax.swing.*;


public class Crypto{
	/**
	 * This method encodes an image inside another image by scrambling the ARGB data for each pixel and possibly applying xor depeding on the user's choice.
	 * This file first reads in the files, compiles them to an array, encrypt, and output.
	 * @param  String name1
	 * @param  String name2
	 * @param  String key
	 * @param  String indvar
	 */
	public static void encode(String name1, String name2,int key, int indvar)
	{
		int depvar = 8-indvar;
		BufferedImage img = null;	
		BufferedImage img2 = null;
		BufferedImage imgp2 = null;

		int width = 0;
		int height = 0;

		int width2 = 0;
		int height2 =0;

		int widthu = 0;
		int heightu = 0;

		int[][] memo = null;
		int [][] memo2 = null;

		try {
			int a = 0;
			img = ImageIO.read(new File(name1)); //read in a file
			img2 = ImageIO.read(new File(name2));

			width = img.getWidth();
			height = img.getHeight();

			width2 = img2.getWidth();
			height2 = img2.getHeight();

			// get the min dimension of the image.
			if ( width2<width )
				widthu = width2;
			else
				widthu = width;

			if ( height2<height )
				heightu = height2;
			else
				heightu = height;

			imgp2 = org.imgscalr.Scalr.resize(img2, org.imgscalr.Scalr.Mode.FIT_EXACT, widthu, heightu);
			memo = new int[width*height][4]; //attain its dimension
			widthu = imgp2.getWidth();
			heightu = imgp2.getHeight();
			memo2 = new int[widthu*heightu][4]; //attain its dimension
			for(int i = 0;i < width ;i++)
			{
				for(int k = 0;k< height; k++) //save the bufferedimage to an 2d array for later use
				{
					memo[a][0] = (new Color(img.getRGB(i, k))).getAlpha();
					memo[a][1] = (new Color(img.getRGB(i, k))).getRed();
					memo[a][2] = (new Color(img.getRGB(i, k))).getGreen();
					memo[a][3] = (new Color(img.getRGB(i, k))).getBlue();
					a++;
				}
			}
			a = 0;
			for(int i = 0;i < widthu ;i++)
			{
				for(int k = 0;k< heightu; k++) //save the bufferedimage to an 2d array for later use
				{
					memo2[a][0] = ((new Color(imgp2.getRGB(i, k))).getAlpha());
					memo2[a][1] = (new Color(imgp2.getRGB(i, k))).getRed();
					memo2[a][2] = (new Color(imgp2.getRGB(i, k))).getGreen();
					memo2[a][3] = (new Color(imgp2.getRGB(i, k))).getBlue();
					a++;
				}
			}

		} catch (IOException e) {
			System.out.println("cant find file");
		}
		BufferedImage make = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB); //create the new image


		int tmp = 0;
		int post = 0;
		int tmp2 = 0;
		int afinal = 0;
		int rfinal = 0;
		int gfinal = 0;
		int bfinal = 0;
		int counter = 0;
		int prop = (width*height)/(heightu*widthu); //find the ratio between the 2 images.

		for(int i = 0;i < width ;i++)
		{
			for(int k = 0;k< height; k++)
			{
				if ( tmp < heightu* (widthu) && counter% prop == 0)
				{
					afinal = ((((memo[tmp2][0] >> indvar) << indvar) | ((memo2[tmp][0]>>depvar)))<<24); //encrypt
					rfinal = ((((memo[tmp2][1] >> indvar) << indvar) | ((memo2[tmp][1]>>depvar)))<<16);
					gfinal = ((((memo[tmp2][2] >> indvar) << indvar) | ((memo2[tmp][2]>>depvar)))<<8);
					bfinal = ((((memo[tmp2][3] >> indvar) << indvar) | ((memo2[tmp][3]>>depvar)))<<0);
					tmp++;
					post = (afinal | rfinal | gfinal | bfinal);  
				}
				else {	
					post = (memo[tmp2][0]<<24) | (memo[tmp2][1]<<16) | (memo[tmp2][2]<<8) | (memo[tmp2][3]);//pass over the original data
				}
				make.setRGB(i, k, post^key);
				tmp2++;
				counter++;
			}
		}
		File output = null;
		String home = System.getProperty("user.home"); //find the path to output
		try
		{ 
			JOptionPane.showMessageDialog(null, " please note your target image had dimensions width: " + widthu + "pixels and height " + heightu + "pixles, this will be needed to decrypt the target image from the background image");
			output = new File(home + File.separator + "Desktop" + File.separator + "output.bmp");  //output the resulting image
			ImageIO.write(make, "BMP", output); 
		} 
		catch(IOException e) 
		{ 
			System.out.println("Error: " + e); 
		} 
	}
	/**
	 * This method decrypts the data from an image using the inputs from the user and reversing the mechanism for xor and pixl data scrambling and outputs the file to the desktop.
	 * @param  String name
	 * @param  int width2
	 * @param  int height2
	 * @param  int key
	 * @param  int indvar
	 */
	public static void decode(String name, int width2, int height2,int key, int indvar)
	{
		BufferedImage tgt = null;
		int height1;
		int width1;
		int ratio;
		int depvar = 8-indvar; //the unshifted figures
		try {
			tgt = ImageIO.read(new File(name));
		} catch (IOException e) {
			System.out.println("Can't find file");
		}


		height1 = tgt.getHeight();
		width1 = tgt.getTileWidth();

		ratio = (height1*width1)/(height2*width2); //find ratio

		int[][] memo = new int[height1*width1][4];
		int a = 0;
		int b = 0;

		int xo = 0;
		if (depvar == 0)
			xo = 255;
		else if(depvar ==1)
			xo = 127;
		else if(depvar ==2)
			xo = 63;
		else if(depvar ==3)
			xo = 31;
		else if(depvar ==4)
			xo = 15;
		else if(depvar ==5)
			xo = 7;
		else if(depvar ==6)
			xo = 3;
		else if(depvar ==7)
			xo = 1;
		else if(depvar ==8)
			xo = 0;

		for(int i = 0;i < width1 ;i++)
		{
			for(int k = 0;k< height1; k++) //save the bufferedimage to an 2d array for later use
			{

				if (a< (height2*width2)*ratio && a%ratio ==0)
				{
					tgt.setRGB(i, k, (tgt.getRGB(i, k))^key); //applying xor
					memo[b][0] = (((new Color(tgt.getRGB(i, k))).getAlpha()) & xo)<<depvar;
					memo[b][1] = (((new Color(tgt.getRGB(i, k))).getRed())& xo)<<depvar;
					memo[b][2] = (((new Color(tgt.getRGB(i, k))).getGreen())& xo)<<depvar;
					memo[b][3] = (((new Color(tgt.getRGB(i, k))).getBlue())& xo)<<depvar;
					b++;
				}
				a++;
			}
		}
		a=0;
		BufferedImage make = new BufferedImage(width2,height2,BufferedImage.TYPE_INT_RGB); //create the new image
		int afinal = 0;
		int rfinal = 0;
		int gfinal = 0;
		int bfinal = 0;
		int post = 0;
		for(int i = 0;i < width2 ;i++)
		{
			for(int k = 0;k< height2; k++)
			{
				afinal = (memo[a][0])<<24;
				rfinal = (memo[a][1])<<16;
				gfinal = (memo[a][2])<<8;
				bfinal = (memo[a][3]);
				a++;
				post = afinal | rfinal | gfinal | bfinal;  //paint the image with the modified pixels
				make.setRGB(i, k, post);
			}
		}




		File out = null;
		String home = System.getProperty("user.home");
		try
		{ 
			out = new File(home + File.separator + "Desktop" + File.separator + "decode.bmp"); 
			ImageIO.write(make, "BMP", out);  //output the file
		} 
		catch(IOException e) 
		{ 
			System.out.println("Error: " + e); 
		} 



	}
}
