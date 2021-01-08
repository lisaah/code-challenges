import * as fs from 'fs';

const addressRegex = /mem\[([0-9]+)\]/;

function extractAddress(instruction: string): number {
  const match = instruction.match(addressRegex);
  if (match) {
    return parseInt(match[1], 10);
  }

  throw new Error('wtf:' + instruction);
}

function dec2bin(dec: number): string {
  return dec
    .toString(2)
    .padStart(36, '0');
}

function applyMask(value: string, mask: string, keep = new Set(['0', '1'])): string {
  const result: string[] = [];
  for (let i = 0; i < mask.length; i++) {
    const maskIndex = mask.length - i - 1;
    const c = mask.charAt(maskIndex);
    if (keep.has(c)) {
      result.push(c);
      continue;
    }

    const valueIndex = value.length - i - 1;
    const v = valueIndex < 0 ? '0' : value.charAt(valueIndex);
    result.push(v);
  }

  result.reverse();
  return result.join('');
}

function applyAddressMask(value: string, mask: string): string[] {
  let addresses: string[][] = [['']];
  // let address: string[] = [];
  for (let i = 0; i < mask.length; i++) {
    const maskIndex = mask.length - i - 1;
    const c = mask.charAt(maskIndex);

    if (c === '1') {
      addresses.forEach((a) => a.push(c));
      continue;
    }

    if (c === '0') {
      const valueIndex = value.length - i - 1;
      const v = valueIndex < 0 ? '0' : value.charAt(valueIndex);
      addresses.forEach((a) => a.push(v));
      continue;
    }

    // c = 'X'
    addresses = addresses.map((a) => [...a, '0']).concat(addresses.map((a) => [...a, '1']));
  }

  return addresses.map((a) => {
    a.reverse();
    return a.join('');
  });
}

function part_a(arr: string[]): number {
  const values: Record<number, string> = {};
  let ogMask = '';

  const instructions = arr.map((s) => s.split(' = '));
  instructions.forEach((i) => {
    const [instruction, value] = i;
    if (instruction === 'mask') {
      ogMask = value;
      return;
    }

    const address = extractAddress(instruction);
    const addressValue = dec2bin(parseInt(value));

    const nValue = applyMask(addressValue, ogMask);
    values[address] = nValue;

    // console.log({
    //   instruction,
    //   value,
    //   bValue: addressValue,
    //   nValue,
    //   ogMask
    // });
  });

  return Object.values(values).reduce((prev: number, curr: string) => prev + parseInt(curr, 2), 0);
}

function part_b(arr: string[]): number {
  const values: Record<number, number> = {};
  let ogMask = '';

  const instructions = arr.map((s) => s.split(' = '));
  instructions.forEach((i) => {
    const [instruction, value] = i;
    if (instruction === 'mask') {
      ogMask = value;
      return;
    }

    const address = dec2bin(extractAddress(instruction));
    const addressValue = parseInt(value, 10);

    const addresses = applyAddressMask(address, ogMask);
    addresses.forEach((a) => values[parseInt(a, 2)] = addressValue);

    // console.log({
    //   instruction,
    //   value,
    //   address,
    //   addressLen: addresses.length,
    //   ogMask
    // });
  });

  return Object.values(values).reduce((prev: number, curr: number) => prev + curr, 0);
}

function main() {
  let data = fs.readFileSync('input/day14.txt', 'utf8');
  let arr: string[] = data
    .split("\n");

  console.log(part_a(arr));
  console.log(part_b(arr));
}

main();