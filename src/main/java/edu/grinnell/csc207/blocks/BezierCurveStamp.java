package edu.grinnell.csc207.blocks;

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
   */
  public BezierCurveStamp(AsciiBlock blockContents, char ch, int degree, int subdivisions, int[] xCoords, int[] yCoords) {
    this.contents = blockContents;
    this.c = ch;
    this.polyDegree = degree;
    this.subdivisions = subdivisions;
    this.stampData = new boolean[this.height()][this.width()];
    for(int i = 0; i < contents.height(); i++) {
      for(int j = 0; j < contents.width(); j++) {
        stampData[i][j] = false;
      }
    }
    createData(xCoords, yCoords);
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
    for(int j = 0; j < this.width(); j++) {
      if(stampData[i][j]) {
        output[j] = this.c;
      }
    } // for [i]
    String toReturn = "";
    for(int j=0; j < this.width(); j++) {
      toReturn = toReturn.concat(output[j]+"");
    }
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

  private void createData(int[] xData, int[] yData) {
    if((xData.length == yData.length) && (xData.length % this.polyDegree == 1 % this.polyDegree)){
      int[] refinedXData = new int[(xData.length) / (this.polyDegree + 1) * this.subdivisions + 1];
      int[] refinedYData = new int[(xData.length) / (this.polyDegree + 1) * this.subdivisions + 1];
      float curX;
      float curY;
      int lastX;
      int lastY;
      float deltaX = 0;
      float deltaY = 0;
      for(int i = 0; i < (xData.length) / (this.polyDegree + 1); i++){
        for(double j = 0; j <= this.subdivisions; j++){
          curX = 0;
          curY = 0;
          for(int k = 0; k <= this.polyDegree; k++){
            deltaX = xData[i * this.polyDegree + k] * combinations(this.polyDegree, k) * (float) (Math.pow(j / this.subdivisions, k) * Math.pow(1.0 - (j / this.subdivisions), this.polyDegree - k));
            deltaY = yData[i * this.polyDegree + k] * combinations(this.polyDegree, k) * (float) (Math.pow(j / this.subdivisions, k) * Math.pow(1.0 - (j / this.subdivisions), this.polyDegree - k));
            curX += deltaX;
            curY += deltaY;
          } // for [k]
          refinedXData[(int) (i * this.subdivisions + j)] = Math.round(curX);
          refinedYData[(int) (i * this.subdivisions + j)] = Math.round(curY);
          System.out.printf("ADDED: [%d, %d]\n", refinedXData[(int) (i * this.subdivisions + j)], refinedYData[(int) (i * this.subdivisions + j)]);
        } // for [j]
      } // for [i]
      for(int i = 0; i < refinedXData.length - 1; i++) {
        curX = refinedXData[i];
        curY = refinedYData[i];
        if ((Math.abs(refinedXData[i + 1]-refinedXData[i]) == 0) && (Math.abs(refinedYData[i + 1]-refinedYData[i]) == 0)) {
          includeIfValid((int) curX, (int) curY);
        } else if (Math.abs(refinedXData[i + 1]-refinedXData[i]) >= Math.abs(refinedYData[i + 1]-refinedYData[i])) {
          deltaX = Math.signum(refinedXData[i + 1]-refinedXData[i]);
          deltaY = (float) (refinedYData[i + 1]-refinedYData[i]) / Math.abs(refinedXData[i + 1]-refinedXData[i]);
          lastY = (int) curY;
          for (curX = refinedXData[i]; (int) curX != (int) refinedXData[i + 1]; curX += deltaX) {
            if (Math.abs((float) lastY - curY) >= 0.5) {
              lastY+=Math.signum(deltaY);
              if(Math.abs(Math.abs((float) lastY - curY) - Math.abs(deltaY * 0.5)) > 0.01) {
                if (Math.abs((float) lastY - curY) > Math.abs(deltaY * 0.5)) {
                  System.out.print("A |lastY - curY| > |deltaY / 2| therefore ");
                  includeIfValid((int) curX,  lastY - (int) Math.signum(deltaY));
                } else if (Math.abs((float) lastY - curY) < Math.abs(deltaY * 0.5)) {
                  System.out.print("A |lastY - curY| < |deltaY / 2| therefore ");
                  includeIfValid((int) curX - (int) Math.signum(deltaX),  lastY);
                } // if / else if
              } // if
            } // if
            includeIfValid((int) curX, lastY);
            curY += deltaY;
          } // for (curX)
          if (Math.abs((float) lastY - curY) >= 0.5) {
            lastY+=Math.signum(deltaY);
            if(Math.abs(Math.abs((float) lastY - curY) - Math.abs(deltaY * 0.5)) > 0.01) {
              if (Math.abs((float) lastY - curY) > Math.abs(deltaY * 0.5)) {
                System.out.print("B |lastY - curY| > |deltaY / 2| therefore ");
                includeIfValid((int) curX,  lastY - (int) Math.signum(deltaY));
              } else if (Math.abs((float) lastY - curY) < Math.abs(deltaY * 0.5)) {
                System.out.print("B |lastY - curY| < |deltaY / 2| therefore ");
                includeIfValid((int) curX - (int) Math.signum(deltaX),  lastY);
              } // if / else if
            }
          } // if
          includeIfValid((int) curX, lastY);
        } else {
          deltaY = Math.signum(refinedYData[i + 1] - refinedYData[i]);
          deltaX = (float) (refinedXData[i + 1]-refinedXData[i]) / Math.abs(refinedYData[i + 1]-refinedYData[i]);
          lastX = (int) curX;
          for(curY = refinedYData[i]; (int) curY != (int) refinedYData[i + 1]; curY += deltaY) {
            if (Math.abs((float) lastX - curX) >= 0.5) {
              lastX+=Math.signum(deltaX);
              if(Math.abs(Math.abs((float) lastX - curX) - Math.abs(deltaX * 0.5)) > 0.01) {
                if (Math.abs((float) lastX - curX) > Math.abs(deltaX * 0.5)) {
                  includeIfValid(lastX - (int) Math.signum(deltaX), (int) curY);
                } else if (Math.abs((float) lastX - curX) < Math.abs(deltaX * 0.5)) {
                  includeIfValid(lastX, (int) curY - (int) Math.signum(deltaY));
                } // if / else if
              } // if
            } // if
            includeIfValid(lastX, (int) curY);
            curX += deltaX;
          } // curY
          if (Math.abs((float) lastX - curX) >= 0.5) {
            lastX+=Math.signum(deltaX);
            if(Math.abs(Math.abs((float) lastX - curX) - Math.abs(deltaX * 0.5)) > 0.01) {
              if (Math.abs((float) lastX - curX) > Math.abs(deltaX * 0.5)) {
                System.out.print("B |lastX - curX| > |deltaX / 2| therefore ");
                includeIfValid(lastX - (int) Math.signum(deltaX), (int) curY);
              } else if (Math.abs((float) lastX - curX) < Math.abs(deltaX * 0.5)) {
                System.out.print("B |lastX - curX| < |deltaX / 2| therefore ");
                includeIfValid(lastX, (int) curY - (int) Math.signum(deltaY));
              } // if / else if
            } // if
          } // if
          includeIfValid(lastX, (int) curY);
        } // if / else
      } // for [i]
    } else {
      System.err.printf("Bad Data count\n");
    } // if / else
  } // createData(int[], int[])

  private float combinations(int n, int r){
    return (float) factorial(n) / (factorial(r) * factorial(n-r));
  }

  private int factorial(int n){
    int output = 1;
    while(n>0) {
      output *= n;
      n--;
    }
    return output;
  }

  private void includeIfValid(int xCoord, int yCoord){
    if((xCoord >= 0) && (xCoord < this.width()) && (yCoord >= 0) && (yCoord < this.height())){
      System.out.printf("Added: (%d, %d)\n", xCoord, yCoord);
      stampData[yCoord][xCoord] = true;
    } else {
      if(xCoord < 2 * this.height()) {

      }
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
  public boolean eqv(Boxed other) {
    return this.contents.eqv(other.contents);
  } // eqv(Boxed)
}
