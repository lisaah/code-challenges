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
function update(node, edge, graph: Record<number, Set<number>>): void {
  const edges = graph[node] || new Set();
  edges.add(edge);
  graph[node] = edges;
}

function sol(input: string, marbles: number[]): string {
  marbles = [...marbles];

  const isOdd = marbles.length % 2 === 1;
  if (isOdd) {
    marbles.unshift(marbles.length + 1);
  }

  // Build graph
  const graph = {};
  for (let i = 0; i < marbles.length; i += 2) {
    // Insert order
    update(i + 1, i + 2, graph);
    update(i + 2, i + 1, graph);

    // Remove order
    const a_i = marbles[i];
    const a_i_1 = marbles[i+1];
    update(a_i, a_i_1, graph);
    update(a_i_1, a_i, graph);
  }

  // Colorize
  const colors = {};
  let insertOrder = [];
  for (let i = 1; i < marbles.length + 1; i++) {
    const m = i;
    const color = getColor(m, graph, colors);
    const oppColor = OPP_COLOR[color];

    colors[m] = color;
    for (let n of graph[m]) {
      colors[n] = oppColor;
    }

    insertOrder.push(color);
  }

  let removeOrder = [];
  for (let i = 0; i < marbles.length; i++) {
    const m = marbles[i];
    const color = getColor(m, graph, colors);
    removeOrder.push(color);
  }

  // console.log(graph, marbles);
  console.log(insertOrder, isValid(insertOrder));
  console.log(removeOrder, isValid(removeOrder));
  console.log(graph);

  if (isOdd) {
    insertOrder.pop();
  }

  return insertOrder.join('');
}

function getColor(m: number, graph: Record<number, Set<number>>, colors: Record<number, string>): string {
  if (m in colors) {
    return colors[m];
  }

  const nodes = graph[m];
  for (let n of nodes) {
    if (n in colors) {
      const color = colors[n];
      return OPP_COLOR[color];
    }
  }

  return 'L';
}


function isValid(order): boolean {
  let balance = 0;
  for (let i = 0; i < order.length; i++) {
      let n = order[i];
      const offset = n === 'L' ? 1 : -1;
      balance += offset;
      if (Math.abs(balance) > 1) {
          return false;
      }
  }
  return true;
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

