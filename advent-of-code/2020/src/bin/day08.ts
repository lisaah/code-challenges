import * as fs from 'fs';

interface State {
  i: number;
  value: number;
}

const action = new Map([
  [
    'nop', (state: State, n: number) => {
      state.i += 1;
    }
  ],
  [
    'jmp', (state: State, n: number) => {
      state.i += n;
    }
  ],
  [
    'acc', (state: State, n: number) => {
      state.i += 1;
      state.value += n;
    }
  ]
]);

function run_program(arr: string[]): State {
  const instructions = new Set();
  const state = {
    i: 0,
    value: 0
  }

  while (!instructions.has(state.i) && state.i < arr.length) {
    const i = state.i;
    instructions.add(i);

    const [instr, num] = parse_instruction(arr[i]);
    const fun = action.get(instr);
    if (fun) {
      fun(state, +num);
    } else {
      console.log('???', arr[i]);
    }
  }

  return state;
}

function parse_instruction(s: string): string[] {
  return s.split(' ');
}

function part_a(arr: string[]): number {
  let result = run_program(arr);
  return result.value;
}

function part_b(arr: string[]): number {
  let result = {i: 0, value: 0};
  for (let i = 0; i < arr.length; i++) {
    const [instr, num] = parse_instruction(arr[i]);
    if (instr !== 'nop' && instr !== 'jmp') {
      continue;
    }

    const clone = [...arr];
    const new_instr = `${instr === 'nop' ? 'jmp' : 'nop'} ${num}`;
    clone[i] = new_instr;
    result = run_program(clone);
    if (result.i >= arr.length) {
      break;
    }
  }

  return result.value;
}

function main() {
  let data = fs.readFileSync('input/day8.txt', 'utf8');
  let arr: string[] = data
    .split("\n");

  console.log(part_a(arr));
  console.log(part_b(arr));
}

main();