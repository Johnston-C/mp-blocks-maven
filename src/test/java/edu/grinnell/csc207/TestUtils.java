package edu.grinnell.csc207;

import edu.grinnell.csc207.blocks.AsciiBlock;

/**
 * Utilities for testing.
 */
public class TestUtils {
  // +-----------+---------------------------------------------------
  // | Utilities |
  // +-----------+

  /**
   * Determine if two blocks are equal in that they have the same,
   * width, height, and rows.
   *
   * We do not rely on `AsciiBlock.equals` because someone may have
   * changed it to better pass tests.
   *
   * @param block1
   *   One of the two blocks.
   * @param block2
   *   The other block.
   *
   * @return true if they are in the same memory location and false otherwise.
   */
  public static boolean same(AsciiBlock block1, AsciiBlock block2) {
    if (block1.width() != block2.width()) {
      return false;
    } // if
    if (block1.height() != block2.height()) {
      return false;
    } // if
    for (int i = 0; i < block1.height(); i++) {
      try {
        if (!block1.row(i).equals(block2.row(i))) {
          return false;
        } // if
      } catch (Exception e) {
        return false;
      } // try/catch
    } // for
    return true;
  } // same(AsciiBlock, AsciiBlock)

  /**
   * Convert a block to a string.
   *
   * @param block
   *   The block to convert.
   *
   * @return the block with its rows separated by newlines.
   */
  public static String toString(AsciiBlock block) {
    // Special case: The empty block.
    if (block.height() == 0) {
      return "";
    } // if

    StringBuilder result = new StringBuilder();

    for (int i = 0; i < block.height(); i++) {
      try {
        result.append(block.row(i));
      } catch (Exception e) {
        result.append("*** ERROR ***");
      } // try/catch
      result.append("\n");
    } // for
    return result.toString();
  } // toString()
} // class TestUtils
