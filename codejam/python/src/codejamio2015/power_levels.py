import math
from collections import OrderedDict

multifact = OrderedDict()
def init():
  for i in range(9000):
    multifact[i + 1] = multifactorial(9000, i + 1)

def multifactorial(N, E):
  K = 0
  res = 1
  while K < N:
    c = N - K * E
    if c > 0:
      res = res * (N - K * E)
    else:
      break
    K = K + 1

  return res

def solve():
  D = int(input())

  i = 0
  for key, value in multifact.items():
    if len(str(value)) < D:
      i = key
      break

  o = "..." if i == 0 else ("IT'S OVER 9000%s" % ("!" * i))
  return ("%s" % (o))

init()

T = int(input())
for i in range(T):
  print ("Case #%d: %s" % (i + 1, solve()))
