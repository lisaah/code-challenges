def readlines(n):
  l = []
  for _ in range(n):
    l.append(list(map(int, input().split())))

  return l

def solve():
  A, B = map(int, input().split())
  return ("%s" % ("Hello World"))

T = int(input())

# Print stdout
for i in range(T):
  print ("Case #%d: %s" % (i + 1, solve()))

# # Print to file
# file = open("out.txt","w")
# for i in range(T):
#   out = ("Case #%d: %s\n" % (i + 1, solve()))
#   file.write(out)
# file.close()
