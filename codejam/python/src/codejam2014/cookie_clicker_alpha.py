def solve():
  C, F, X = list(map(float, input().split()))

  cookieRate = 2
  seconds = 0

  while True:
    time = C / cookieRate
    recoupCookieTime = X / (cookieRate + F)
    timeFinish = X / cookieRate

    if timeFinish < time + recoupCookieTime:
      seconds = seconds + timeFinish
      break

    cookieRate = cookieRate + F
    seconds = seconds + time

  return ("%.7f" % (seconds))

T = int(input())
for i in range(T):
  print ("Case #%d: %s" % (i + 1, solve()))

# file = open("out.txt","w")
# for i in range(T):
#   out = ("Case #%d: %s\n" % (i + 1, solve()))
#   file.write(out)
# file.close()
