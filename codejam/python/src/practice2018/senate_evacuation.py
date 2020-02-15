def maxKeys(d):
  # max value
  maxV = max(d.items(), key=lambda x: x[1])

  # iterate find all max keys
  keys = list()
  for key, value in d.items():
      if value == maxV[1]:
          keys.append(key)

  return keys

def dec(key, dict):
  if not key in dict:
    return

  if dict[key] == 1:
    del dict[key]
    return

  dict[key] = dict[key] - 1

def solve():
  n = int(input())
  p = list(map(int, input().split()))
  y = []

  # Map senators
  d = {}
  for i in range(len(p)):
    d[chr(65 + i)] = p[i]

  while len(d) > 0:
    keys = maxKeys(d)
    l = len(keys)
    x0, x1 = "", ""

    if l == 2 or l > 3:
      x0 = keys[0]
      x1 = keys[1]

      dec(x0, d)
      dec(x1, d)
    else:
      x0 = keys[0]
      dec(x0, d)

    y.append("%s%s" % (x0, x1))

  return ("%s" % (" ".join(y)))

T = int(input())
for i in range(T):
  print ("Case #%d: %s" % (i + 1, solve()))

