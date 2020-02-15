# # Cycle solver
# def getKey(dict, val):
#   for key, value in dict.items():
#     if val == value:
#       return key
#   return None
#
# def swap(pos, dir):
#   length = len(pos)
#   newPos = [None for i in range(len(pos))]
#   for i in range(length):
#     if dir == 1:
#       j = ((i + 1) if i % 2 == 0 else (i - 1)) % length
#     else:
#       x = (length - i) % length
#       j = ((i - 1) if x % 2 == 0 else (i + 1)) % length
#     newPos[i] = pos[j]
#   return tuple(newPos)
#
# def solve():
#   D, K, N = map(int, input().split())
#   currentPosition = tuple(i for i in range(1, D + 1))
#   positions = {currentPosition: 0}
#   level = N % D
#   for i in range(1, N + 1):
#     # execute move
#     currentPosition = swap(currentPosition, i % 2)
#     # Found cycle
#     if currentPosition in positions:
#       initial = positions[currentPosition]
#       cycleLength = i - initial
#       finalPositionIndex = (N - i) % cycleLength
#       currentPosition = getKey(positions, finalPositionIndex)
#       break
#     else:
#       positions[currentPosition] = i
#   index = currentPosition.index(K)
#   y = currentPosition[(index + 1) % D]
#   z = currentPosition[(index - 1) % D]
#   return ("%d %d" % (y, z))

def solve():
  D, K, N = map(int, input().split())

  if K % 2 == 1:
    y = ((K + 2 * N) % D) + 1
    z = ((K - 2 + 2 * N) % D) + 1
  else:
    y = ((K - 2 * N) % D) + 1
    z = ((K - 2 - 2 * N) % D) + 1

  return ("%d %d" % (y, z))

T = int(input())
for i in range(T):
  print ("Case #%d: %s" % (i + 1, solve()))