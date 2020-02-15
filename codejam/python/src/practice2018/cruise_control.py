import sys

def readlines(n):
  i = 0
  l = []
  while i < n:
    l.append(list(map(int, input().split())))
    i += 1

  return l

def intercept(h, d):
  k, s = h
  t = (d - k) / s
  return d / t

def solve():
  d, n = list(map(int, input().split()))
  horses = readlines(n)

  s = sys.maxsize
  for i in range(len(horses)):
    interceptSpeed = intercept(horses[i], d)
    s = min(s, interceptSpeed)

  return ("%.6f" % (s))

T = int(input())
for i in range(T):
  print ("Case #%d: %s" % (i + 1, solve()))

