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
async function parse(inputStream: StdinLineStream): Promise<[string, number[]]> {
  return [await inputStream.getLine(), await inputStream.getLineAsNumbers()]
}

function sol(input: string, marbles: number[]): string {
  return "<SOLUTION>";
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

