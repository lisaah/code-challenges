
import itertools
import math

def combinations(n, r):
  f = math.factorial
  return f(n) // f(r) // f(n-r)

def permutations(n):
  if n < 3:
    return 1

  return math.factorial(n - 1) / 2

def solve():
  N, K = map(int, input().split())

  tableSize = N // K
  extras = N % K

  c = 1
  needSeats = N
  for i in range(K):
    numPeople = (tableSize + 1) if i < extras else tableSize
    c = c * combinations(needSeats, numPeople) * permutations(numPeople)
    needSeats = needSeats - numPeople

  c = c / (math.factorial(extras) * math.factorial(K - extras))

  return ("%d" % (c))

T = int(input())
for i in range(T):
  print ("Case #%d: %s" % (i + 1, solve()))

