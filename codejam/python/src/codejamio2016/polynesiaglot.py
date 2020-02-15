import math

# # Non-memoized
# def permute(C, V, L, free = True):
#   choices = C + V if free else V
#   # Last letter must always be vowel
#   if L == 1:
#     return V
#   combos = V * permute(C, V, L - 1, True)
#   if (free):
#     combos = combos + C * permute(C, V, L - 1, False)
#   return combos

PRIME = 1000000007
def permute(C, V, L, memoized):
  if L == 0:
    return 1

  if L == 1:
    return V

  if memoized[L] != -1:
    return memoized[L]

  combos = ((V * permute(C, V, L - 1, memoized) % PRIME) + \
    (C * V * permute(C, V, L - 2, memoized)) % PRIME)
  combos = combos % PRIME

  memoized[L] = combos
  return combos

def solve():
  C, V, L = map(int, input().split())
  result = permute(C, V, L, [-1 for i in range(L + 1)])
  return ("%d" % (result))

T = int(input())
for i in range(T):
  print ("Case #%d: %s" % (i + 1, solve()))