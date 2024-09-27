package edu.grinnell.csc207.blocks;

/**
 * One line of text. The line is mutable (that is, it can be changed).
 *
 * @author Samuel A. Rebelsky
 * @author Cade Johnston
 * @author Nicky Moreno Gonzalez
 */
public class Line implements AsciiBlock {
  // +--------+------------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The contents of the line.
   */
  String line;

  // +--------------+------------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Build a new line.
   *
   * @param contents
   *   The contents of the line.
   */
  public Line(String contents) {
    this.line = contents;
  } // Line(String)

  // +--------------------+------------------------------------------
  // | AsciiBlock methods |
  // +--------------------+

  /**
   * Get one row from the block.
   *
   * @param i the number of the row
   *
   * @return row i.
   *
   * @exception Exception
   *   if i is outside the range of valid rows.
   */
  public String row(int i) throws Exception {
    if (i != 0) {
      throw new Exception("Invalid row " + i);
    } // if
    return this.line;
  } // row(int)

  /**
   * Determine how many rows are in the block.
   *
   * @return the number of rows
   */
  public int height() {
    return 1;
  } // height()

  /**
   * Determine how many columns are in the block.
   *
   * @return the number of columns
   */
  public int width() {
    return this.line.length();
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
    if (other instanceof Line) {
      return this.eqv((Line) other);
    } // if
    return false;
  } // eqv(AsciiBlock)

  /**
   * Determine if another block is structurally equivalent to this block.
   *
   * @param other
   *   The block to compare to this block.
   *
   * @return true if the two blocks are structurally equivalent and
   *    false otherwise.
   */
  public boolean eqv(Line other) {
    return this.line.equals(other.line);
  } // eqv(Line)

  // +---------------+-----------------------------------------------
  // | Other methods |
  // +---------------+

  /**
   * Update the line.
   *
   * @param newContents
   *   The new contents of the line.
   */
  public void update(String newContents) {
    this.line = newContents;
  } // update(String)
} // class Line
