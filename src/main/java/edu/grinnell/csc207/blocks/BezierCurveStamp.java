package edu.grinnell.csc207.blocks;

import edu.grinnell.csc207.util.Fraction;

/**
 * A block of Ascii characters with a curve of characture transposed
 * on top of it.
 *
 * @author Cade Johnston
 * @author Nicky Moreno Gonzalez
 */
public class BezierCurveStamp implements AsciiBlock {

  // +--------+------------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The data to apply the stamp over.
   */
  AsciiBlock contents;

  /**
   * The degree of the bezierCurve polynomial.
   */
  int polyDegree;

  /**
   * The amount of subdivisions in each bezierCurve.
   */
  int subdivisions;

  /**
   * The amount of subdivisions in each bezierCurve.
   */
  int[] xCoordArray;

  /**
   * The amount of subdivisions in each bezierCurve.
   */
  int[] yCoordArray;

  /**
   * The data of the stamp.
   */
  boolean[][] stampData;

  /**
   * The character used by the stamp.
   */
  char c;

  // +--------------+------------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Build a new block with the specified contents.
   *
   * @param blockContents
   *   The contents of the block.
   * @param ch
   *   The character to use in the stamp.
   * @param degree
   *   The degree of the bezier curve.
   * @param divisions
   *   The amount of subdivisions involved in the approximation of the curve.
   * @param xCoords
   *   The set of x coordinates to use as points.
   * @param yCoords
   *   The set of y coordinates to use as points.
   */
  public BezierCurveStamp(AsciiBlock blockContents, char ch, int degree,
                          int divisions, int[] xCoords, int[] yCoords) {
    this.contents = blockContents;
    this.c = ch;
    this.polyDegree = degree;
    this.subdivisions = divisions;
    this.xCoordArray = xCoords;
    this.yCoordArray = yCoords;
    this.stampData = new boolean[this.height()][this.width()];
    for (int i = 0; i < contents.height(); i++) {
      for (int j = 0; j < contents.width(); j++) {
        stampData[i][j] = false;
      } // for [j]
    } // for [i]
    createData();
  } // Boxed(AsciiBlock)

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
   *   if the row is invalid
   */
  public String row(int i) throws Exception {
    if (i < 0 || i >= this.height()) {
      throw new Exception("Invalid row " + i);
    } // if
    char[] output = contents.row(i).toCharArray();
    for (int j = 0; j < this.width(); j++) {
      if (stampData[i][j]) {
        output[j] = this.c;
      } // if
    } // for [i]
    String toReturn = "";
    for (int j = 0; j < this.width(); j++) {
      toReturn = toReturn.concat(output[j] + "");
    } // for [j]
    return toReturn;
  } // row(int)

  /**
   * Determine how many rows are in the block.
   *
   * @return the number of rows
   */
  public int height() {
    return this.contents.height();
  } // height()

  /**
   * Determine how many columns are in the block.
   *
   * @return the number of columns
   */
  public int width() {
    return this.contents.width();
  } // width()

  /**
   * Determine which coordinates should be replaced with c.
   */
  private void createData() {
    if ((this.xCoordArray.length == this.yCoordArray.length)
        && (this.xCoordArray.length % this.polyDegree == 1 % this.polyDegree)) {
      int[] refinedXCoordArray = new int[(this.xCoordArray.length - 1) / (this.polyDegree)
                                          * this.subdivisions + 1];
      int[] refinedYCoordArray = new int[(this.xCoordArray.length - 1) / (this.polyDegree)
                                          * this.subdivisions + 1];
      Fraction curX;
      Fraction curY;
      Fraction lastX;
      Fraction lastY;
      Fraction deltaX = Fraction.ZERO;
      Fraction deltaY = Fraction.ZERO;
      for (int i = 0; i < (this.xCoordArray.length - 1) / (this.polyDegree); i++) {
        for (int j = 0; j <= this.subdivisions; j++) {
          curX = Fraction.ZERO;
          curY = Fraction.ZERO;
          for (int k = 0; k <= this.polyDegree; k++) {
            deltaX = new Fraction(this.xCoordArray[i * this.polyDegree + k])
            .multiply(combinations(this.polyDegree, k))
            .multiply(new Fraction(j, this.subdivisions).pow(k))
            .multiply(Fraction.ONE.subtract(new Fraction(j, this.subdivisions))
            .pow(this.polyDegree - k));
            deltaY = new Fraction(this.yCoordArray[i * this.polyDegree + k])
            .multiply(combinations(this.polyDegree, k))
            .multiply(new Fraction(j, this.subdivisions).pow(k))
            .multiply(Fraction.ONE.subtract(new Fraction(j, this.subdivisions))
            .pow(this.polyDegree - k));
            curX = curX.add(deltaX);
            curY = curY.add(deltaY);
          } // for [k]
          refinedXCoordArray[(int) (i * this.subdivisions + j)] = curX.round();
          refinedYCoordArray[(int) (i * this.subdivisions + j)] = curY.round();
        } // for [j]
      } // for [i]
      for (int i = 0; i < refinedXCoordArray.length - 1; i++) {

        curX = new Fraction(refinedXCoordArray[i]);
        curY = new Fraction(refinedYCoordArray[i]);
        if ((Math.abs(refinedXCoordArray[i + 1] - refinedXCoordArray[i]) == 0)
            && (Math.abs(refinedYCoordArray[i + 1] - refinedYCoordArray[i]) == 0)) {
          includeIfValid(refinedXCoordArray[i], refinedYCoordArray[i]);
        } else if (Math.abs(refinedXCoordArray[i + 1] - refinedXCoordArray[i])
                   >= Math.abs(refinedYCoordArray[i + 1] - refinedYCoordArray[i])) {
          deltaX = new Fraction(refinedXCoordArray[i + 1] - refinedXCoordArray[i]).sign();
          deltaY = new Fraction(refinedYCoordArray[i + 1] - refinedYCoordArray[i],
                   Math.abs(refinedXCoordArray[i + 1] - refinedXCoordArray[i]));
          lastY = curY;
          for (curX = new Fraction(refinedXCoordArray[i]);
               !curX.equal(new Fraction(refinedXCoordArray[i + 1]));
               curX = curX.add(deltaX)) {
            if (lastY.subtract(curY).abs().greaterEq(new Fraction(1, 2))) {
              if (lastY.subtract(curY).subtract(deltaY.divide(new Fraction(1, 2))).abs()
                  .greater(new Fraction(1, 2))) {
                lastY = lastY.add(deltaY.sign());
                includeIfValid(curX.numerator(),  lastY.subtract(deltaY.sign()).numerator());
              } else if (lastY.subtract(curY).subtract(deltaY.divide(new Fraction(1, 2))).abs()
                  .less(new Fraction(1, 2))) {
                lastY = lastY.add(deltaY.sign());
                includeIfValid(curX.subtract(deltaX.sign()).numerator(),  lastY.numerator());
              } // if / else if
            } // if
            includeIfValid(curX.numerator(), lastY.numerator());
            curY = curY.add(deltaY);
          } // for (curX)
          if (lastY.subtract(curY).abs().greaterEq(new Fraction(1, 2))) {
            if (lastY.subtract(curY).subtract(deltaY.divide(new Fraction(1, 2))).abs()
                .greater(new Fraction(1, 2))) {
              lastY = lastY.add(deltaY.sign());
              includeIfValid(curX.numerator(),  lastY.subtract(deltaY.sign()).numerator());
            } else if (lastY.subtract(curY).subtract(deltaY.divide(new Fraction(1, 2))).abs()
                .less(new Fraction(1, 2))) {
              lastY = lastY.add(deltaY.sign());
              includeIfValid(curX.subtract(deltaX.sign()).numerator(),  lastY.numerator());
            } // if / else if
          } // if
          includeIfValid(curX.numerator(), lastY.numerator());
        } else {
          deltaY = new Fraction(refinedYCoordArray[i + 1] - refinedYCoordArray[i]).sign();
          deltaX = new Fraction(refinedXCoordArray[i + 1] - refinedXCoordArray[i],
                   Math.abs(refinedYCoordArray[i + 1] - refinedYCoordArray[i]));
          lastX = curX;
          for (curY = new Fraction(refinedYCoordArray[i]);
               !curY.equal(new Fraction(refinedYCoordArray[i + 1]));
               curY = curY.add(deltaY)) {
            if (lastX.subtract(curX).abs().greaterEq(new Fraction(1, 2))) {
              if (lastX.subtract(curX).subtract(deltaX.divide(new Fraction(1, 2))).abs()
                  .greater(new Fraction(1, 2))) {
                lastX = lastX.add(deltaX.sign());
                includeIfValid(lastX.numerator(), curY.subtract(deltaY.sign()).numerator());
              } else if (lastX.subtract(curX).subtract(deltaX.divide(new Fraction(1, 2))).abs()
                  .less(new Fraction(1, 2))) {
                lastX = lastX.add(deltaX.sign());
                includeIfValid(lastX.subtract(deltaX.sign()).numerator(), curY.numerator());
              } // if / else if
            } // if
            includeIfValid(lastX.numerator(), curY.numerator());
            curX = curX.add(deltaX);
          } // curY
          if (lastX.subtract(curX).abs().greaterEq(new Fraction(1, 2))) {
            if (lastX.subtract(curX).subtract(deltaX.divide(new Fraction(1, 2))).abs()
                .greater(new Fraction(1, 2))) {
              lastX = lastX.add(deltaX.sign());
              includeIfValid(lastX.numerator(), curY.subtract(deltaY.sign()).numerator());
            } else if (lastX.subtract(curX).subtract(deltaX.divide(new Fraction(1, 2))).abs()
                .less(new Fraction(1, 2))) {
              lastX = lastX.add(deltaX.sign());
              includeIfValid(lastX.subtract(deltaX.sign()).numerator(), curY.numerator());
            } // if / else if
          } // if
          includeIfValid(lastX.numerator(), curY.numerator());
        } // if / else
      } // for [i]
    } else {
      System.err.printf("Bad Data count\n");
    } // if / else
  } // createData(int[], int[])

  /**
   * Return the count of combinations for n choose r.
   *
   * @param n
   *   The total number of items to choose from
   * @param r
   *   The number of items being choosen
   * @return
   *   A fraction representing the combination count for n choose r
   */
  private Fraction combinations(int n, int r) {
    return new Fraction(factorial(n), (factorial(r) * factorial(n - r)));
  } // combinations(int, int)

  /**
   * Returns the factorial of positive integer n.
   *
   * @param n
   *   The number to get the factorial of (Assumed to be positive)
   * @return
   *   The factorial of n
   */
  private int factorial(int n) {
    int output = 1;
    while (n > 0) {
      output *= n;
      n--;
    } // while
    return output;
  } // factorial(int)

  /**
   * Adds a character at the coordinate if the coordinate is in bounds.
   *
   * @param xCoord
   *   The y coordinate of the prospective point
   * @param yCoord
   *   The y coordinate of the prospective point
   */
  private void includeIfValid(int xCoord, int yCoord) {
    if ((xCoord >= 0) && (xCoord < this.width()) && (yCoord >= 0) && (yCoord < this.height())) {
      stampData[yCoord][xCoord] = true;
    } // if
  } // includeIfValid(int, int)

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

  /**
   * Determine if another Boxed is structurally equivalent to this block.
   *
   * @param other
   *   The block to compare to this block.
   *
   * @return true if the two blocks are structurally equivalent and
   *     false otherwise.
   */
  public boolean eqv(BezierCurveStamp other) {
    return this.contents.eqv(other.contents)
      && (this.c == other.c)
      && (this.polyDegree == other.polyDegree)
      && (this.subdivisions == other.subdivisions)
      && (this.xCoordArray == other.xCoordArray)
      && (this.yCoordArray == other.yCoordArray);
  } // eqv(BezierCurveStamp)
} // class BezierCurveStamp
