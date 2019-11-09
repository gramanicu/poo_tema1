# Sheriff of Nottingham

The problem statement can be found [here](http://elf.cs.pub.ro/poo/teme/tema). It is an adapted version of the Sheriff of Nottingham "board game".

## Table of Contents

- [Sheriff of Nottingham](#sheriff-of-nottingham)
  - [Table of Contents](#table-of-contents)
  - [Project structure](#project-structure)
  - [Packages](#packages)
    - [game](#game)
    - [helpers](#helpers)
    - [player](#player)
    - [strategy](#strategy)
  - [More info](#more-info)

## Project structure

``` bash
 - ./com/tema1
    |-- Readme.md
    |-- goods ...
    |-- game
    |   |-- Game.java
    |   |-- LeaderBoard.java
    |   |-- Score.java
    |   |-- ScoreComparator.java
    |   |-- IdFrequencyPair.java
    |   |-- IdFrequencyPairComparator.java
    |-- helpers
    |   |-- Constants.java
    |   |-- RoleType.java
    |   |-- StrategyType.java
    |-- main ...
    |-- player
    |   |-- Bag.java
    |   |-- Player.java
    |   |-- Stall.java
    |-- strategy
        |-- Strategy.java
        |-- BaseStrategy.java
        |-- BribeStrategy.java
        |-- GreedyStrategy.java
        |-- FrequencyPair.java
        |-- FrequencyPairComparator.java
        |-- ItemValueComparator.java
```

The goods and main package contains the original files.

## Packages
This package contains most of the game logic, things like loading data from the input files, rounds & subrounds logic, etc.
### game
- Game - contains the base game logic
- Leaderboard - most of the score calculating logic
- Score - a container for the data used to display the leaderboard
- ScoreComparator - a comparator used for sorting player scores

### helpers
This package contains constants and enums.
- Constants - constants used in the program
- RoleType - the player roles : Sheriff & Trader
- StrategyType - the strategy types for any player : Base, Bribe or Greedy
  
### player
This package contains containers for all sorts of data used by the players.
- Player - a container for player related data : role, money, strategy, bag, shop(stall), etc.
- Bag - the container used by the players to transport goods into Notthingham. It contains items, bribes, but also has the logic for bag inspection
- Stall - the shop of the player. This is the place where the player will place all the items he got into the city. In this class, some of the players score is computed.

### strategy
This package contains most of the player related game logic, because the players behaviours is determined mostly by his chosen strategy.
- Strategy - a interface for the other strategies
- BaseStrategy - the base strategy
- GreedyStrategy - inherits most of the "strategy" of the base player. The biggest difference is the fact that he can be bribed, when he is a sheriff
- BribeStrategy - inherits a few things from the basic strategy. Most often he will bring illegal goods into the town, give bribes, take bribes or simply not inspect the "traders"

## More info
For more information, see the javadocs the comments. I explained the implementation there.

© 2019 Grama Nicolae, 322CA
