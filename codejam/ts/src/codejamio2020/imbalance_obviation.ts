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
  const isOdd = marbles.length % 2 === 1;
  if (isOdd) {
    marbles.unshift(marbles.length + 1);
  }

  // Build graph
  const graph = {};
  for (let i = 0; i < marbles.length; i += 2) {
    // Insert order pairs
    update(i + 1, i + 2, graph);
    update(i + 2, i + 1, graph);

    // Remove order pairs
    update(marbles[i], marbles[i + 1], graph);
    update(marbles[i + 1], marbles[i], graph);
  }

  // Colorize
  const colors = {};
  let insertOrder = [];
  for (let i = 1; i < marbles.length + 1; i++) {
    // Already processed.
    if (!colors[i]) {
      color(i, graph, colors);
    }

    insertOrder.push(colors[i]);
  }

  if (isOdd) {
    insertOrder.pop();
  }

  return insertOrder.join('');
}

function update(node: number, edge: number, graph: Record<number, Set<number>>): void {
  const edges = graph[node] || new Set();
  edges.add(edge);
  graph[node] = edges;
}

function color(m: number, graph: Record<number, Set<number>>, colors: Record<number, string>): void {
  const queue = [m];
  colors[m] = 'L';

  while (queue.length) {
    const node = queue.shift();
    for (let subNode of graph[node]) {
      if (!colors[subNode]) {
        colors[subNode] = OPP_COLOR[colors[node]];
        queue.push(subNode);
      }
    }
  }
}

const OPP_COLOR = {
  L: 'R',
  R: 'L'
};

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

