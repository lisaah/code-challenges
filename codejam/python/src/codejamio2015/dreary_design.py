def solve():
  K, V = map(int, input().split())

  combos = 0
  for R in range(V + 1):
    rCombos = 0
    for G in range(max(R - V, 0), min(R + V, K) + 1):
      minB = max(R - V, G - V, 0)
      maxB = min(R + V, G + V, K)
      rCombos = rCombos + (maxB - minB + 1)

    if R == V:
      # Both ends of V range + middle
      combos = 2 * combos + rCombos * (K - 2 * V + 1)
    else:
      combos = combos + rCombos

  return ("%d" % (combos))

T = int(input())
for i in range(T):
  print ("Case #%d: %s" % (i + 1, solve()))
