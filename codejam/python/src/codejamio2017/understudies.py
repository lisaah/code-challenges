def solve():
  N = int(input())
  P = list(map(float, input().split()))
  P.sort()

  y = 1
  for i in range(N):
    u = P[i] * P[2 * N - i - 1]
    y = y * (1.0 - u)

  return ("%.6f" % (y))

T = int(input())
for i in range(T):
  print ("Case #%d: %s" % (i + 1, solve()))
