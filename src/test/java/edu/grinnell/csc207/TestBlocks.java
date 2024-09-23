package edu.grinnell.csc207;

import edu.grinnell.csc207.blocks.AsciiBlock;
import edu.grinnell.csc207.blocks.Boxed;
import edu.grinnell.csc207.blocks.Empty;
import edu.grinnell.csc207.blocks.Line;
import edu.grinnell.csc207.blocks.Rect;
import edu.grinnell.csc207.blocks.Surrounded;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

/**
 * Tests of the various ASCII Blocks.
 */
public class TestBlocks {
  // +-------+-------------------------------------------------------
  // | Empty |
  // +-------+

  /**
   * Do we successfully build the empty block?
   */
  @Test
  public void testEmpty() {
    AsciiBlock empty = new Empty();
    assertEquals(0, empty.width(), 
        "R: Empty block has no width");
    assertEquals(0, empty.height(), 
        "R: Empty block has no height");
    assertEquals("", TestUtils.toString(empty),
        "R: Empty block has no contents");
  } // testEmpty()

  // +-------+-------------------------------------------------------
  // | Lines |
  // +-------+

  /**
   * Do we successfully build lines?
   */
  @Test
  public void testLine() throws Exception {
    Line line = new Line("Hello");
    assertEquals(5, line.width(),
        "R: Basic line has appropriate width");
    assertEquals(1, line.height(),
        "R: Basic line has appropriate height");
    assertEquals("Hello", line.row(0),
        "R: Basic line has appropriate contents");
  } // testLine()

  /**
   * Do we succesfully change lines?
   */
  @Test
  public void testLineChange() throws Exception {
    Line line = new Line("hi");
    assertEquals(2, line.width());
    assertEquals(1, line.height());
    assertEquals("hi", line.row(0));
    line.update("Good day");
    assertEquals(8, line.width());
    assertEquals(1, line.height());
    assertEquals("Good day", line.row(0));
  } // testLineChange()

  // +------------+--------------------------------------------------
  // | Rectangles |
  // +------------+

  /**
   * Do we successfully build rectangles?
   */
  @Test
  public void testRect() throws Exception {
    assertEquals("X\n", TestUtils.toString(new Rect('X', 1, 1)), 
        "R: 1x1 rectangle");
    assertEquals("""
                 YYY
                 YYY
                 """,
        TestUtils.toString(new Rect('Y', 3, 2)),
        "R: 3x2 rectangle");
  } // testRect()

  /**
   * Can we successfully change rectangles?
   */
  @Test
  public void testRectChange() throws Exception {
    Rect rect = new Rect('!', 3, 4);
    assertEquals("!!!\n!!!\n!!!\n!!!\n", TestUtils.toString(rect), 
        "R: original 3x4 rectangle");

    rect.wider();
    assertEquals("!!!!\n!!!!\n!!!!\n!!!!\n", TestUtils.toString(rect),
        "M: wider version of rectangle, now 4x4");

    rect.wider();
    assertEquals("!!!!!\n!!!!!\n!!!!!\n!!!!!\n", TestUtils.toString(rect),
        "M: even wider version of rectangle, now 5x4");

    rect.taller();
    assertEquals("!!!!!\n!!!!!\n!!!!!\n!!!!!\n!!!!!\n", 
        TestUtils.toString(rect),
        "M: taller version of rectangle, now 5x5");

    rect.shorter();
    rect.shorter();
    rect.shorter();
    assertEquals("!!!!!\n!!!!!\n", TestUtils.toString(rect),
        "M: much shorter version of rectangle, now 5x2");

    rect.shorter();
    assertEquals("!!!!!\n", TestUtils.toString(rect),
        "M: shortest version of rectangle, now 5x1");

    rect.shorter();
    assertEquals("!!!!!\n", TestUtils.toString(rect),
        "E: cannot shorten rectangle below 5x1");

    rect.taller();
    assertEquals("!!!!!\n!!!!!\n", TestUtils.toString(rect),
        "E: can make rectangle taller after shortening to base, now 5x2");

    rect.narrower();
    assertEquals("!!!!\n!!!!\n", TestUtils.toString(rect),
        "M: narrower version rectangle, now 4x2");

    rect.narrower();
    rect.narrower();
    rect.narrower();
    assertEquals("!\n!\n", TestUtils.toString(rect),
        "M: very narrow rectangle, now 1x2");

    rect.narrower();
    assertEquals("!\n!\n", TestUtils.toString(rect),
        "E: cannot make rectangle narrower than 1 column, still 1x2");

    rect.wider();
    assertEquals("!!\n!!\n", TestUtils.toString(rect),
        "E: widening after narrowing, now 2x2");

  } // testRectChange()

  // +-------+-------------------------------------------------------
  // | Boxed |
  // +-------+

  /**
   * Testing of basic boxed structures.
   */
  @Test
  void TestBoxedBasic() throws Exception {
    assertNotNull(new Boxed(new Line("Hello")),
        "R: Can box text lines");
    assertNotNull(new Boxed(new Rect('B', 3, 4)),
        "R: Can box rectangles");
  } // TestBoxedBasic()

  /**
   * Testing of basic boxed lines.
   */
  @Test
  void TestBoxedLine() {
    AsciiBlock boxedLine = new Boxed(new Line("Hello"));
    assertEquals(7, boxedLine.width(),
        "M: Correct width for a boxed line");
    assertEquals(3, boxedLine.height(),
        "M: Correct height for a boxed line");
    assertEquals("/-----\\\n|Hello|\n\\-----/\n", TestUtils.toString(boxedLine),
        "M: Correct contents for a boxed line");
  } // TestBoxedLine()

  /**
   * Testing of basic boxed rectangles.
   */
  @Test
  void TestBoxedRect() throws Exception {
    AsciiBlock boxedRect = new Boxed(new Rect('B', 3, 4));
    assertEquals(5, boxedRect.width(),
        "M: Correct width for a boxed rectangle");
    assertEquals(6, boxedRect.height(),
        "M: Correct height for a boxed rectangle");
    assertEquals("""
                 /---\\
                 |BBB|
                 |BBB|
                 |BBB|
                 |BBB|
                 \\---/
                 """,
        TestUtils.toString(boxedRect),
        "M: Correct contents for a boxed rectangle");
  } // TestBoxedRect()

  /**
   * Test of boxed lines that have been narrowed.
   */
  @Test
  public void testBoxedLineNarrower() {
    Line line = new Line("Hello");
    AsciiBlock boxedLine = new Boxed(line);

    line.update("Hi");
    assertEquals(4, boxedLine.width(),
        "E: Correct width after making boxed line narrower");
    assertEquals(3, boxedLine.height(),
        "E: Correct height after making boxed line narrower");
    assertEquals("/--\\\n|Hi|\n\\--/\n", TestUtils.toString(boxedLine),
        "E: Correct contents for narrowed boxed line");
  } // testBoxedLineNarrower()

  /**
   * Test of boxed lines that have been made wider.
   */
  @Test
  public void testBoxedLineWider() {
    Line line = new Line("Bye");
    AsciiBlock boxedLine = new Boxed(line);

    line.update("Goodbye");
    assertEquals(9, boxedLine.width(),
        "E: Correct width after making boxed line wider");
    assertEquals(3, boxedLine.height(),
        "E: Correct height after making boxed line wider");
    assertEquals("/-------\\\n|Goodbye|\n\\-------/\n", TestUtils.toString(boxedLine),
        "E: Correct contents for widened boxed line");
  } // testBoxedLineWider()

  /**
   * Test of boxed rects that change. We do this as one test so that
   * we can stack changes.
   */
  @Test
  public void testBoxedRectChange() throws Exception {
    Rect rect = new Rect('Q', 3, 4);
    AsciiBlock boxedRect = new Boxed(rect);
    rect.wider();

    assertEquals(6, boxedRect.width(),
        "E: Correct width for a wider boxed rectangle");
    assertEquals(6, boxedRect.height(),
        "E: Correct height for a wider boxed rectangle");
    assertEquals("""
                 /----\\
                 |QQQQ|
                 |QQQQ|
                 |QQQQ|
                 |QQQQ|
                 \\----/
                 """,
        TestUtils.toString(boxedRect),
        "E: Correct contents for a wider boxed rectangle");

    rect.narrower();
    rect.narrower();

    assertEquals(4, boxedRect.width(),
        "E: Correct width for a narrower boxed rectangle");
    assertEquals(6, boxedRect.height(),
        "E: Correct height for a narrower boxed rectangle");
    assertEquals("""
                 /--\\
                 |QQ|
                 |QQ|
                 |QQ|
                 |QQ|
                 \\--/
                 """,
        TestUtils.toString(boxedRect),
        "E: Correct contents for a narrower boxed rectangle");

    rect.shorter();
    rect.shorter();

    assertEquals(4, boxedRect.width(),
        "E: Correct width for a shorter boxed rectangle");
    assertEquals(4, boxedRect.height(),
        "E: Correct height for a shorter boxed rectangle");
    assertEquals("""
                 /--\\
                 |QQ|
                 |QQ|
                 \\--/
                 """,
        TestUtils.toString(boxedRect),
        "E: Correct contents for a shorter boxed rectangle");

    rect.taller();

    assertEquals(4, boxedRect.width(),
        "E: Correct width for a taller boxed rectangle");
    assertEquals(5, boxedRect.height(),
        "E: Correct height for a taller boxed rectangle");
    assertEquals("""
                 /--\\
                 |QQ|
                 |QQ|
                 |QQ|
                 \\--/
                 """,
        TestUtils.toString(boxedRect),
        "E: Correct contents for a taller boxed rectangle");
  } // testBoxedRectChange()

  /**
   * Make sure that we can box the empty block.
   */
  @Test
  public void boxedEmptyBlock() throws Exception {
    AsciiBlock boxed = new Boxed(new Empty());
    assertEquals(2, boxed.width(),
        "E: Correct width for a boxed empty block");
    assertEquals(2, boxed.height(),
        "E: Correct height for a boxed empty block");
    assertEquals("/\\", boxed.row(0),
        "E: Correct row 0 of boxed empty block");
    assertEquals("\\/", boxed.row(1),
        "E: Correct row 1 of boxed empty block");
  } // boxedEmptyBlock()

  // +------------+--------------------------------------------------
  // | Surrounded |
  // +------------+

  /**
   * Testing of basic surrounded structures.
   */
  @Test
  void TestSurroundedBasic() throws Exception {
    assertNotNull(new Surrounded(new Line("Hello"), '-'),
        "R: Can surround text lines");
    assertNotNull(new Surrounded(new Rect('B', 3, 4), '!'),
        "R: Can surround rectangles");
  } // TestSurroundedBasic()

  /**
   * Testing of basic surrounded lines.
   */
  @Test
  void TestSurroundedLine() {
    AsciiBlock surroundedLine = new Surrounded(new Line("Hello"), 'x');
    assertEquals(7, surroundedLine.width(),
        "M: Correct width for a surrounded line");
    assertEquals(3, surroundedLine.height(),
        "M: Correct height for a surrounded line");
    assertEquals("xxxxxxx\nxHellox\nxxxxxxx\n", TestUtils.toString(surroundedLine),
        "M: Correct contents for a surrounded line");
  } // TestSurroundedLine()

  /**
   * Testing of basic surrounded rectangles.
   */
  @Test
  void TestSurroundedRect() throws Exception {
    AsciiBlock surroundedRect = new Surrounded(new Rect('v', 3, 4), '^');
    assertEquals(5, surroundedRect.width(),
        "M: Correct width for a surrounded rectangle");
    assertEquals(6, surroundedRect.height(),
        "M: Correct height for a surrounded rectangle");
    assertEquals("""
                 ^^^^^
                 ^vvv^
                 ^vvv^
                 ^vvv^
                 ^vvv^
                 ^^^^^
                 """,
        TestUtils.toString(surroundedRect),
        "M: Correct contents for a surrounded rectangle");
  } // TestSurroundedRect()

  /**
   * Test of surrounded lines that have been narrowed.
   */
  @Test
  public void testSurroundedLineNarrower() {
    Line line = new Line("Hello");
    AsciiBlock surroundedLine = new Surrounded(line, '|');

    line.update("Hi");
    assertEquals(4, surroundedLine.width(),
        "E: Correct width after making surrounded line narrower");
    assertEquals(3, surroundedLine.height(),
        "E: Correct height after making surrounded line narrower");
    assertEquals("||||\n|Hi|\n||||\n", TestUtils.toString(surroundedLine),
        "E: Correct contents for narrowed surrounded line");
  } // testSurroundedLineNarrower()

  /**
   * Test of surrounded lines that have been made wider.
   */
  @Test
  public void testSurroundedLineWider() {
    Line line = new Line("X");
    AsciiBlock surroundedLine = new Surrounded(line, 'x');

    line.update("ABCDE");
    assertEquals(7, surroundedLine.width(),
        "E: Correct width after making surrounded line wider");
    assertEquals(3, surroundedLine.height(),
        "E: Correct height after making surrounded line wider");
    assertEquals("xxxxxxx\nxABCDEx\nxxxxxxx\n", 
        TestUtils.toString(surroundedLine),
        "E: Correct contents for widened surrounded line");
  } // testSurroundedLineWider()

  /**
   * Test of surrounded rects that change. We do this as one test so that
   * we can stack changes.
   */
  @Test
  public void testSurroundedRectChange() throws Exception {
    Rect rect = new Rect('X', 3, 4);
    AsciiBlock surroundedRect = new Surrounded(rect, 'O');
    rect.wider();

    assertEquals(6, surroundedRect.width(),
        "E: Correct width for a wider surrounded rectangle");
    assertEquals(6, surroundedRect.height(),
        "E: Correct height for a wider surrounded rectangle");
    assertEquals("""
                 OOOOOO
                 OXXXXO
                 OXXXXO
                 OXXXXO
                 OXXXXO
                 OOOOOO
                 """,
        TestUtils.toString(surroundedRect),
        "E: Correct contents for a wider surrounded rectangle");

    rect.narrower();
    rect.narrower();

    assertEquals(4, surroundedRect.width(),
        "E: Correct width for a narrower surrounded rectangle");
    assertEquals(6, surroundedRect.height(),
        "E: Correct height for a narrower surrounded rectangle");
    assertEquals("""
                 OOOO
                 OXXO
                 OXXO
                 OXXO
                 OXXO
                 OOOO
                 """,
        TestUtils.toString(surroundedRect),
        "E: Correct contents for a narrower surrounded rectangle");

    rect.shorter();
    rect.shorter();

    assertEquals(4, surroundedRect.width(),
        "E: Correct width for a shorter surrounded rectangle");
    assertEquals(4, surroundedRect.height(),
        "E: Correct height for a shorter surrounded rectangle");
    assertEquals("""
                 OOOO
                 OXXO
                 OXXO
                 OOOO
                 """,
        TestUtils.toString(surroundedRect),
        "E: Correct contents for a shorter surrounded rectangle");

    rect.taller();

    assertEquals(4, surroundedRect.width(),
        "E: Correct width for a taller surrounded rectangle");
    assertEquals(5, surroundedRect.height(),
        "E: Correct height for a taller surrounded rectangle");
    assertEquals("""
                 OOOO
                 OXXO
                 OXXO
                 OXXO
                 OOOO
                 """,
        TestUtils.toString(surroundedRect),
        "E: Correct contents for a taller surrounded rectangle");
  } // testSurroundedRectChange()

  /**
   * Make sure that we can surround the empty block.
   */
  @Test
  public void surroundedEmptyBlock() throws Exception {
    AsciiBlock surrounded = new Surrounded(new Empty(), 's');
    assertEquals(2, surrounded.width(),
        "E: Correct width for a surrounded empty block");
    assertEquals(2, surrounded.height(),
        "E: Correct height for a surrounded empty block");
    assertEquals("ss", surrounded.row(0),
        "E: Correct row 0 of surrounded empty block");
    assertEquals("ss", surrounded.row(1),
        "E: Correct row 1 of surrounded empty block");
  } // surroundedEmptyBlock()

} // class TestBlocks
