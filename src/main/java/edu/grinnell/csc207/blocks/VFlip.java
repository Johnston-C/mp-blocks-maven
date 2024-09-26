package edu.grinnell.csc207.blocks;

/**
 * A vertically flipped ASCII block.
 *
 * @author Cade Johnston
 * @author Nicky Moreno Gonzalez
 */
public class VFlip implements AsciiBlock {
  // +--------+------------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The original block.
   */
  AsciiBlock block;

  // +--------------+------------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Build a new block with the specified contents.
   *
   * @param original
   *   The original block.
   */
  public VFlip(AsciiBlock original) {
    this.block = original;
  } // VFlip(AsciiBlock)

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
    if ((i >= 0) && (i < this.height())) {
      return this.block.row(this.height() - 1 - i);
    } else {
      throw new Exception("Invalid row " + i);
    } // if / else
  } // row(int)

  /**
   * Determine how many rows are in the block.
   *
   * @return the number of rows
   */
  public int height() {
    return this.block.height();
  } // height()

  /**
   * Determine how many columns are in the block.
   *
   * @return the number of columns
   */
  public int width() {
    return this.block.width();
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
    if (other instanceof VFlip) {
      return this.eqv((VFlip) other);
    } // if
    return false;
  } // eqv(AsciiBlock)

  /**
   * This method checks the structural equivalence of the
   * underlying blocks by calling their respective eqv methods.
   *
   * @param other the VFlip block to compare to this block.
   * @return true if the two VFlip blocks are structurally equivalent,
   *         false otherwise.
   */
  public boolean eqv(VFlip other) {
    return this.block.eqv(other.block);
  } // eqv(VFlip)
} // class VFlip
