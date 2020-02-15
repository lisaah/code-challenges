
import itertools
import math

def readlines(n):
  i = 0
  l = []
  while i < n:
    l.append(list(map(int, input().split())))
    i += 1

  return l

def solve():
  rowA = int(input()) - 1
  arrangementA = readlines(4)

  rowB = int(input()) - 1
  arrangementB = readlines(4)

  pairs = []
  for i in range(4):
    card_i = arrangementA[rowA][i]
    if card_i in arrangementB[rowB]:
      pairs.append(card_i)

  if len(pairs) == 0:
    o = "Volunteer cheated!"
  elif len(pairs) > 1:
    o = "Bad magician!"
  else:
    o = pairs[0]

  return ("%s" % (o))

T = int(input())
for i in range(T):
  print ("Case #%d: %s" % (i + 1, solve()))

# file = open("out.txt","w")
# for i in range(T):
#   out = ("Case #%d: %s\n" % (i + 1, solve()))
#   file.write(out)
# file.close()
