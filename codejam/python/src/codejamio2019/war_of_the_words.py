#!/usr/bin/env python
# python src/pyutil/helper/interactive_runner.py python src/codejamio2019/testing_tool.py 0 -- python3 src/codejamio2019/war_of_the_words.py

import sys
import random


def gen():
  return ''.join(random.choice(['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J']) for _ in range(5))

def solve(n = 0, default = '', best = ''):
  if n == 0:
    default = gen()
    guess = default
  elif n % 2 == 1:
    guess = default
  else:
    guess = best

  print(guess)
  sys.stdout.flush()

  if n == 100:
    return

  response = input()

  if response == "-1":
    return

  if n % 2 == 0:
    best = response

  solve(n + 1, default, best)

def solveT2(n = 0, order = [], tracker = {}):
  if n == 0:
    guess = gen()
  elif n < 30:
    guess = order[-1]
  elif n % 3 == 0:
    guess = high
  elif n % 3 == 1:
    guess = gen()
  else:
    guess = low

  print(guess)
  sys.stdout.flush()

  if n == 100:
    return

  response = input()

  if response == "-1":
    return

  if n % 2 == 0:
    best = response

  solve(n + 1, default, best)

T, N = map(int, input().split())
for _ in range(T):
  solve()

