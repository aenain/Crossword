/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import javax.swing.JComponent;
import javax.swing.RepaintManager;

/**
 * @inspiration http://www.apl.jhu.edu/~hall/java/Swing-Tutorial/Swing-Tutorial-Printing.html
 * @author arturhebda
 */
public class Printer implements Printable {
    private JComponent[] components;
    private String[] headers;

    public Printer(JComponent[] components, String[] headers) {
      this.components = components;
      this.headers = headers;
    }
  
    public void print() throws PrinterException {
        PrinterJob printJob = PrinterJob.getPrinterJob();
        printJob.setPrintable(this);

        if (printJob.printDialog())
            printJob.print();
    }

    @Override
    public int print(Graphics g, PageFormat pageFormat, int pageIndex) {
        if (pageIndex >= components.length)
            return NO_SUCH_PAGE;

        Graphics2D g2d = (Graphics2D)g;
        FontMetrics fontMetrics = g2d.getFontMetrics();

        g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
        g2d.drawString(headers[pageIndex], 0, fontMetrics.getAscent());

        int componentTopOffset = fontMetrics.getHeight() * 2;
        int componentWidth = (int) pageFormat.getImageableWidth();
        int componentHeight = (int) pageFormat.getImageableHeight() - componentTopOffset;


        Graphics2D g2dComponent = (Graphics2D) g2d.create(0, componentTopOffset, componentWidth, componentHeight);
        paintComponent(components[pageIndex], g2dComponent);
        
        return PAGE_EXISTS;
    }

    private void paintComponent(JComponent component, Graphics2D g2d) {
        disableDoubleBuffering(component);
        component.paint(g2d);
        enableDoubleBuffering(component);
    }

    public void disableDoubleBuffering(JComponent c) {
        RepaintManager currentManager = RepaintManager.currentManager(c);
        currentManager.setDoubleBufferingEnabled(false);
    }

    public void enableDoubleBuffering(JComponent c) {
        RepaintManager currentManager = RepaintManager.currentManager(c);
        currentManager.setDoubleBufferingEnabled(true);
    }
}