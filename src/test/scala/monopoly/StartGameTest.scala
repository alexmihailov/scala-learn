package com.witcher
package monopoly

class StartGameTest extends AbstractMonopolyTest {

  "all chips" should "be in the starting position" in {
    testKit.start(Array(BEAR, KREMLIN))
    assert(testKit.whereIs(BEAR) == 0)
    assert(testKit.whereIs(KREMLIN) == 0)
    testKit.finish()
  }

  "all chips" should "have a starting account balance" in {
    testKit.start(Array(BALALAIKA, COSMONAUT))
    assert(testKit.getAccount(BALALAIKA) == START_ACCOUNT)
    assert(testKit.getAccount(COSMONAUT) == START_ACCOUNT)
    testKit.finish()
  }

  "in game" should "play a minimum of 2 players" in {
    assertThrows[RuntimeException] {
      testKit.start(Array(BEAR))
    }
  }

  "in game" should "play a maximum of 6 players" in {
    assertThrows[RuntimeException] {
      testKit.start(Array(BEAR, KREMLIN, BALALAIKA, COSMONAUT, POINTE, MATRYOSHKA, BEAR))
    }
  }

  "each chip" should "used by one player" in {
    assertThrows[RuntimeException] {
      testKit.start(Array(BEAR, BALALAIKA, BEAR, BALALAIKA))
    }
  }

  "unknown chips not" should "participate in the game" in {
    assertThrows[RuntimeException] {
      testKit.start(Array(BEAR, BALALAIKA, UNKNOWN))
    }
  }

  "not" should "receive an account until the game is started" in {
    assertThrows[RuntimeException] {
      testKit.getAccount(BALALAIKA)
    }
  }

  "not" should "receive an location until the game is started" in {
    assertThrows[RuntimeException] {
      testKit.whereIs(BALALAIKA)
    }
  }

  "not" should "receive an account a player who does not play or does not exist" in {
    testKit.start(Array(BEAR, BALALAIKA))
    assertThrows[RuntimeException] {
      testKit.getAccount(COSMONAUT)
    }
    assertThrows[RuntimeException] {
      testKit.getAccount(UNKNOWN)
    }
    testKit.finish()
  }

  "not" should "receive an location a player who does not play or does not exist" in {
    testKit.start(Array(BEAR, BALALAIKA))
    assertThrows[RuntimeException] {
      testKit.whereIs(COSMONAUT)
    }
    assertThrows[RuntimeException] {
      testKit.whereIs(UNKNOWN)
    }
    testKit.finish()
  }

  "not" should "receive information about the prisoner of a player who does not play or does not exist" in {
    testKit.start(Array(BEAR, BALALAIKA))
    assertThrows[RuntimeException] {
      testKit.isArrested(COSMONAUT)
    }
    assertThrows[RuntimeException] {
      testKit.isArrested(UNKNOWN)
    }
    testKit.finish()
  }
}
