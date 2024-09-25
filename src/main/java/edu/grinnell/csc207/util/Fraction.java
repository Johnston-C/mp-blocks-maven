package edu.grinnell.csc207.util;

import java.math.BigInteger;
/**
 * A simple implementation of arbitrary-precision Fractions.
 *
 * @author Cade Johnston
 */
public class Fraction {

  /** The zero fraction */
  public static final Fraction ZERO = new Fraction("0");

  /** The one fraction */
  public static final Fraction ONE = new Fraction("1");

  /** The negative one fraction */
  public static final Fraction NEGONE = new Fraction("-1");

  // +--------+
  // | Fields |
  // +--------+

  /** The numerator. Can be any integer. */
  private BigInteger num;

  /** The denominator. Must be non-negative. */
  private BigInteger denom;

  // +--------------+
  // | Constructors |
  // +--------------+

  /**
   * Build a new fraction with numerator num and denominator denom.
   *
   * @param n The numerator of the fraction [BigInteger].
   * @param d The denominator of the fraction [BigInteger].
   */
  public Fraction(BigInteger n, BigInteger d) {
    BigInteger gcd = n.gcd(d);
    this.num = n.divide(gcd);
    this.denom = d.divide(gcd);
    if (this.num.multiply(this.denom).abs().equals(this.num.multiply(this.denom))) {
      this.num = this.num.abs();
    } else {
      this.num = this.num.abs().negate();
    } // if / else
    this.denom = this.denom.abs();
  } // Fraction(BigInteger, BigInteger)

  /**
   * Build a new fraction with numerator num and denominator denom.
   *
   * @param n The numerator of the fraction [int].
   * @param d The denominator of the fraction [int].
   */
  public Fraction(int n, int d) {
    BigInteger bINum = BigInteger.valueOf(n);
    BigInteger bIDenom = BigInteger.valueOf(d);
    BigInteger gcd = bINum.gcd(bIDenom);
    this.num = bINum.divide(gcd);
    this.denom = bIDenom.divide(gcd);
    if (this.num.multiply(this.denom).abs().equals(this.num.multiply(this.denom))) {
      this.num = this.num.abs();
    } else {
      this.num = this.num.abs().negate();
    } // if / else
    this.denom = this.denom.abs();
  } // Fraction(int, int)

  /**
   * Build a new fraction with numerator num and denominator denom.
   *
   * @param n The numerator of the fraction [int].
   * @param d The denominator of the fraction [int].
   */
  public Fraction(int n) {
    this.num = new BigInteger(n+"");
    this.denom = BigInteger.ONE;
  } // Fraction(int, int)

  /**
   * Build a new fraction based on the input string.
   *
   * @param fracString The string to convert into a fraction.
   */
  public Fraction(String fracString) {
    int split = fracString.indexOf("/");
    if (split == -1) {
      this.num = new BigInteger(fracString);
      this.denom = BigInteger.ONE;
    } else {
      this.num = new BigInteger(fracString.substring(0, split));
      this.denom = new BigInteger(fracString.substring(split + 1));
      BigInteger gcd = this.num.gcd(this.denom);
      if (this.num.multiply(this.denom).abs().equals(this.num.multiply(this.denom))) {
        this.num = this.num.abs().divide(gcd);
      } else {
        this.num = this.num.abs().negate().divide(gcd);
      } // if / else
      this.denom = this.denom.abs().divide(gcd);
    } // if / else
  } // Fraction(String)

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
  /**
   * Add another faction to this fraction.
   *
   * @param addend
   *   The fraction to add.
   *
   * @return the result of the addition.
   */
  public Fraction add(Fraction addend) {
    return new Fraction(this.num.multiply(addend.denom).add(addend.num.multiply(this.denom)),
        this.denom.multiply(addend.denom));
  } // add(Fraction)

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
        this.num.multiply(subtrahend.denom).subtract(subtrahend.num.multiply(this.denom)),
        this.denom.multiply(subtrahend.denom));
  } // Subtract(Fraction)

  /**
   * Multiply another faction to the current value and store the result.
   *
   * @param multiplier
   *   The fraction to mlultiply by.
   *
   * @return the result of the multiplication.
   */
  public Fraction multiply(Fraction multiplier) {
    return new Fraction(this.num.multiply(multiplier.num),
        this.denom.multiply(multiplier.denom));
  } // multiply(Fraction)

  /**
   * Divide another faction from the current value abnd store the result.
   *
   * @param divisor
   *   The fraction to divide by.
   *
   * @return the result of the division.
   */
  public Fraction divide(Fraction divisor) {
    return new Fraction(this.num.multiply(divisor.denom), this.denom.multiply(divisor.num));
  } // divide(Fraction)

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
    return (int) Math.round((this.num.doubleValue() / this.denom.doubleValue()));
  } // round()

  /**
   * Get the absolute value of the fraction.
   *
   * @return the absolute value.
   */
  public Fraction abs() {
    return new Fraction(this.num.abs(), this.denom);
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
  public int gcd(int a, int b) {
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
    if (this.num.equals(BigInteger.ZERO)) {
      return 0;
    } else if (this.num.compareTo(this.denom) > 0) {
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
    if (this.num.equals(BigInteger.ZERO)) {
      return Fraction.ZERO;
    } else if (this.num.compareTo(this.denom) > 0) {
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
    return this.num.intValue();
  } // numerator()

  /**
   * Access the 'denom' variable of a BigFraction object.
   *
   * @return the value of 'denom'.
   */
  public int denominator() {
    return this.denom.intValue();
  } // denominator()

  /**
   * The BigFraction object's fraction represented as a String.
   *
   * @return The String equivalent of the BigFraction's fraction.
   */
  public String toString() {
    if (this.denominator() == 1) {
      return this.num + "";
    } // if
    return this.num + "/" + this.denom;
  } // toString()
} // class Fraction
