import * as fs from 'fs';

const LEFT_RE = /(.+) bags contain/;
const RIGHT_RE = /([0-9]+) (.*?) bag/g;

function part_a(arr: string[]): number {
  const map = new Map();

  arr.forEach((line: string) => {
    const outer = line.match(LEFT_RE)![1];
    const bags = [...line.matchAll(RIGHT_RE)];

    bags.forEach((b) => {
      const inner = b[2];
      const innerCount = +b[1];

      const values = map.get(inner) || new Map();
      values.set(outer, innerCount)
      map.set(inner, values);
    });
  })

  const processed = new Set();
  const nodes = ['shiny gold'];
  while (nodes.length) {
    const node = nodes.pop();
    processed.add(node);

    const children: string[] = Array.from(map.get(node)?.keys() || []);
    children.forEach((child: string): void => {
      if (!processed.has(child)) {
        nodes.push(child);
      }
    });
  }

  console.log(processed);
  console.log(map);
  return processed.size - 1;
}


function part_b(arr: string[]): number {
  const map = new Map();

  arr.forEach((line: string) => {
    const outer = line.match(LEFT_RE)![1];
    const bags = [...line.matchAll(RIGHT_RE)];

    bags.forEach((b) => {
      const inner = b[2];
      const innerCount = +b[1];

      const values = map.get(outer) || new Map();
      values.set(inner, innerCount)
      map.set(outer, values);
    });
  })

  let total = 0;
  const nodes = ['shiny gold'];
  while (nodes.length) {
    const node = nodes.pop();
    const children: [string, number][] = Array.from(map.get(node)?.entries() || []);
    children.forEach((child: [string, number]): void => {
      total += child[1];

      // oh dear lord
      for (let i = 0; i < child[1]; i++) {
        nodes.push(child[0]);
      }
    });
  }

  return total;
}

function main() {
  let data = fs.readFileSync('input/day7.txt', 'utf8');
  let arr: string[] = data
    .split("\n");

  // console.log(part_a(arr));
  console.log(part_b(arr));
}

main();