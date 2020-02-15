import math

# Original Solution
# def solve():
#   n, k = map(int, input().split())

#   depth = int(math.floor(math.log(k, 2)))
#   occupied = int(math.pow(2, depth) - 1)
#   leaves = k - occupied

#   open_spaces = occupied + 1
#   stalls = n - occupied

#   minEmpty = stalls // open_spaces
#   extras = max(stalls % open_spaces - (leaves - 1), 0)

#   if (extras > 0):
#     return ("%d %d" % (math.ceil(minEmpty / 2), math.floor(minEmpty / 2)))
#   else:
#     return ("%d %d" % (math.ceil((minEmpty - 1) / 2), math.floor((minEmpty - 1) / 2)))

# Optimal
def solve():
  n, k = map(int, input().split())
  s = {n: 1}

  while (k > 0):
    x = max(s)
    c = s[x]

    x0 = x // 2
    x1 = (x - 1) // 2

    s[x0] = s.get(x0, 0) + c
    s[x1] = s.get(x1, 0) + c

    k -= c
    del s[x]

  return ("%d %d" % (x0, x1))

T = int(input())
for i in range(T):
  print ("Case #%d: %s" % (i + 1, solve()))

