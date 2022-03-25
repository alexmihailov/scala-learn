package com.witcher
package monopoly

import org.scalatest.flatspec.AnyFlatSpec

abstract class AbstractMonopolyTest extends AnyFlatSpec {

  protected val KREMLIN = "Kremlin"
  protected val COSMONAUT = "Cosmonaut"
  protected val POINTE = "Pointe"
  protected val BEAR = "Bear"
  protected val MATRYOSHKA = "Matryoshka"
  protected val BALALAIKA = "Balalaika"
  protected val UNKNOWN = "Unknown"

  protected val START_ACCOUNT = 1_500_000_000L
  protected val SALARY = 200_000_000L


  protected val testKit: Monopoly = MonopolyGame
}
