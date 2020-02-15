def solve():
  _ = int(input())
  prices = list(map(int, input().split()))
  prices.sort()

  salePrices = []
  while len(prices) > 0:
    price = prices.pop()
    salePrice = int(price * 3 / 4)
    salePrices.append(str(salePrice))

    saleIndex = prices.index(salePrice)
    del prices[saleIndex]
  
  salePrices.reverse()
  return ("%s" % (' '.join(salePrices)))

T = int(input())
for i in range(T):
  print ("Case #%d: %s" % (i + 1, solve()))
