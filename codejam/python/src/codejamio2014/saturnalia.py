def solve():
  text = input()
  return ("+-%s-+\n| %s |\n+-%s-+" % ('-' * len(text), text, '-' * len(text)))

T = int(input())
for i in range(T):
  print ("Case #%d:" % (i + 1))
  print ("%s" % (solve()))

