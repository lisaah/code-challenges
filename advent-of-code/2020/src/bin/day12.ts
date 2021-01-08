import * as fs from 'fs';


type DIRECTION = 'N' | 'S' | 'E' | 'W';

const DIRECTIONS: Record<DIRECTION, number> = {
  'N': 0,
  'E': 1,
  'S': 2,
  'W': 3
};

const DIRECTION_ORDER: DIRECTION[] = [
  'N',
  'E',
  'S',
  'W',
];

interface State {
  dir: DIRECTION;
  x: number;
  y: number;
  waypoint: [number, number];
}

const action = new Map([
  [
    'N', (state: State, n: number) => {
      state.y += n;
    }
  ],
  [
    'S', (state: State, n: number) => {
      state.y -= n;
    }
  ],
  [
    'E', (state: State, n: number) => {
      state.x += n;
    }
  ],
  [
    'W', (state: State, n: number) => {
      state.x -= n;
    }
  ],
  [
    'L', (state: State, n: number) => {
      const course = Math.floor(n / 90) % 4;
      const offset = (DIRECTIONS[state.dir] - course + 4) % 4
      state.dir = DIRECTION_ORDER[offset];
    }
  ],
  [
    'R', (state: State, n: number) => {
      const course = Math.floor(n / 90) % 4;
      const offset = (DIRECTIONS[state.dir] + course + 4) % 4
      state.dir = DIRECTION_ORDER[offset];
    }
  ],
]);

function calcRotation(x: number, y: number, angle: number): [number, number] {
  const radians = (Math.PI * angle) / 180;
  // console.log(angle);
  return [
    Math.round(x * Math.cos(radians) - y * Math.sin(radians)),
    Math.round(y * Math.cos(radians) + x * Math.sin(radians))
  ];
}
const action2 = new Map([
  [
    'N', (state: State, n: number) => {
      state.waypoint[1] += n;
    }
  ],
  [
    'S', (state: State, n: number) => {
      state.waypoint[1] -= n;
    }
  ],
  [
    'E', (state: State, n: number) => {
      state.waypoint[0] += n;
    }
  ],
  [
    'W', (state: State, n: number) => {
      state.waypoint[0] -= n;
    }
  ],
  [
    'L', (state: State, n: number) => {
      state.waypoint = calcRotation(state.waypoint[0], state.waypoint[1], n);
    }
  ],
  [
    'R', (state: State, n: number) => {
      state.waypoint = calcRotation(state.waypoint[0], state.waypoint[1], -n);
    }
  ],
  [
    'F', (state: State, n: number) => {
      state.x += n * state.waypoint[0];
      state.y += n * state.waypoint[1];
    }
  ],
]);

function parse_instruction(s: string): [string, number] {
  return [s.charAt(0), parseInt(s.slice(1) || '')];
}

function part_a(arr: string[]): number {
  const state: State = {
    dir: 'E',
    x: 0,
    y: 0,
    waypoint: [0, 0]
  }

  for (let i = 0; i < arr.length; i++) {
    const [instr, amount] = parse_instruction(arr[i]);

    const act = action.get(instr === 'F' ? state.dir : instr);
    if (act) {
      act(state, amount)
    } else {
      console.log('poop');
      console.log(state, instr, amount);
    }
  }

  return Math.abs(state.x) + Math.abs(state.y);
}

function part_b(arr: string[]): number {

  const state: State = {
    dir: 'E',
    x: 0,
    y: 0,
    waypoint: [10, 1]
  }

  console.log(state);
  for (let i = 0; i < arr.length; i++) {
    const [instr, amount] = parse_instruction(arr[i]);

    const act = action2.get(instr);
    if (act) {
      act(state, amount)
    } else {
      console.log('poop');
    }
    // console.log(instr, amount, state);
  }

  return Math.abs(state.x) + Math.abs(state.y);
}

function main() {
  let data = fs.readFileSync('input/day12.txt', 'utf8');
  let arr: string[] = data
    .split("\n");

  // console.log(part_a(arr));
  console.log(part_b(arr));
}

main();