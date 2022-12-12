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

You can get speeds using the `--benchmark <day>` parameter.

- `01`: 1.6 ms
- `02`: 1.8 ms
- `03`: 5.2 ms
- `04`: 3.9 ms
- `05`: 15.7 ms
- `06`: 3.8 ms
- `07`: 500 µs
- `08`: 57.4 ms
- `09`: 44.4 ms
- `10`: 140 µs
- `11`: 1.4 s