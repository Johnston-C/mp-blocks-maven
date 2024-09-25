package edu.grinnell.csc207.main;

import edu.grinnell.csc207.blocks.AsciiBlock;
import edu.grinnell.csc207.blocks.BezierCurveStamp;
import edu.grinnell.csc207.blocks.HAlignment;
import edu.grinnell.csc207.blocks.HComp;
import edu.grinnell.csc207.blocks.HFlip;
import edu.grinnell.csc207.blocks.Rect;
import edu.grinnell.csc207.blocks.VAlignment;
import edu.grinnell.csc207.blocks.VComp;
import java.io.PrintWriter;

/**
 * Create and print an amazing 80x24 ASCII artwork.
 *
 * @author Your Name Here
 * @author Your Name Here
 */
public class Art80x24 {
  /**
   * Our cute Halloween pumpkin. 
   *
   * @param args
   *   Command-line arguments (currently ignored).
   *
   * @exception Exception
   *   If something goes wrong with one of the underlying classes.
   */
  public static void main(String[] args) throws Exception {
    PrintWriter pen = new PrintWriter(System.out, true);
    AsciiBlock halfCircle = new BezierCurveStamp(new Rect('^', 40, 18), ' ', 3, 40, new int[]{40, -8, -8, 40}, new int[]{0, 0, 16, 16});
    AsciiBlock stick1 = new Rect('^', 4, 2);
    AsciiBlock stick2 = new Rect('^', 36, 6);
    AsciiBlock stick = new HComp(VAlignment.TOP, new AsciiBlock[] {stick2, stick1});
    AsciiBlock assemble1 = new VComp(HAlignment.LEFT, new AsciiBlock[] {stick, halfCircle});
    AsciiBlock eyes = new BezierCurveStamp(assemble1, ' ', 3, 40, new int[]{28, 32, 35, 28}, new int[]{10, 5, 16, 10}) ;
    AsciiBlock smile = new BezierCurveStamp(eyes, ' ', 3, 40, new int[]{40, 18, 23, 40}, new int[]{16, 11, 20, 20}) ;
    AsciiBlock reversedHalf = new HFlip(smile);
    AsciiBlock art = new HComp(VAlignment.TOP, new AsciiBlock[] {smile, reversedHalf});
    AsciiBlock.print(pen, art);
    pen.close();
  } // main(String[])
} // class Art80x24
