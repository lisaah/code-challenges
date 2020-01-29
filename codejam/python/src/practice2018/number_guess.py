#!/usr/bin/env python

import sys

def solve(a, b):
  guess = (a + b ) // 2

  print(guess)
  sys.stdout.flush()

  response = input()
  if response == "CORRECT":
    return
  elif response == "TOO_SMALL":
    a = guess + 1
  else:
    b = guess - 1

  solve(a, b)


T = int(input())
for _ in range(T):
  a, b = map(int, input().split())
  _ = int(input())
  solve(a + 1, b)

