package edu.grinnell.csc207.blocks;

import java.util.Arrays;

/**
 * The vertical composition of blocks.
 *
 * @author Samuel A. Rebelsky
 * @author Your Name Here
 * @author Your Name Here
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
    if((i >= 0) && (i < this.height())){
      String output ="";
      int smallerHalfCt = (this.width() - output.length()) / 2;
      int largerHalfCt = (this.width() - output.length() + 1) / 2;
      for (int block = 0; block < this.blocks.length; block++) {
        if (this.blocks[block].height() > i) {
          output = this.blocks[block].row(i);
          block = this.blocks.length;
        } else {
          i -= this.blocks[block].height();
        } // if / else
      } // for [block]
      if (this.align.equals(HAlignment.LEFT)) {
        return output + new String(new char[] {' '}).repeat(smallerHalfCt + largerHalfCt);
      } else if (this.align.equals(HAlignment.CENTER)) {
        return new String(new char[] {' '}).repeat(smallerHalfCt) + output + new String(new char[] {' '}).repeat(largerHalfCt);
      } else {
        return new String(new char[] {' '}).repeat(smallerHalfCt + largerHalfCt) + output;
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
    for(AsciiBlock block : this.blocks){
      h += block.height();
    }
    return h;
  } // height()

  /**
   * Determine how many columns are in the block.
   *
   * @return the number of columns
   */
  public int width() {
    int w = 0;
    for(AsciiBlock block : this.blocks){
      w = Math.max(w,block.width());
    }
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
    return false;       // STUB
  } // eqv(AsciiBlock)
} // class VComp
