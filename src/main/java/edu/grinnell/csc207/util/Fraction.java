package edu.grinnell.csc207.util;

/**
 * A simple implementation of arbitrary-precision Fractions.
 *
 * @author Cade Johnston
 */
public class Fraction {

  /** The zero fraction */
  public static final Fraction ZERO = new Fraction(0);

  /** The one fraction */
  public static final Fraction ONE = new Fraction(1);

  /** The negative one fraction */
  public static final Fraction NEGONE = new Fraction(-1);

  // +--------+
  // | Fields |
  // +--------+

  /** The numerator. Can be any integer. */
  private int num;

  /** The denominator. Must be non-negative. */
  private int denom;

  // +--------------+
  // | Constructors |
  // +--------------+

  /**
   * Build a new fraction with numerator num and denominator denom.
   *
   * @param n The numerator of the fraction [int].
   * @param d The denominator of the fraction [int].
   */
  public Fraction(int n, int d) {
    int gcd = gcd(n, d);
    this.num = n / gcd;
    this.denom = d / gcd;
    if (Math.abs(this.num * this.denom) == (this.num * this.denom)) {
      this.num = Math.abs(this.num);
    } else {
      this.num = - Math.abs(this.num);
    } // if / else
    this.denom = Math.abs(this.denom);
  } // BigFraction(int, int)

  public Fraction(int n) {
    this.num = n;
    this.denom = 1;
  } // BigFraction(int)

  /**
   * Build a new fraction based on the input string.
   *
   * @param fracString The string to convert into a fraction.
   */
  public Fraction(String fracString) {
    int split = fracString.indexOf("/");
    if (split == -1) {
      this.num = Integer.parseInt(fracString);
      this.denom = 1;
    } else {
      this.num = Integer.parseInt(fracString.substring(0, split));
      this.denom = Integer.parseInt(fracString.substring(split + 1));
      int gcd = gcd(Math.abs(this.num), Math.abs(this.denom));
      if (Math.abs(this.num * this.denom) == (this.num * this.denom)) {
        this.num = Math.abs(this.num) / gcd;
      } else {
        this.num = - Math.abs(this.num) / gcd;
      } // if / else
      this.denom = Math.abs(this.denom) / gcd;
    } // if / else
  } // BigFraction(String)

  // +---------+
  // | Methods |
  // +---------+

  /**
   * Add another faction to this fraction.
   *
   * @param addend
   *   The fraction to add.
   *
   * @return the result of the addition.
   */
  public Fraction add(Fraction addend) {
    return new Fraction(this.num * addend.denom + addend.num * this.denom,
        this.denom * addend.denom);
  } // add(BigFraction)

  /**
   * Subtract another faction from the current value and store the result.
   *
   * @param subtrahend
   *   The fraction to subtract with.
   *
   * @return the result of the subtraction.
   */
  public Fraction subtract(Fraction subtrahend) {
    return new Fraction(
        this.num * subtrahend.denom - subtrahend.num * this.denom,
        this.denom * subtrahend.denom);
  } // Subtract(BigFraction)

  /**
   * Multiply another faction to the current value and store the result.
   *
   * @param multiplier
   *   The fraction to mlultiply by.
   *
   * @return the result of the multiplication.
   */
  public Fraction multiply(Fraction multiplier) {
    return new Fraction(this.num * multiplier.num,
        this.denom * multiplier.denom);
  } // multiply(BigFraction)

  /**
   * Divide another faction from the current value abnd store the result.
   *
   * @param divisor
   *   The fraction to divide by.
   *
   * @return the result of the division.
   */
  public Fraction divide(Fraction divisor) {
    return new Fraction(this.num * divisor.denom, this.denom * divisor.num);
  } // divide(BigFraction)

  /**
   * Raise the power of this fraction to 'pow'.
   *
   * @param pow
   *   The power to be raised to.
   *
   * @return the new Fraction.
   */
  public Fraction pow(int pow) {
    if (pow == 0) {
      return Fraction.ONE;
    } else if (pow == 1) {
      return this;
    } else {
      return this.multiply(this.pow(pow-1));
    } //
  } // pow(BigFraction)

  /**
   * Returns if two fractions are of equal value
   *
   * @param other
   *   The fraction to compare with
   *
   * @return whether the fractions are equal
   */
  public boolean equal(Fraction other) {
    if (this.subtract(other).numerator() == 0) {
      return true;
    } else {
      return false;
    } // if / else
  } // equal(BigFraction)

  /**
   * Returns if this fraction is greater than another
   *
   * @param other
   *   The fraction to compare with
   *
   * @return whether this fraction is greater
   */
  public boolean greater(Fraction other) {
    if (this.subtract(other).numerator() > 0) {
      return true;
    } else {
      return false;
    } // if / else
  } // greater(BigFraction)

  /**
   * Returns if this fraction is greater or equal to another
   *
   * @param other
   *   The fraction to compare with
   *
   * @return whether this fraction is greater or equal
   */
  public boolean greaterEq(Fraction other) {
    if (this.subtract(other).numerator() >= 0) {
      return true;
    } else {
      return false;
    } // if / else
  } // greaterEq(BigFraction)

  /**
   * Returns if this fraction is less than another
   *
   * @param other
   *   The fraction to compare with
   *
   * @return whether this fraction less
   */
  public boolean less(Fraction other) {
    if (this.subtract(other).numerator() < 0) {
      return true;
    } else {
      return false;
    } // if / else
  } // less(BigFraction)

  /**
   * Returns if this fraction is less or equal to another
   *
   * @param other
   *   The fraction to compare with
   *
   * @return whether this fraction is less or equal
   */
  public boolean lessEq(Fraction other) {
    if (this.subtract(other).numerator() <= 0) {
      return true;
    } else {
      return false;
    } // if / else
  } // lessEq(BigFraction)

  /**
   * Get the nearest integer to the fraction.
   *
   * @return the nearest integer.
   */
  public int round() {
    return (int) Math.round(((float) this.num / (float) this.denom));
  } // round()

  /**
   * Get the absolute value of the fraction.
   *
   * @return the absolute value.
   */
  public Fraction abs() {
    return new Fraction(Math.abs(this.num), this.denom);
  } // abs()

  /**
   * Get the greatest common factor of two numbers.
   * 
   * @param a
   *   First integer.
   * @param b
   *   Second integer
   * @return
   *   The greatest common denominator of the two numbers.
   */
  private int gcd(int a, int b) {
    if(b == 0) {
      return a;
    } else {
      int temp = a;
      a = b;
      b = temp % a;
      return gcd(a,b);
    } // if / else
  } // gcd(int, int)

  /**
   * Returns the sign of the variable as an int
   *
   * @return the sign of the fraction.
   */
  public int signInt() {
    if (this.num == 0) {
      return 0;
    } else if (this.num > 0) {
      return 1;
    } else {
      return -1;
    }
  } // signInt()

  /**
   * Returns the sign of the variable as a Fraction
   *
   * @return the sign of the fraction.
   */
  public Fraction sign() {
    if (this.num == 0) {
      return Fraction.ZERO;
    } else if (this.num > 0) {
      return Fraction.ONE;
    } else {
      return Fraction.NEGONE;
    }
  } // sign()

  /**
   * Access the 'num' variable of a BigFraction object.
   *
   * @return the value of 'num'.
   */
  public int numerator() {
    return this.num;
  } // numerator()

  /**
   * Access the 'denom' variable of a BigFraction object.
   *
   * @return the value of 'denom'.
   */
  public int denominator() {
    return this.denom;
  } // denominator()

  /**
   * The BigFraction object's fraction represented as a String.
   *
   * @return The String equivalent of the BigFraction's fraction.
   */
  public String toString() {
    if (this.denom == 1) {
      return this.num + "";
    } // if
    return this.num + "/" + this.denom;
  } // toString()
} // class Fraction
