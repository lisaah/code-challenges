
fp = open("in.txt")

def read():
  # return input()
  return fp.readline()

def war(b1, b2):
  s = b1_s = b2_s = 0
  b1_e = b2_e = len(b1) - 1

  while (b1_s <= b1_e and b2_s <= b2_e):
    if b1[b1_e] > b2[b2_e]:
      b1_e = b1_e - 1
      b2_s = b2_s + 1
      s = s + 1
    else:
      b1_e = b1_e - 1
      b2_e = b2_e - 1

  return s

def deceitful_war(b1, b2):
  s = b1_s = b2_s = 0
  b1_e = b2_e = len(b1) - 1

  while (b1_s <= b1_e and b2_s <= b2_e):
    if b1[b1_s] < b2[b2_s]:
      b1_s = b1_s + 1
      b2_e = b2_e - 1
    else:
      b1_s = b1_s + 1
      b2_s = b2_s + 1
      s = s + 1

  return s

def solve():
  N = int(read())
  naomi_blocks = list(map(float, read().split()))
  ken_blocks = list(map(float, read().split()))

  naomi_blocks.sort()
  ken_blocks.sort()

  y = deceitful_war(naomi_blocks, ken_blocks)
  z = war(naomi_blocks, ken_blocks)

  return ("%d %d" % (y, z))

T = int(read())
for i in range(T):
  print ("Case #%d: %s" % (i + 1, solve()))

# file = open("out.txt","w")
# for i in range(T):
#   out = ("Case #%d: %s\n" % (i + 1, solve()))
#   file.write(out)
# file.close()
