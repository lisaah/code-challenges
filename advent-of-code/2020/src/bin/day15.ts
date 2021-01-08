import * as fs from 'fs';

function solve(arr: number[], turns: number): number {
  const m = new Map();

  arr.forEach((n, index) => {
    m.set(n, [-1, index]);
  });

  let n = arr[arr.length - 1];
  // console.log({n, m})
  for (let i = arr.length; i < turns; i++) {
    const prevN = n;
    const prevNHistory = m.get(prevN);

    // Figure out new spoken number
    const first = prevNHistory[0] === -1;
    n = first ? 0 : prevNHistory[1] - prevNHistory[0];
    const newNHistory = m.get(n) || [-1, -1];
    m.set(n, [newNHistory[1], i])

    // console.log({prevN, n, first, m})
  }

  return n;
}

function part_a(arr: number[]): number {
  return solve(arr, 2020);
}

function part_b(arr: number[]): number {
  return solve(arr, 30000000);
}

function main() {
  let data = fs.readFileSync('input/day15.txt', 'utf8');
  let arr: number[] = data
    .split(',')
    .map((x) => parseInt(x, 10));

  console.log(part_a(arr));
  console.log(part_b(arr));
}

main();
