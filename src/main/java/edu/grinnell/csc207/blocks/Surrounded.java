package edu.grinnell.csc207.blocks;

/**
 * A text block surrounded by a single letter.
 *
 * @author Samuel A. Rebelsky
 * @author Nicky Moreno Gonzalez
 * @author Cade J
 */
public class Surrounded implements AsciiBlock {
  // +--------+------------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The stuff in the box.
   */
  AsciiBlock contents;

  /**
   * The character we put around the box.
   */
  String surroundChar;

  // +--------------+------------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Build a new block with the specified contents.
   *
   * @param blockContents
   *   The contents of the block.
   *
   * @param theChar
   *   The character that we use to surround the block.
   */
  public Surrounded(AsciiBlock blockContents, char theChar) {
    this.contents = blockContents;
    this.surroundChar = Character.toString(theChar);
  } // Surrounded(AsciiBlock)

  // +---------+-----------------------------------------------------------
  // | Methods |
  // +---------+

  /**
   * Get one row from the block.
   *
   * @param i the number of the row
   *
   * @return row i.
   *
   * @exception Exception
   *   If the row is invalid.
   */
  public String row(int i) throws Exception {
    if (i < 0 || i >= height()) {
      throw new Exception("Invalid row number");
    }
  // Top and bottom row
    if (i == 0 || i == height() - 1) {
      return surroundChar.repeat(width());
    }
  // Middle rows: surroundChar on both sides, with contents in the middle
    String contentRow = contents.row(i - 1); // Get the row from the contents
    return surroundChar + contentRow + surroundChar;
  } // row(int)

  /**
   * Determine how many rows are in the block.
   *
   * @return the number of rows
   */
  public int height() {
    return contents.height() + 2;   // STUB
  } // height()

  /**
   * Determine how many columns are in the block.
   *
   * @return the number of columns
   */
  public int width() {
    return contents.width() + 2;  // STUB
  } // width()

  /**
   * Determine if another block is structurally equivalent to this block.
   *
   * @param other
   *   The block to compare to this block.
   *
   * @return true if the two blocks are structurally equivalent and
   *    false otherwise.
   */
  public boolean eqv(AsciiBlock other) {
      // Check if 'other' is the same type of block (Surrounded)
      if (other instanceof Surrounded) {
          return this.eqv((Surrounded) other);
      }
      return false; // Different types of blocks are not equivalent
  } // eqv(AsciiBlock)

  /**
   * Compares this Surrounded block to another Surrounded block
   * to determine if they are structurally equivalent.
   *
   * @param other the Surrounded block to compare to this block.
   * @return true if the two blocks have the same surrounding character
   *         and their contents are structurally equivalent; 
   *         false otherwise.
   */
  public boolean eqv(Surrounded other) {
      // Compare the surrounding character
      if (!this.surroundChar.equals(other.surroundChar)) {
          return false;
      }
      // Compare the contents (the inner block)
      return this.contents.eqv(other.contents);
  } // eqv(Surrounded)
} // class Surrounded
