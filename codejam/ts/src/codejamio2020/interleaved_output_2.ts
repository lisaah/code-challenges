import * as readline from 'readline'

/**
 * Courtesy of: https://github.com/bkolobara/stdin-line
 */
const WHITESPACE_REGEX = /\s+/;
class StdinLineStream {
  rl: readline.Interface;
  buffer: Array<string>;
  resolvers: Array<(value?: string) => void>;

  constructor(stream?: NodeJS.ReadableStream) {
    let stdin: NodeJS.ReadableStream;
    if (stream) {
      stdin = stream;
    } else {
      stdin = process.stdin;
    }
    this.rl = readline.createInterface({
      input: stdin
    });
    this.buffer = [];
    this.resolvers = [];
    this.rl.on('line', (input: string) => {
      if (this.resolvers.length > 0) {
        let resolver = this.resolvers.shift();
        if (resolver) {
          resolver(input);
        }
      } else {
        this.buffer.push(input);
      }
    });
  }

  /* Returns a promise that will be resolved when the next line from stdin is available. */
  async getLine(): Promise<string> {
    return new Promise((resolve, reject) => {
      if (this.buffer.length > 0) {
        this.rl.pause();
        resolve(this.buffer.shift());
      } else {
        this.rl.resume();
        this.resolvers.push(resolve);
      }
    });
  }

  /* Returns a promise containing an array of numbers that are parsed from the next stdin line. */
  async getLineAsNumbers(): Promise<Array<number>> {
    const line = await this.getLine();
    const split_whitespace = line.split(WHITESPACE_REGEX);
    return split_whitespace.map(num => +num);
  }

  close() {
    this.rl.close();
  }
}

/**
 * Solution
 */
async function parse(inputStream: StdinLineStream): Promise<[string]> {
  return [await inputStream.getLine()]
}

// const MACHINE_INDEX = {
//   IO: 0b1000,
//   Io: 0b100,
//   iO: 0b10,
//   io: 0b1,
// }

// function sol(input: string): string {
//   const dp = new Array(input.length + 1)
//     .fill(null)
//     .map(() => new Array(16).fill(-1));
//   dp[0][0] = 0;

//   const n = input.length;
//   for (let i = 0; i < n; i++) {
//     const c = input.charAt(i);

//     for (let j = 0; j < 16; j++) {
//       // Invalid state
//       if (dp[i][j] < 0) {
//         continue;
//       }

//       if (c === 'I' || c === 'i') {
//         const checkMachines = c === 'I' ?
//           [MACHINE_INDEX.IO, MACHINE_INDEX.Io] :
//           [MACHINE_INDEX.io, MACHINE_INDEX.iO];

//         checkMachines.forEach((machineIndex) => {
//           // If machine ain't started, start
//           if (!(machineIndex & j)) {
//             dp[i + 1][j | machineIndex] = Math.max(dp[i + 1][j | machineIndex], dp[i][j])
//           }
//         });
//       }

//       if (c === 'O' || c === 'o') {
//         const checkMachines = c === 'O' ?
//           [MACHINE_INDEX.IO, MACHINE_INDEX.iO] :
//           [MACHINE_INDEX.Io, MACHINE_INDEX.io];

//         checkMachines.forEach((machineIndex) => {
//           // If machine started, finish
//           if (machineIndex & j) {
//             const score = machineIndex === MACHINE_INDEX.IO ? 1 : 0;
//             dp[i + 1][j ^ machineIndex] = Math.max(dp[i + 1][j ^ machineIndex], dp[i][j] + score)
//           }
//         });
//       }
//     }
//   }

//   const total = dp[n][0];
//   return `${total}`;
// }


const MACHINE_INDEX = {
  IO: 1<<0,
  io: 1<<1,
  Io: 1<<2,
  iO: 1<<3
}

let broadcast: string;
let cache: number[][];

function sol(input: string): string {
  broadcast = input;
  cache = new Array(input.length)
    .fill(null)
    .map(() => new Array(16).fill(-1))

  const total = calc(0, 0);
  return `${total}`;
}

function calc(index: number, machineState: number): number {
  if (index === broadcast.length) {
    return 0;
  }

  if (cache[index][machineState] > -1) {
    return cache[index][machineState];
  }

  const c = broadcast.charAt(index);
  let counts = [];
  if (c === 'I' || c === 'i') {
    const machines = c === 'I' ?
      [MACHINE_INDEX.IO, MACHINE_INDEX.Io] : [MACHINE_INDEX.io, MACHINE_INDEX.iO];
    machines.forEach((machine_index) => {
      if (!(machineState & machine_index)) {
        counts.push(calc(index + 1, machineState | machine_index));
      }
    })
  }

  if (c === 'O' || c === 'o') {
    const machines = c === 'O' ?
      [MACHINE_INDEX.IO, MACHINE_INDEX.iO] : [MACHINE_INDEX.io, MACHINE_INDEX.Io];
    machines.forEach((machine_index) => {
      if (machineState & machine_index) {
        let updateCount = machine_index === MACHINE_INDEX.IO ? 1 : 0;
        counts.push(calc(index + 1, machineState ^ machine_index) + updateCount);
      }
    })
  }

  const result = Math.max.apply(null, counts);
  cache[index][machineState] = result;
  return result;
}

/**
 * old jank
 */
// function calc(index: number, input: string, machineState: MachineState, currCount: number): number[] {
//   if (index === input.length) {
//     return [currCount];
//   }

//   const c =  input.charAt(index);
//   let counts = [];
//   if (c === 'I' || c === 'i') {
//     const machines = c === 'I' ?
//       [MACHINE_INDEX.IO, MACHINE_INDEX.Io] : [MACHINE_INDEX.io, MACHINE_INDEX.iO];
//     machines.forEach((machine_index) => {
//       if (!machineState[machine_index]) {
//         const copy = [...machineState];
//         copy[machine_index] = true;
//         counts = counts.concat(calc(index + 1, input, copy, currCount));
//       }
//     })
//   }

//   if (c === 'O' || c === 'o') {
//     const machines = c === 'O' ?
//       [MACHINE_INDEX.IO, MACHINE_INDEX.iO] : [MACHINE_INDEX.io, MACHINE_INDEX.Io];
//     machines.forEach((machine_index) => {
//       if (machineState[machine_index]) {
//         const copy = [...machineState];
//         copy[machine_index] = false;
//         let updateCount = machine_index === MACHINE_INDEX.IO ? currCount + 1 : currCount;
//         counts = counts.concat(calc(index + 1, input, copy, updateCount));
//       }
//     })
//   }

//   return counts;
// }

// const MACHINE_INDEX = {
//   IO: 0,
//   io: 1,
//   Io: 2,
//   iO: 3
// }

// type MachineState = boolean[];
// function sol(input: string): string {
//   const machineState = [false, false, false, false]
//   const counts = calc(0, input, machineState, 0);
//   const total = Math.max.apply(null, counts);
//   return `${total}`;
// }

/**
 * I/O
 */
async function main() {
  let inputStream = new StdinLineStream();

  let [numberOfCases] = await inputStream.getLineAsNumbers();
  for (let i = 1; i < numberOfCases + 1; i++) {
    let input = await parse(inputStream);
    let output = sol(...input);
    console.log(`Case #${i}: ${output}`);
  }

  inputStream.close();
}
main();

