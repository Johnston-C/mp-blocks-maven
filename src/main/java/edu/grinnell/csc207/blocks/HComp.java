package edu.grinnell.csc207.blocks;

import java.util.Arrays;

/**
 * The horizontal composition of blocks.
 *
 * @author Samuel A. Rebelsky
 * @author Cade Johnston
 * @author Nicky Moreno Gonzalez
 */
public class HComp implements AsciiBlock {
  // +--------+------------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The blocks.
   */
  AsciiBlock[] blocks;

  /**
   * How the blocks are aligned.
   */
  VAlignment align;

  // +--------------+------------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Build a horizontal composition of two blocks.
   *
   * @param alignment
   *   The way in which the blocks should be aligned.
   * @param leftBlock
   *   The block on the left.
   * @param rightBlock
   *   The block on the right.
   */
  public HComp(VAlignment alignment, AsciiBlock leftBlock,
      AsciiBlock rightBlock) {
    this.align = alignment;
    this.blocks = new AsciiBlock[] {leftBlock, rightBlock};
  } // HComp(VAlignment, AsciiBlock, AsciiBlock)

  /**
   * Build a horizontal composition of multiple blocks.
   *
   * @param alignment
   *   The alignment of the blocks.
   * @param blocksToCompose
   *   The blocks we will be composing.
   */
  public HComp(VAlignment alignment, AsciiBlock[] blocksToCompose) {
    this.align = alignment;
    this.blocks = Arrays.copyOf(blocksToCompose, blocksToCompose.length);
  } // HComp(Alignment, AsciiBLOCK[])

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
   *   if i is outside the range of valid rows.
   */
  public String row(int i) throws Exception {
    if (i < 0 || i >= height()) {
      throw new Exception("Invalid row index.");
    } // if
    String composedRow = ""; // Start with an empty string
    for (AsciiBlock block : blocks) {
      int blockHeight = block.height();
      int rowIndex;
      if (align == VAlignment.TOP) {
        rowIndex = i;
      } else if (align == VAlignment.CENTER) {
        int topPadding = (height() - blockHeight) / 2;
        rowIndex = i - topPadding;
      } else {
        int bottomPadding = height() - blockHeight;
        rowIndex = i - bottomPadding;
      } // else
      if (rowIndex >= 0 && rowIndex < blockHeight) {
        composedRow += block.row(rowIndex);
      } else {
        composedRow += " ".repeat(block.width());
      } // else
    } // for
    return composedRow;
  } // row(int)

  /**
   * Determine how many rows are in the block.
   *
   * @return the number of rows
   */
  public int height() {
    int maxHeight = 0;
    for (AsciiBlock block : blocks) {
      maxHeight = Math.max(maxHeight, block.height());
    } // for
    return maxHeight;
  } // height()

  /**
   * Determine how many columns are in the block.
   *
   * @return the number of columns
   */
  public int width() {
    int totalWidth = 0;
    for (AsciiBlock block : blocks) {
      totalWidth += block.width();
    } // for
    return totalWidth;
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
    if (other instanceof HComp) {
      return this.eqv((HComp) other);
    } // if
    return false;
  } // eqv(AsciiBlock)

  /**
   * Compares this HComp block to another HComp block
   * to determine if they are structurally equivalent.

  * @param other the HComp block to compare to this block.
  * @return true if the two HComp blocks are structurally equivalent,
  *         false otherwise.
  */
  public boolean eqv(HComp other) {
    if (this.align != other.align) {
      return false;
    } // if
    if (this.blocks.length != other.blocks.length) {
      return false;
    } // if
    for (int i = 0; i < this.blocks.length; i++) {
      if (!this.blocks[i].eqv(other.blocks[i])) {
        return false;
      } // if
    } // for
    return true;
  } // eqv(HComp)
} // class HComp
