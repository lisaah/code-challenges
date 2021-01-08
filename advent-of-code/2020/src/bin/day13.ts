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

function calc_offset(time: number, a: number): number {
  const offset = (Math.floor(time / a) + 1) * a - time;
  return offset;

}

function part_a(arr: string[]): number {
  const time = +arr[0];
  const buses = arr[1].split(',')
    .filter((s) => s !== 'x')
    .map((s) => +s);

  buses.sort((a, b) => {
    return calc_offset(time, a) - calc_offset(time, b);
  });

  return calc_offset(time, buses[0]) * buses[0];
}

function find_pair(buses: [number, number][], offset: number): [[number, number], number] {
  const b0 = buses[0];
  const b1 = buses[1];

  while ((offset + b1[1]) % b1[0] !== 0) {
    offset += b0[0];
  }

  console.log('good', offset, b0, b1)
  return [[b0[0] * b1[0], 0], offset];
}

function part_b(arr: string[]): number {
  const buses: [number, number][] = arr[1].split(',')
    .map((s, index) => [s, index])
    .filter((b) => b[0] !== 'x')
    .map((b) => [+b[0], +b[1]]);

  let offset = 0;
  while (buses.length > 1) {
    const [pair, newOffset] = find_pair(buses, offset);
    buses.splice(0, 2, pair);
    offset = newOffset;
  }

  console.log(buses);


  return offset;
}

function main() {
  let data = fs.readFileSync('input/day13.txt', 'utf8');
  let arr: string[] = data
    .split("\n");

  console.log(part_a(arr));
  console.log(part_b(arr));
}

main();