package edu.grinnell.csc207;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import edu.grinnell.csc207.blocks.AsciiBlock;
import edu.grinnell.csc207.blocks.BezierCurveStamp;
import edu.grinnell.csc207.blocks.Empty;
import edu.grinnell.csc207.blocks.Rect;
import edu.grinnell.csc207.blocks.VFlip;
/**
 * Tests of the new block.
 */
public class TestNewBlock {

  /** A curve over an empty block.
   * Name means "Bezier Curve Stamp Empty"
   */
  static AsciiBlock bcsE;

  /** A 5x5 Rect of the ' ' charcter.
   * Name means "Rect 5x5"
   */
  static AsciiBlock r5x5;

  /** A linear curve from top left corner to bottom right corner (5x5).
   * Name means "Bezier Curve Stamp Linear Top left Bottom right 5x5"
   */
  static AsciiBlock bcsLTlBr5x5;

  /** A linear curve from top right corner to bottom left corner (5x5).
   * Name means "Bezier Curve Stamp Linear Top right Bottom left 5x5"
   */
  static AsciiBlock bcsLTrBl5x5;

  /** A quadratic bezier curve from top left corner to bottom right corner (5x5)
   * Name means "Bezier Curve Stamp Quadratic Top left Bottom right 5x5"
   */
  static AsciiBlock bcsQTlBr5x5;

  /** A quadratic bezier curve from top left corner to bottom right corner (5x5)
   * Name means "Bezier Curve Stamp Quadratic Top right Bottom left 5x5"
   */
  static AsciiBlock bcsQTrBl5x5;

  /** A quadratic bezier curve stamp constructed with two curve stamps overlaid
   * Name means "Bezier Curve Stamp Quadratic Stamp over Stamp 5x5"
   */
  static AsciiBlock bcsQSoS5x5;

  /** A quadratic bezier curve stamp constructed with enough coordinates for two curves
   * Name means "Bezier Curve Stamp Quadratic Double Stamp 5x5"
   */
  static AsciiBlock bcsQDS5x5;

  // +----------------+
  // | Initialization |
  // +----------------+

  @BeforeAll
  public static void setup() throws Exception {
    try {
      // Globals for BezierCurveTest
      bcsE = new BezierCurveStamp(new Empty(), '*', 2, 10, new int[]{0, 2, 4}, new int[]{0, 6, 0});
      r5x5 = new Rect(' ', 5, 5);
      bcsLTlBr5x5 = new BezierCurveStamp(r5x5, '*', 1, 1, new int[]{0, 4}, new int[]{0, 4});
      bcsLTrBl5x5 = new BezierCurveStamp(r5x5, '*', 1, 1, new int[]{4, 0}, new int[]{0, 4});
      bcsQTlBr5x5 = new BezierCurveStamp(r5x5, '*', 2, 1, new int[]{0, 0, 4}, new int[]{0, 4, 4});
      bcsQTrBl5x5 = new BezierCurveStamp(r5x5, '*', 2, 1, new int[]{4, 4, 0}, new int[]{0, 4, 4});
      bcsQSoS5x5 = new BezierCurveStamp(new BezierCurveStamp(r5x5, '*', 2, 10, new int[]{0, 2, 4}, new int[]{2, 5, 2}), '*', 2, 10, new int[]{4, 2, 0}, new int[]{2, -1, 2});
      bcsQDS5x5 = new BezierCurveStamp(r5x5, '*', 2, 10, new int[]{0, 2, 4, 2, 0}, new int[]{2, 5, 2, -1, 2});
    } catch (Exception e) {
      // Do nothing; we shouldn't get exceptions.
    } // try/catch
  } // setup()

  // +-------+
  // | Tests |
  // +-------+

  /**
   * Are mirrored BezierCurveStamps whose points are mirrors equal?
   */
  @Test
  public void mirroredBCS() {
    assertTrue(TestUtils.same(bcsLTlBr5x5,new VFlip(bcsLTrBl5x5)), "Horizontally mirrored are equal");
  } // placeholder()

  /**
   * Are the first and last points included?
   */
  @Test
  public void firstLastIncluded() throws Exception {
    assertEquals('*', bcsLTlBr5x5.row(0).charAt(0), "First point is included (Linear)");
    assertEquals('*', bcsLTlBr5x5.row(4).charAt(4), "Last point is included (Linear)");
    assertEquals('*', bcsQTlBr5x5.row(0).charAt(0), "First point is included (Quadratic)");
    assertEquals('*', bcsQTlBr5x5.row(4).charAt(4), "Last point is included (Quadratic)");
  } // placeholder()

  /**
   * Are the middle points included in linear curves?
   */
  @Test
  public void middleIncluded() throws Exception {
    assertEquals('*', bcsLTlBr5x5.row(2).charAt(2), "First point is included (Linear)");
    assertEquals('*', bcsLTlBr5x5.row(2).charAt(2), "Last point is included (Linear)");
    assertEquals('*', bcsLTrBl5x5.row(2).charAt(2), "First point is included (Linear)");
    assertEquals('*', bcsLTrBl5x5.row(2).charAt(2), "Last point is included (Linear)");
  } // placeholder()

  /**
   * Are parts of the stamp outside of the block parameter's range not included?
   */
  @Test
  public void empty() {
    assertEquals("",TestUtils.toString(bcsE));
  }

  /**
   * Do multi-curves work?
   * Are they equivalent to layered stamps?
   */
  @Test
  public void multiCurve() {
    assertEquals(TestUtils.toString(bcsQSoS5x5), TestUtils.toString(bcsQDS5x5));
  }

} // class TestNewBlock
