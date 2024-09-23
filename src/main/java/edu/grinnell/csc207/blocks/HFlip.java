package edu.grinnell.csc207.blocks;

/**
 * A horizontally flipped ASCII block.
 *
 * @author Samuel A. Rebelsky
 * @author Your Name Here
 */
public class HFlip implements AsciiBlock {
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
  public HFlip(AsciiBlock original) {
    this.block = original;
  } // HFlip(AsciiBlock)

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
    String originalRow = block.row(i);
    String reversedRow = "";
    for (int j = originalRow.length() - 1; j >= 0; j--) {
      reversedRow += originalRow.charAt(j);
    }
    return reversedRow;
  } // row(int)

  /**
   * Determine how many rows are in the block.
   *
   * @return the number of rows
   */
  public int height() {
    return block.height();   // STUB
  } // height()

  /**
   * Determine how many columns are in the block.
   *
   * @return the number of columns
   */
  public int width() {
    return block.width();   // STUB
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
    if (other instanceof HFlip) {
      return this.eqv((HFlip) other); 
    }
    return false;      
  } // eqv(AsciiBlock)
  
  /**
   * Compares this HFlip block to another HFlip block
   * to determine if they are structurally equivalent.

   * @param other the HFlip block to compare to this block.
   * @return true if the two HFlip blocks are structurally equivalent,
   *         false otherwise.
   */
  public boolean eqv(HFlip other) {
    return this.block.eqv(other.block);
  }
} // class HFlip
