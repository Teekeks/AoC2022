# Advent of Code 2022

This repository are my solutions for the [Advent of Code](https://adventofcode.com) Coding challenge 2022.

The aim of this repository is to further my knowledge of clojure and therefore the solutions might not be the optimal ones.

## Setup

- rename `config.example.edn` to `config.edn`
- get your cookie session key from the advent of code website
- set the `session-cookie` variable in `config.edn` to said session key
- Create a executable with: `lein uberjar`
- Place the generated jar in the same folder as your `config.edn` and `resources` folder 

## Usage

You can execute a specific day using the `-d` parameter.
Example:
```java -jar de.teawork.aoc2022-1.0.0.jar -d 01```


## Times

I try to make these run fast enough. Here are the times on my machine:

- `01`: 2ms
- `02`: 2ms
- `03`: 6ms
- `04`: 4ms
- `05`: 18ms
- `06`: 5ms
- `07`: 1ms
- `08`: 56ms
- `09`: 44ms