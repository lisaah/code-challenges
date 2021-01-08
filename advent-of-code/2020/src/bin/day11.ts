import { X_OK } from 'constants';
import * as fs from 'fs';


let WIDTH = 0;
let HEIGHT = 0;

function translateGrid(row: number, col: number): number {
  return WIDTH * row + col;
}

function translateFlat(i: number): [number, number] {
  return [Math.floor(i / WIDTH), i % WIDTH];
}

function checkNeighbors(arr: string, n: number): number {
  const [row, col] = translateFlat(n);
  const row_low = Math.max(0, row - 1);
  const row_high = Math.min(row + 1 + 1, HEIGHT);
  const col_low = Math.max(0, col - 1);
  const col_high = Math.min(col + 1 + 1, WIDTH);
  let count = 0;

  for (let i = row_low; i < row_high; i++) {
    for (let j = col_low; j < col_high; j++) {
      if (i === row && j === col) {
        continue;
      }

      const index = translateGrid(i, j);
      if (arr.charAt(index) === '#') {
        count++;
      }
    }
  }

  // console.log(row, col, count);
  return count;
}

function isOccupied(arr: string, row: number, col: number, x: number, y: number): boolean {
  row += y;
  col += x;

  while (row < HEIGHT && row >= 0 && col < WIDTH && col >=0) {
    const index = translateGrid(row, col);
    if (arr.charAt(index) === '#') {
      return true;
    }

    if (arr.charAt(index) === 'L') {
      return false;
    }

    row += y;
    col += x;
  }

  return false;
}

function checkNeighbors2(arr: string, n: number): number {
  const [row, col] = translateFlat(n);
  let count = 0;

  for (let x = -1; x <= 1; x++) {
    for (let y = -1; y <= 1; y++) {
      if (x === 0 && y === 0) {
        continue;
      }

      if (isOccupied(arr, row, col, x, y)) {
        // console.log('occ', row, col, x, y)
        count++;
      }
    }
  }

  return count;
}

function toggle(original: string): string {
  const next = original.split('');
  for (let i = 0; i < next.length; i++) {
    const c = next[i];
    if (c === '.') {
      continue;
    }

    const occupiedNeighbors = checkNeighbors(original, i);
    if (c === 'L' && occupiedNeighbors === 0) {
      next[i] = '#';
    } else if (c === '#' && occupiedNeighbors >= 4) {
      next[i] = 'L';
    }
  }

  return next.join('');
}

function toggle2(original: string): string {
  const next = original.split('');
  for (let i = 0; i < next.length; i++) {
    const c = next[i];
    if (c === '.') {
      continue;
    }

    const occupiedNeighbors = checkNeighbors2(original, i);
    if (c === 'L' && occupiedNeighbors === 0) {
      next[i] = '#';
    } else if (c === '#' && occupiedNeighbors >= 5) {
      next[i] = 'L';
    }
  }

  return next.join('');
}

function part_a(arr: string): number {
  const history = new Set();

  let prev = arr;
  let current = arr;
  let count = 0;
  while (!history.has(current)) {
    prev = current;
    // console.log(count, prev);

    history.add(current);

    current = toggle(current);

    count++;
  }

  return prev.split('').filter((a) => a === '#').length;
}

function part_b(arr: string): number {
  const history = new Set();

  let prev = arr;
  let current = arr;
  let count = 0;
  while (!history.has(current)) {
    prev = current;
    history.add(current);

    current = toggle2(current);

    // print(prev);
    // console.log('c-', current);

    count++;
  }

  return prev.split('').filter((a) => a === '#').length;
}

function print(n: string): void {
  let s = '';
  for (let i = 0; i < n.length; i++) {
    if (i % WIDTH === 0) {
      s += '\n';
    }
    s += n.charAt(i);
  }
  console.log(s);
}

function main() {
  let data = fs.readFileSync('input/day11.txt', 'utf8');
  const arr: string[] = data
    .split("\n");

  HEIGHT = arr.length;
  WIDTH = arr[0].length;

  const s = arr.join('');
  // console.log(part_a(s));
  console.log(part_b(s));
}

main();