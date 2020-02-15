def solve():
  F, S = map(int, input().split())

  seen = {}
  seats = [0 for i in range(S + 1)]
  for i in range(F):
    A, B = map(int, input().split())
    if A > B:
      X = A
      Y = B
    else:
      X = B
      Y = A

    if not (X, Y) in seen:
      seen[(X, Y)] = 1
      seats[X] = seats[X] + 1
      if X != Y:
        seats[Y] = seats[Y] + 1


  y = max(seats)
  return ("%d" % (y))

T = int(input())
for i in range(T):
  print ("Case #%d: %s" % (i + 1, solve()))
