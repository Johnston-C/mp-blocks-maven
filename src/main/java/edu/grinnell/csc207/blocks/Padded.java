package edu.grinnell.csc207.blocks;

/**
 * A padded ASCII block.
 *
 * @author Samuel A. Rebelsky
 * @author Cade Johnston
 * @author Nicky Moreno Gonzalez
 */
public class Padded implements AsciiBlock {
  // +--------+------------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The original block.
   */
  AsciiBlock block;

  /**
   * The character used for padding.
   */
  String pad;

  /**
   * How is the original block horizontally aligned within the padding?
   */
  HAlignment halign;

  /**
   * How is the original block vertically aligned within the padding?
   */
  VAlignment valign;

  /**
   * How wide is the padded block?
   */
  int width;

  /**
   * How tall is the padded block.
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
   * @param ch
   *   The character we use for padding.
   * @param horiz
   *   How the original block is horizontally aligned within the padding.
   * @param vert
   *   How the original block is vertically aligned within the padding.
   * @param paddedWidth
   *   The width of the padded block.
   * @param paddedHeight
   *   The height of the padded block.
   */
  public Padded(AsciiBlock original, char ch, HAlignment horiz,
      VAlignment vert, int paddedWidth, int paddedHeight) {
    this.block = original;
    this.pad = new String(new char[] {ch});
    this.halign = horiz;
    this.valign = vert;
    this.width = paddedWidth;
    this.height = paddedHeight;
  } // Padded(AsciiBlock, char, HAlignment, VAlignment, int, int)

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
    if ((i < 0) || (i >= this.height())) {
      throw new Exception("Invalid row " + i);
    } // if
    int smallerPadV = (this.height() - this.block.height()) / 2;
    int largerPadV = (this.height() - this.block.height() + 1) / 2;
    int smallerPadH = (this.height() - this.block.height()) / 2;
    int largerPadH = (this.height() - this.block.height() + 1) / 2;
    if ((this.valign.equals(VAlignment.TOP)) && (i < smallerPadV + largerPadV)) {
      return this.pad.repeat(this.width());
    } else if ((this.valign.equals(VAlignment.CENTER)) && ((i < smallerPadV)
              || (i > smallerPadV + this.height()))) {
      return this.pad.repeat(this.width());
    } else if ((this.valign.equals(VAlignment.BOTTOM)) && (i > this.height())) {
      return this.pad.repeat(this.width());
    } else {
      String output;
      if (this.valign.equals(VAlignment.TOP)) {
        output = this.block.row(i);
      } else if (this.valign.equals(VAlignment.CENTER)) {
        output = this.block.row(i - smallerPadV);
      } else {
        output = this.block.row(i - smallerPadV - largerPadV);
      } // if / else if / else
      if (this.halign.equals(HAlignment.LEFT)) {
        return output + this.pad.repeat(smallerPadH + largerPadH);
      } else if (this.halign.equals(HAlignment.CENTER)) {
        return this.pad.repeat(smallerPadH) + output + this.pad.repeat(largerPadH);
      } else {
        return this.pad.repeat(smallerPadH + largerPadH) + output;
      } // if / else if / else
    } // if / else if / else if / else
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
    return ((other instanceof Padded) && (this.eqv((Padded) other)));
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
  public boolean eqv(Padded other) {
    return (this.block.eqv(other.block))
      && (this.halign == other.halign)
      && (this.height == other.height)
      && (this.pad.equals(other.pad))
      && (this.valign == other.valign)
      && (this.width == other.width);
  } // eqv(Padded)
} // class Padded
