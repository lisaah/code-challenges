import math

def solve():
  A, B, alpha, beta, Y = map(int, input().split())

  for i in range(Y):
    decA = int(A / 100)
    decB = int(B / 100)

    K = min(A, B)
    babies = int(K * 2 / 100)

    alphaBaby = int(alpha * babies / 100)
    betaBaby = int(beta * babies / 100)

    leftovers = babies - alphaBaby - betaBaby
    newA = int(leftovers / 2)
    newB = leftovers - newA

    resultA = A - decA + alphaBaby + newA
    resultB = B - decB + betaBaby + newB

    if resultA == A and resultB == B:
      break

    A = resultA
    B = resultB

  return ("%d %d" % (A, B))

T = int(input())
for i in range(T):
  print ("Case #%d: %s" % (i + 1, solve()))

