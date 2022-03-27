package com.witcher
package monopoly

class MovingTest extends AbstractMonopolyTest {

  "all chips" should "move correctly on the playing field" in {
    testKit.start(Array(BEAR, KREMLIN))
    testKit.rollDice(5, 6) // BEAR -> Уфа
    skip()
    assert(testKit.whereIs(BEAR) == 11)
    testKit.rollDice(4, 6) // KREMLIN -> Экскурсия в тюрьме
    assert(testKit.whereIs(KREMLIN) == 10)
    testKit.rollDice(4, 6) // BEAR -> Омск
    skip()
    assert(testKit.whereIs(BEAR) == 21)
    testKit.rollDice(4, 6) // KREMLIN -> Бесплатная стоянка
    assert(testKit.whereIs(KREMLIN) == 20)
    testKit.rollDice(5, 6) // BEAR -> Владивосток
    skip()
    assert(testKit.whereIs(BEAR) == 32)
    testKit.rollDice(5, 6) // KREMLIN -> Екатеринбург
    skip()
    assert(testKit.whereIs(KREMLIN) == 31)
    testKit.rollDice(5, 6) // BEAR -> Самара
    skip()
    assert(testKit.whereIs(BEAR) == 3)
    testKit.rollDice(4, 6) // KREMLIN -> Тюмень
    skip()
    assert(testKit.whereIs(KREMLIN) == 1)
    testKit.finish()
  }

  "only 3 chips" should  "be placed on the same field together" in {
    testKit.start(Array(BEAR, KREMLIN, BALALAIKA, POINTE))
    testKit.rollDice(4, 6) // BEAR -> Экскурсия в тюрьме
    testKit.rollDice(4, 6) // KREMLIN -> Экскурсия в тюрьме
    testKit.rollDice(4, 6) // BALALAIKA -> Экскурсия в тюрьме
    testKit.rollDice(4, 6) // POINTE -> Уфа
    skip()
    assert(testKit.whereIs(BEAR) == 10)
    assert(testKit.whereIs(KREMLIN) == 10)
    assert(testKit.whereIs(BALALAIKA) == 10)
    assert(testKit.whereIs(POINTE) == 11)
    testKit.finish()
  }

  "the numbers that appear on the dice" should "be between 1 and 6" in {
    testKit.start(Array(BEAR, KREMLIN))
    assertThrows[RuntimeException] {
      testKit.rollDice(8, 12)
    }
    assertThrows[RuntimeException] {
      testKit.rollDice(0, 123)
    }
    assertThrows[RuntimeException] {
      testKit.rollDice(-5, 12)
    }
    assertThrows[RuntimeException] {
      testKit.rollDice(-12, 24)
    }
    testKit.finish()
  }

  "if you rolled a double, you" should "to go again. If 3 times in a row then go to error" in {
    testKit.start(Array(BEAR, KREMLIN, COSMONAUT))
    testKit.rollDice(5, 5) // BEAR -> Вперед, KREMLIN - Вперед
    assert(testKit.whereIs(BEAR) == 0)
    assert(testKit.whereIs(KREMLIN) == 0)
    assert(testKit.whereIs(COSMONAUT) == 0)
    testKit.rollDice(6, 6) // BEAR -> Вперед, KREMLIN - Вперед, COSMONAUT - Вперед
    assert(testKit.whereIs(BEAR) == 0)
    assert(testKit.whereIs(KREMLIN) == 0)
    assert(testKit.whereIs(COSMONAUT) == 0)
    testKit.rollDice(3, 3) // BEAR -> В тюрьме, KREMLIN - Вперед, COSMONAUT - Вперед
    assert(testKit.isArrested(BEAR))
    assert(!testKit.isArrested(KREMLIN))
    assert(!testKit.isArrested(COSMONAUT))
    assert(testKit.whereIs(BEAR) == 10)
    assert(testKit.whereIs(KREMLIN) == 0)
    assert(testKit.whereIs(COSMONAUT) == 0)
    testKit.rollDice(1, 2) // KREMLIN -> Самара
    skip()
    assert(testKit.whereIs(KREMLIN) == 3)
    testKit.rollDice(5, 5) // COSMONAUT -> Вперед
    testKit.rollDice(2, 2) // COSMONAUT -> Вперед
    testKit.rollDice(3, 3) // COSMONAUT -> В тюрьме
    assert(testKit.isArrested(BEAR))
    assert(!testKit.isArrested(KREMLIN))
    assert(testKit.isArrested(COSMONAUT))
    assert(testKit.whereIs(BEAR) == 10)
    assert(testKit.whereIs(KREMLIN) == 3)
    assert(testKit.whereIs(COSMONAUT) == 10)
    testKit.finish()
  }
}
