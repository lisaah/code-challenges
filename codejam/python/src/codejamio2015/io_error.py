import math

def solve():
  B = int(input())
  chars = list(input())
  x = []

  for i in range(len(chars)):
    bit = i % 8
    if (bit == 0):
      b = 0

    if (chars[i] == "I"):
      b = b + math.pow(2, 7 - bit)

    if (bit == 7):
      x.append(str(chr(int(b))))

  return ("%s" % ("".join(x)))

T = int(input())
for i in range(T):
  print ("Case #%d: %s" % (i + 1, solve()))
