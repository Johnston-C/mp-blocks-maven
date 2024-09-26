package edu.grinnell.csc207.blocks;

/**
 * A block of Ascii characters with a curve of characture transposed
 * on top of it.
 *
 * @author Samuel A. Rebelsky
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
    // Accept and store the parameters.
    this.contents = blockContents;
    this.c = ch;
    this.polyDegree = degree;
    this.subdivisions = divisions;
    this.xCoordArray = xCoords;
    this.yCoordArray = yCoords;
    this.stampData = new boolean[this.height()][this.width()];
    // Fill stampData with false values.
    for (int i = 0; i < contents.height(); i++) {
      for (int j = 0; j < contents.width(); j++) {
        stampData[i][j] = false;
      } // for [j]
    } // for [i]
    // Attempt to create the data of the stamp.
    try {
      createData(xCoords, yCoords);
    // If the arguments were bad, print out an error message.
    } catch (Exception e) {
      System.err.println("Bad Argument: No stamp generated.");
    } // try / catch [Exception]
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

  private void createData(int[] xData, int[] yData) throws Exception {
    // If the count of x coordinates and y coordinates is equal and there is one more data point
    // than a multiple of the degree ...
    if ((xData.length == yData.length) && (xData.length % this.polyDegree == 1 % this.polyDegree)) {
      // refined_Data variables will hold a set of coordinates that are approximations
      // of points on the curve. The are (input data count - 1) / (degree) * (subdivisions) + 1
      // points that need to be approximated along the curve.
      int[] refinedXData = new int[(xData.length - 1) / (this.polyDegree) * this.subdivisions + 1];
      int[] refinedYData = new int[(xData.length - 1) / (this.polyDegree) * this.subdivisions + 1];
      // cur_ variables will hold the current values of the X and Y coordinates of the
      // currently approximated point.
      float curX;
      float curY;
      // last_ variables will hold the current values of the X and Y coordinates such that
      // these values are the nearest integers. The equivalent cur_ variable will still hold with
      // float precision alongside last_.
      int lastX;
      int lastY;
      // delta_ variables will hold the rate of change of cur_.
      float deltaX = 0;
      float deltaY = 0;
      // For each curve specified by the data ...
      for (int i = 0; i < (xData.length - 1) / (this.polyDegree); i++) {
        // For each to-be approximated point on curve i ...
        for (double j = 0; j <= this.subdivisions; j++) {
          // Reset curX and curY
          curX = 0;
          curY = 0;
          // For each point relevant to curve i ...
          for (int k = 0; k <= this.polyDegree; k++) {
            // The equation of a point along bezier curve is as follows:
            // Summation (k=0 ; k<=degree) {t = k / degree; point(k) * combinations(degree, k) *
            // (t ^ k) * ((1 - t) ^ (degree - k))}
            // Thus, we need to generate the weight point k has on curve i at approximated point j.
            // Do this by multiplying each point j by nCr(degree, k),
            // (j / subdivisions) ^ (k), and ((subdivisions - j) / subdivisions)^(degree - k).
            deltaX = xData[i * this.polyDegree + k] * combinations(this.polyDegree, k)
                     * (float) (Math.pow(j / this.subdivisions, k)
                     * Math.pow((this.subdivisions - j) / this.subdivisions, this.polyDegree - k));
            deltaY = yData[i * this.polyDegree + k] * combinations(this.polyDegree, k)
                     * (float) (Math.pow(j / this.subdivisions, k)
                     * Math.pow((this.subdivisions - j) / this.subdivisions, this.polyDegree - k));
            // Add the weight point k has on curve i at approximated point j to curX and curY.
            curX += deltaX;
            curY += deltaY;
          } // for [k]
          // Add the new point (curX, curY) to the refined_Data variables at
          // the appropriate location. NOTE: rounding discrepancies make it so
          // that BezierCurveStamp is order dependent for the point arrays.
          refinedXData[(int) (i * this.subdivisions + j)] = Math.round(curX);
          refinedYData[(int) (i * this.subdivisions + j)] = Math.round(curY);
        } // for [j]
      } // for [i]
      // Now we need to add points along each line segment defined by a point and the next
      // point in the array.
      // Thus, for each approximated point along the line, except for the last one ...
      for (int i = 0; i < refinedXData.length - 1; i++) {
        // Set the cur_ variables to the first point
        curX = refinedXData[i];
        curY = refinedYData[i];
        // If the first point and next point are the same ...
        if ((Math.abs(refinedXData[i + 1] - refinedXData[i]) == 0)
            && (Math.abs(refinedYData[i + 1] - refinedYData[i]) == 0)) {
          // Add that shared point to the curve.
          includeIfValid((int) curX, (int) curY);
        // Otherwise, if the change in X is greater than or equal to the change in Y ...
        } else if (Math.abs(refinedXData[i + 1] - refinedXData[i])
                   >= Math.abs(refinedYData[i + 1] - refinedYData[i])) {
          // Set deltaX to the sign of the difference between
          // the next point and this one.
          deltaX = Math.signum(refinedXData[i + 1] - refinedXData[i]);
          // Then set deltaY to the difference in Y between the next point and this one
          // divided by the absolute value of the difference in X.
          deltaY = (float) (refinedYData[i + 1] - refinedYData[i])
                   / Math.abs(refinedXData[i + 1] - refinedXData[i]);
          // Set lastY to curY
          lastY = (int) curY;
          // For as long as curX != the next points' X value, and once more after that ...
          for (curX = refinedXData[i]; (int) curX != (int) refinedXData[i + 1] + deltaX;
               curX += deltaX) {
            // Check if the absolute difference between lastY and curY is greater than 0.5
            if (Math.abs((float) lastY - curY) >= 0.5) {
              // If it is, increase lastY by the sign of deltaY
              lastY += Math.signum(deltaY);
              // If the difference of lastY and curY + half of deltaY is less than 0.5 ...
              // (i.e. If the line approximation crosses the y-axis first)
              if (Math.abs(lastY - curY - deltaY * 0.5) < 0.5) {
                // Add a point at the coordinate (curX, lastY - sign deltaY).
                includeIfValid((int) curX,  lastY - (int) Math.signum(deltaY));
              // If the difference of lastY and curY + half of deltaY is greater than 0.5 ...
              // (i.e. If the line approximation crosses the x-axis first)
              } else if (Math.abs(lastY - curY - deltaY * 0.5) > 0.5) {
                // Add a point at the coordinate (curX - sign deltaX, lastY).
                includeIfValid((int) curX - (int) Math.signum(deltaX),  lastY);
              } // if / else if
            } // if
            // Add a point at the coordinate (curX, lastY).
            includeIfValid((int) curX, lastY);
            // Increase curY by deltaY.
            curY += deltaY;
          } // for (curX)
        //Otherwise, (i.e. if the change in Y is greater than the change in X) ...
        } else {
          // Set deltaY to the sign of the difference between
          // the next point and this one.
          deltaY = Math.signum(refinedYData[i + 1] - refinedYData[i]);
          // Then set deltaX to the difference in X between the next point and this one
          // divided by the absolute value of the difference in Y.
          deltaX = (float) (refinedXData[i + 1] - refinedXData[i])
                   / Math.abs(refinedYData[i + 1] - refinedYData[i]);
          // Set lastX to curX
          lastX = (int) curX;
          // For as long as curY != the next points' Y value, and once more after that ...
          for (curY = refinedYData[i]; (int) curY != (int) refinedYData[i + 1] + deltaY;
               curY += deltaY) {
            // Check if the absolute difference between lastX and curX is greater than 0.5
            if (Math.abs((float) lastX - curX) >= 0.5) {
              // If it is, increase lastX by the sign of deltaX
              lastX += Math.signum(deltaX);
              // If the difference of lastX and curX + half of deltaX is less than 0.5 ...
              // (i.e. If the line approximation crosses the x-axis first)
              if (Math.abs(lastX - curX - deltaX * 0.5) < 0.5) {
                // Add a point at the coordinate (lastX - sign deltaX, curY).
                includeIfValid(lastX - (int) Math.signum(deltaX), (int) curY);
              // If the difference of lastX and curX + half of deltaX is greater than 0.5 ...
              // (i.e. If the line approximation crosses the y-axis first)
              } else if (Math.abs(lastX - curX - deltaX * 0.5) > 0.5) {
                // Add a point at the coordinate (lastX, curY - sign deltaY).
                includeIfValid(lastX, (int) curY - (int) Math.signum(deltaY));
              } // if / else if
            } // if
            // Add a point at the coordinate (lastX, curY).
            includeIfValid(lastX, (int) curY);
            // Increase curX by deltaX.
            curX += deltaX;
          } // for (curY)
        } // if / else
      } // for [i]
    // Otherwise, throw an exception.
    } else {
      throw new Exception("Bad Data Count");
    } // if / else
  } // createData(int[], int[])

  /**
   * Return the count of combinations of n choose r.
   *
   * @param n
   *   The number of objects.
   * @param r
   *   The number of selections.
   * @return
   *   The count of combinations
   */
  private float combinations(int n, int r) {
    return (float) factorial(n) / (factorial(r) * factorial(n - r));
  } // combinations(int, int)

  /**
   * Returns the factiorial of positive integer n.
   *
   * @param n
   *   The number to get the factorial of.
   * @return
   *   The factorial of n.
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
   * Attempts to include point (xCoord, yCoord) in the stamp data.
   *
   * @param xCoord
   *   The x coordinate of the new point.
   * @param yCoord
   *   The y coordinate of the new point.
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
    return false;
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
