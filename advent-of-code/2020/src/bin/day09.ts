import * as fs from 'fs';

function contains_sum(arr: Set<number>, n: number): boolean {
  for (let k of arr.values()) {
    const opp = n - k;
    if (opp !== k && arr.has(n - k)) {
      return true;
    }
  }
  return false;
}

function get_weakness(arr: number[]): number {
  const set = new Set(arr.slice(0, PREAMBLE));
  for (let i = PREAMBLE; i < arr.length; i++) {
    const n = arr[i];
    if (!contains_sum(set, n)) {
      return n;
    }

    set.add(n);
    set.delete(arr[i - PREAMBLE]);
  }
  return -1;
}

const PREAMBLE = 25;

function part_a(arr: number[]): number {
  return get_weakness(arr);
}

function part_b(arr: number[]): number {
  const weakness = get_weakness(arr);

  let p = 0;
  let current_sum = 0;
  for (let i = 0; i < arr.length; i++) {
    current_sum += arr[i];

    while (current_sum > weakness) {
      current_sum -= arr[p];
      p++;
    }

    console.log(`arr[${p}]...arr[${i}] = ${current_sum}`);

    if (current_sum === weakness && i - p >= 2) {
      const c = arr.slice(p, i + 1);
      c.sort((a, b) => a - b);
      return c[0] + c[c.length - 1];
    }
  }

  return -1;
}

function main() {
  let data = fs.readFileSync('input/day9.txt', 'utf8');
  let arr: number[] = data
    .split("\n")
    .map((a) => +a);

  // console.log(part_a(arr));
  console.log(part_b(arr));
}

main();