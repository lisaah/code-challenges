import random

chars = list("ABCDEFGHIJKLMNOPQRSTUVWXYZ")

def check(passwords, chars):
  for password in passwords:
    if password in chars:
      return False

  return True

# todo: solve large input
def solve():
  N = int(input())
  passwords = input().split()

  isPossible = True
  for password in passwords:
    if len(password) == 1:
      return "IMPOSSIBLE"

  result = "".join(chars)
  while not check(passwords, result):
    random.shuffle(chars)
    result = "".join(chars)

  return ("%s" % (result))

T = int(input())
for i in range(T):
  print ("Case #%d: %s" % (i + 1, solve()))
