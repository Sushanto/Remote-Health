package patientside;

import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Dimension;
import java.awt.Component;

/**
* Printer class to print the prescription
* @author Sushanto Halder
*/
public class Printer implements Printable
{
	Component comp;
	/**
	* Intialize the component to be print
	* @param comp Component to be print
	*/
	protected Printer(Component comp)
	{
		this.comp = comp;
	}

	/**
	* Set page and print
	* @param g Graphics object
	* @param format PageFormat object
	* @param page_index Index of the page
	*/
	@Override
	public int print(Graphics g,PageFormat format,int page_index)
	throws PrinterException
	{
		if(page_index > 0)
			return Printable.NO_SUCH_PAGE;

		Dimension dim = comp.getSize();
		double cheight = dim.getHeight();
		double cwidth = dim.getWidth();

		double pheight = format.getImageableHeight();
		double pwidth = format.getImageableWidth();

		double Xstart = format.getImageableX();
		double Ystart = format.getImageableY();

		double Xratio = pwidth/cwidth;
		double Yratio = pheight/cheight;

		Graphics2D g2 = (Graphics2D)g;
		g2.translate(Xstart,Ystart);
		g2.scale(Xratio,Yratio);
		comp.paint(g2);
		return Printable.PAGE_EXISTS;
	}
}
