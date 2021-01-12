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
function calc(index: number, input: string, machineState: MachineState, currPath: string[]): string[][] {
  if (index === input.length) {
    return [currPath];
  }

  const c =  input.charAt(index);
  let paths = [];
  if (c === 'I' || c === 'i') {
    const machines = c === 'I' ?
      [MACHINE_INDEX.IO, MACHINE_INDEX.Io] : [MACHINE_INDEX.io, MACHINE_INDEX.iO];
    machines.forEach((machine_index) => {
      if (!machineState[machine_index]) {
        const copy = [...machineState];
        copy[machine_index] = true;
        const updatedPaths = currPath;
        paths = paths.concat(calc(index + 1, input, copy, currPath));
      }
    })
  }

  if (c === 'O' || c === 'o') {
    const machines = c === 'O' ?
      [MACHINE_INDEX.IO, MACHINE_INDEX.iO] : [MACHINE_INDEX.io, MACHINE_INDEX.Io];
    machines.forEach((machine_index) => {
      if (machineState[machine_index]) {
        const copy = [...machineState];
        copy[machine_index] = false;
        const updatedPaths = [...currPath, MACHINE_INDEX_REVERSE[machine_index]];
        paths = paths.concat(calc(index + 1, input, copy, updatedPaths));
      }
    })
  }

  return paths;
}

const MACHINE_INDEX = {
  IO: 0,
  io: 1,
  Io: 2,
  iO: 3
}
const MACHINE_INDEX_REVERSE = ['IO', 'io', 'Io', 'iO'];

type MachineState = boolean[];
function sol(input: string): string {
  const machineState = [false, false, false, false]
  const paths = calc(0, input, machineState, []);
  const total = Math.max.apply(null, paths.map((path) => path.filter((pair) => pair === 'IO').length))
  return `${total}`;
}

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

