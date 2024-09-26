package edu.grinnell.csc207.blocks;

import java.util.Arrays;

/**
 * The vertical composition of blocks.
 *
 * @author Samuel A. Rebelsky
 * @author Cade Johnston
 * @author Nicky Moreno Gonzalez
 */
public class VComp implements AsciiBlock {
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
  HAlignment align;

  // +--------------+------------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Build a vertical composition of two blocks.
   *
   * @param alignment
   *   The way in which the blocks should be aligned.
   * @param topBlock
   *   The block on the top.
   * @param bottomBlock
   *   The block on the bottom.
   */
  public VComp(HAlignment alignment, AsciiBlock topBlock,
      AsciiBlock bottomBlock) {
    this.align = alignment;
    this.blocks = new AsciiBlock[] {topBlock, bottomBlock};
  } // VComp(HAlignment, AsciiBlock, AsciiBlock)

  /**
   * Build a vertical composition of multiple blocks.
   *
   * @param alignment
   *   The alignment of the blocks.
   * @param blocksToCompose
   *   The blocks we will be composing.
   */
  public VComp(HAlignment alignment, AsciiBlock[] blocksToCompose) {
    this.align = alignment;
    this.blocks = Arrays.copyOf(blocksToCompose, blocksToCompose.length);
  } // VComp(HAlignment, AsciiBLOCK[])

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
    if ((i >= 0) && (i < this.height())) {
      String output = "";
      for (int block = 0; block < this.blocks.length; block++) {
        if (this.blocks[block].height() > i) {
          output = this.blocks[block].row(i);
          block = this.blocks.length;
        } else {
          i -= this.blocks[block].height();
        } // if / else
      } // for [block]
      int smallerHalfCt = (this.width() - output.length()) / 2;
      int largerHalfCt = (this.width() - output.length() + 1) / 2;
      if (this.align.equals(HAlignment.LEFT)) {
        return output + " ".repeat(smallerHalfCt + largerHalfCt);
      } else if (this.align.equals(HAlignment.CENTER)) {
        return " ".repeat(smallerHalfCt) + output + " ".repeat(largerHalfCt);
      } else {
        return " ".repeat(smallerHalfCt + largerHalfCt) + output;
      } // if / else if / else
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
    int h = 0;
    for (AsciiBlock block : this.blocks) {
      h += block.height();
    } // foreach [block]
    return h;
  } // height()

  /**
   * Determine how many columns are in the block.
   *
   * @return the number of columns
   */
  public int width() {
    int w = 0;
    for (AsciiBlock block : this.blocks) {
      w = Math.max(w, block.width());
    } // foreach [block]
    return w;
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
    if (other instanceof VComp) {
      return this.eqv((VComp) other);
    } // if
    return false;
  } // eqv(AsciiBlock)

  /**
   * This method compares the horizontal alignment, the number of blocks,
   * and iterates through each block to check structural equivalence using
   * their respective eqv methods.
   *
   * @param other the VComp block to compare to this block.
   * @return true if the two VComp blocks are structurally equivalent,
   *         false otherwise.
   */
  public boolean eqv(VComp other) {
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
    } // for [i]
    return true;
  } // eqv(VComp)
} // class VComp
