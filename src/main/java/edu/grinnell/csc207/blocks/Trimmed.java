package edu.grinnell.csc207.blocks;

/**
 * A trimmed ASCII block.
 *
 * @author Cade Johnston
 * @author Nicky Moreno Gonzalez
 */
public class Trimmed implements AsciiBlock {
  // +--------+------------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The original block.
   */
  AsciiBlock block;

  /**
   * Which part of the block do we keep horizontally?
   */
  HAlignment halign;

  /**
   * Which part of the block do we keep vertically?
   */
  VAlignment valign;

  /**
   * How much of the block do we keep horizontally?
   */
  int width;

  /**
   * How much of the block do we keep vertically?
   */
  int height;

  // +--------------+------------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Build a new block with the specified contents.
   *
   * @param original
   *   The original block.
   * @param horiz
   *   How the trimmed block is horizontally aligned on the original.
   * @param vert
   *   How the trimmed block is vertically aligned on the original.
   * @param trimmedWidth
   *   The width of the trimmed block.
   * @param trimmedHeight
   *   The height of the trimmed block.
   */
  public Trimmed(AsciiBlock original, HAlignment horiz, VAlignment vert,
      int trimmedWidth, int trimmedHeight) {
    this.block = original;
    this.halign = horiz;
    this.valign = vert;
    this.width = trimmedWidth;
    this.height = trimmedHeight;
  } // Trimmed(AsciiBlock, HAlignment, VAlignment, int, int)

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
    if (this.width > block.width() || this.height > block.height()) {
      return "Error: Original Block width or height is smaller";
    } // if
    int startRow;
    if (valign == VAlignment.TOP) {
      startRow = i;
    } else if (valign == VAlignment.CENTER) {
      startRow = i + (block.height() - this.height) / 2;
    } else {
      startRow = i + (block.height() - this.height); // Align the bottom
    } // else
    if (startRow < 0 || startRow >= block.height()) {
      throw new Exception("Invalid row index.");
    } // if

    String originalRow = block.row(startRow);
    int startCol;
    if (halign == HAlignment.LEFT) {
      startCol = 0;
    } else if (halign == HAlignment.CENTER) {
      startCol = (block.width() - this.width) / 2;
    } else {
      startCol = block.width() - this.width; // Align the right
    } // else
    return originalRow.substring(startCol, startCol + this.width);
  } // row(int)

  /**
   * Determine how many rows are in the block.
   *
   * @return the number of rows
   */
  public int height() {
    return this.height;
  } // height()

  /**
   * Determine how many columns are in the block.
   *
   * @return the number of columns
   */
  public int width() {
    return this.width;
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
    if (other instanceof Trimmed) {
      return this.eqv((Trimmed) other);
    } // if
    return false;
  } // eqv(AsciiBlock)

/**
 * This method compares the horizontal and vertical alignment,
 * the trimmed width and height, and the structural equivalence of
 * the underlying blocks.
 *
 * @param other the Trimmed block to compare to this block.
 * @return true if the two Trimmed blocks are structurally equivalent,
 *         false otherwise.
 */
  public boolean eqv(Trimmed other) {
    return false;
  } // eqv(Trimmed)
} // class Trimmed
