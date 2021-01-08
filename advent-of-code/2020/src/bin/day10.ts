import * as fs from 'fs';

function part_a(arr: number[]): number {
  arr.push(0);
  arr.sort((a, b) => a - b);
  arr.push(arr[arr.length - 1] + 3);

  const diff: Map<number, number> = new Map();
  for (let i = 1; i < arr.length; i++) {
    const d = arr[i] - arr[i - 1];
    diff.set(d, (diff.get(d) || 0) + 1);
  }

  console.log(diff);
  return diff.get(1)! * diff.get(3)!;
}

const fact_map = new Map();
function fact(f: number): number {
  if (f < 3) {
    return f;
  }

  if (fact_map.has(f)) {
    return fact_map.get(f);
  }

  const r = f * fact(f - 1);
  fact_map.set(f, r);
  return r;
}

function combo(n: number, r: number) : number {
  return fact(n) / (fact(r) * fact(n - r))
}


function calc_combos(arr: number[], i: number, j: number): number {
  if (j -  i < 2) {
    return 1;
  }

  // console.log(`arr[${i}] = ${arr[i]}, arr[${j}] = ${arr[j]}`, arr.slice(i, j + 1));

  if (j - i === 2) {
    return 1 + 1;

  }

  if (j - i === 3) {
    return combo(2, 1) + 1 + 1;

  }

  if (j - i === 4) {
    return combo(3, 1) + combo(3, 2) + 1;
  }

  console.log('never');
  return 1;
}

function part_b(arr: number[]): number {
  arr.push(0);
  arr.sort((a, b) => a - b);
  arr.push(arr[arr.length - 1] + 3);

  console.log(arr);

  const diff: Map<number, number> = new Map();
  let combinations = 1;
  for (let i = arr.length - 1; i >= 0; i--) {
    let d = arr[i] - arr[i - 1];

    if (d < 3) {
      let j = i;
      while (d < 3 && i >= 0) {
        i--;
        d = arr[i] - arr[i - 1];
      }
      combinations *= calc_combos(arr, i, j);
    }
  }

  return combinations;
}

function main() {
  let data = fs.readFileSync('input/day10.txt', 'utf8');
  let arr: number[] = data
    .split("\n")
    .map((a) => +a);

  console.log(part_a(arr));
  console.log(part_b(arr));
}

main();