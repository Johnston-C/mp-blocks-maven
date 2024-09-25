package edu.grinnell.csc207.experiments;

import java.io.PrintWriter;
import edu.grinnell.csc207.blocks.AsciiBlock;
import edu.grinnell.csc207.blocks.BezierCurveStamp;
import edu.grinnell.csc207.blocks.Boxed;
import edu.grinnell.csc207.blocks.Grid;
import edu.grinnell.csc207.blocks.HAlignment;
import edu.grinnell.csc207.blocks.HComp;
import edu.grinnell.csc207.blocks.HFlip;
import edu.grinnell.csc207.blocks.Line;
import edu.grinnell.csc207.blocks.Lines;
import edu.grinnell.csc207.blocks.Rect;
import edu.grinnell.csc207.blocks.Surrounded;
import edu.grinnell.csc207.blocks.Trimmed;
import edu.grinnell.csc207.blocks.VAlignment;
import edu.grinnell.csc207.blocks.VComp;

/**
 * Experiments with ASCII blocks.
 *
 * @author Samuel A. Rebelsky
 * @author Cade Johnston
 * @author Nicky Moreno Gonzalez
 */
public class Blocks {
  /**
   * Print a separator.
   *
   * @param pen
   *   What we use to print the separator.
   */
  static void separator(PrintWriter pen) {
    pen.printf("\n%s\n\n", "=".repeat(60));
  } // separator(PrintWriter)

  /**
   * Print a single AsciiBlock with a separator and a caption.
   *
   * @param pen
   *   The PrintWriter to use for printing.
   * @param caption
   *   The caption to print.
   * @param block
   *   The block to print.
   */
  static void figure(PrintWriter pen, String caption, AsciiBlock block) {
    separator(pen);
    pen.println(caption);
    pen.println("-".repeat(caption.length()));
    pen.println();
    AsciiBlock.print(pen, block);
  } // figure

  /**
   * Run the experiments.
   *
   * @param args
   *   The command-line parameters (ignored).
   */
  public static void main(String[] args) throws Exception {
    PrintWriter pen = new PrintWriter(System.out, true);

    Line line = new Line("Hello");
    Rect exes = new Rect('X', 3, 3);
    AsciiBlock boxedLine = new Boxed(line);
    AsciiBlock boxedboxedLine = new Boxed(boxedLine);
    AsciiBlock boxedExes = new Boxed(exes);

    pen.println("Original Values");
    figure(pen, "line", line);
    figure(pen, "exes", exes);
    figure(pen, "boxedLine", boxedLine);
    figure(pen, "boxedboxedLine", boxedboxedLine);
    figure(pen, "boxedExes", boxedExes);

    separator(pen);
    pen.println("After changing the line.");
    line.update("Goodbye");
    figure(pen, "line", line);
    figure(pen, "boxedLine", boxedLine);
    figure(pen, "boxedboxedLine", boxedboxedLine);

    separator(pen);
    pen.println("After widening exes");
    exes.wider();
    figure(pen, "exes", exes);
    figure(pen, "boxedExes", boxedExes);

    separator(pen);
    pen.println("After shortening exes");
    exes.shorter();
    figure(pen, "exes", exes);
    figure(pen, "boxedExes", boxedExes);

    separator(pen);
    pen.println("After making exes narrower twice and taller twice");
    exes.narrower();
    exes.narrower();
    exes.taller();
    exes.taller();
    figure(pen, "exes", exes);
    figure(pen, "boxedExes", boxedExes);

    separator(pen);
    pen.println("Multi-line boxes");
    figure(pen, "Using an array",
        new Lines(new String[] {"this", "and", "that", "or", "whatever"}));
    figure(pen, "Using a multi-line string",
        new Lines("""
                  multi-line strings
                  were
                  introduced
                  in
                  Java 13
                  """));
    figure(pen, "Using a string with newlines",
        new Lines("alpha\nbeta\ngamma\ndelta\nepsilon"));

    separator(pen);
    pen.println("Fun with horizontal composition");
    AsciiBlock a = new Rect('A', 5, 2);
    AsciiBlock b = new Rect('B', 3, 3);
    AsciiBlock c = new Rect('C', 2, 6);
    figure(pen, "a", a);
    figure(pen, "b", b);
    figure(pen, "c", c);
    figure(pen, "Top composition",
        new HComp(VAlignment.TOP, new AsciiBlock[] {a, b, c}));
    figure(pen, "Center composition",
        new HComp(VAlignment.CENTER, new AsciiBlock[] {a, b, c}));
    figure(pen, "Bottom composition",
        new HComp(VAlignment.BOTTOM, new AsciiBlock[] {a, b, c}));

    separator(pen);
    pen.println("Fun with vertical composition");
    AsciiBlock v1 = new Line("One");
    AsciiBlock v7 = new Line("Seven");
    AsciiBlock v11 = new Line("Eleven");
    AsciiBlock v19 = new Line("Nineteen");
    figure(pen, "v1", v1);
    figure(pen, "v7", v7);
    figure(pen, "v11", v11);
    figure(pen, "v19", v19);
    figure(pen, "Left composition",
        new VComp(HAlignment.LEFT, new AsciiBlock[] {v1, v7, v11, v19}));
    figure(pen, "Left composition",
        new VComp(HAlignment.CENTER, new AsciiBlock[] {v1, v7, v11, v19}));
    figure(pen, "Left composition",
        new VComp(HAlignment.RIGHT, new AsciiBlock[] {v1, v7, v11, v19}));

    separator(pen);
    AsciiBlock.print(pen, new Surrounded(new Surrounded(new Line("A"), 'B'), 'C'));
    separator(pen);
    AsciiBlock.print(pen, new Grid(new Line("Hello"), 3, 4));
    separator(pen);
    AsciiBlock one = new Grid(new Line("A"), 5, 2); // 5x2 grid of 'A'
    AsciiBlock two = new Grid(new Line("B"), 3, 3); // 3x3 grid of 'B'
    AsciiBlock three = new Grid(new Line("C"), 2, 6); // 2x6 grid of 'C'
    AsciiBlock.print(pen, new HComp(VAlignment.TOP, new AsciiBlock[] {one, two, three}));
    separator(pen);
    AsciiBlock.print(pen, new HComp(VAlignment.CENTER, new AsciiBlock[] {one, two, three}));
    separator(pen);
    AsciiBlock.print(pen, new HComp(VAlignment.BOTTOM, new AsciiBlock[] {one, two, three}));
    separator(pen);
    AsciiBlock.print(pen,
        new HFlip(new HComp(VAlignment.BOTTOM, new AsciiBlock[] {one, two, three})));
    separator(pen);
    AsciiBlock.print(pen,
        new Trimmed(new Surrounded(new Surrounded(new Line("A"), 'B'), 'C'),
            HAlignment.CENTER, VAlignment.CENTER, 3, 3));
    // AsciiBlock bg = new Rect(' ', 80, 24);
    separator(pen);
    AsciiBlock.print(pen,
        new BezierCurveStamp(new Grid(new Boxed(new Rect(' ', 1, 1)), 7, 7),
            'n', 2, 10, new int[]{0, 10, 20}, new int[]{0, 40, 0}));
    separator(pen);
    AsciiBlock.print(pen,
      new BezierCurveStamp(new Grid(new Boxed(new Rect(' ', 1, 1)), 7, 7),
          'n', 2, 10, new int[]{20, 10, 0}, new int[]{0, 40, 0}));
    separator(pen);
    AsciiBlock.print(pen,
        new BezierCurveStamp(new Rect(' ', 21, 21),
            '*', 2, 10, new int[]{0, 20, 20}, new int[]{0, 40, 0}));
    separator(pen);
    AsciiBlock.print(pen,
        new BezierCurveStamp(new Rect(' ', 21, 21),
            '*', 3, 10, new int[]{20, 20, 0, 0}, new int[]{20, 0, 20, 0}));
    separator(pen);
    AsciiBlock.print(pen,
        new BezierCurveStamp(new Rect(' ', 21, 21),
            '*', 3, 10, new int[]{10, 20, 20, 10}, new int[]{0, 0, 20, 20}));
    AsciiBlock.print(pen,
        new BezierCurveStamp(new Rect(' ', 21, 21),
            '*', 3, 100, new int[]{10, 20, 20, 10}, new int[]{0, 0, 20, 20}));
    separator(pen);
    AsciiBlock.print(pen,
        new BezierCurveStamp(new Rect(' ', 21, 21),
            '*', 1, 1, new int[]{10, 20, 20, 10}, new int[]{0, 0, 20, 20}));
    separator(pen);
    AsciiBlock.print(pen,
        new BezierCurveStamp(new Rect(' ', 21, 21),
            '*', 1, 10, new int[]{10, 20, 20, 10}, new int[]{0, 0, 20, 20}));
    separator(pen);
    AsciiBlock.print(pen,
        new BezierCurveStamp(new Rect(' ', 21, 21),
            '*', 3, 10, new int[]{10, 30, -10, 10}, new int[]{0, 0, 20, 20}));
    pen.close();
  } // main(String[])
} // class Blocks
