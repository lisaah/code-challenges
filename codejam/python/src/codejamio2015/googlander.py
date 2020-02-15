memoized = [[-1 for i in range(26)] for j in range(26)]
def permute(R, C):
  if R == 1 or C == 1:
    return 1

  if memoized[R][C] != -1:
    return memoized[R][C]

  o = 0
  for i in range(R):
    o = o + permute(i + 1, C - 1)

  memoized[R][C] = o
  return o

def solve():
  R, C = map(int, input().split())

  o = permute(R, C)

  return ("%d" % (o))

T = int(input())
for i in range(T):
  print ("Case #%d: %s" % (i + 1, solve()))
