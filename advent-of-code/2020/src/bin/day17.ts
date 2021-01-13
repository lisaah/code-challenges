import * as fs from 'fs';

interface Range {
  start: number
  end: number
}

interface Constraint {
  label: string
  ranges: Range[]
}

interface Data {
  constraints: Constraint[]
  ticket: number[]
  nearbyTickets: number[][]
}


function parse(arr: string[]): Data {
  const constraints: Constraint[] = [];
  const ticket: number[] = [];
  const nearbyTickets: number[][] = [];

  let parsePhase = 0;
  arr.forEach((n) => {
    if (n.trim() === '') {
      return;
    }

    if (n === 'your ticket:') {
      parsePhase = 1;
      return;
    }

    if (n === 'nearby tickets:') {
      parsePhase = 2;
      return;
    }

    if (parsePhase === 0) {
      const c = /([a-z ]+): ([0-9]+)-([0-9]+) or ([0-9]+)-([0-9]+)/
      const [_, label, r1s, r1e, r2s, r2e] = c.exec(n)!
      constraints.push({
        label,
        ranges: [{
          start: +r1s, end: +r1e
        }, {
          start: +r2s, end: +r2e
        }]
      });
    } else if (parsePhase === 1) {
      n.split(',').forEach((v) => ticket.push(+v));
    } else if (parsePhase === 2) {
      nearbyTickets.push(n.split(',').map((v) => +v));
    }
  });

  return {
    constraints, ticket, nearbyTickets
  };
}

function valid(n: number, c: Constraint): boolean {
  for (let i = 0; i < c.ranges.length; i++) {
    const r = c.ranges[i];
    if (r.start <= n && n <= r.end) {
      return true;
    }
  }

  return false;
}

function part_a(data: Data): number {
  let badCount = 0;

  for (let i = 0; i < data.nearbyTickets.length; i++) {
    const ticket = data.nearbyTickets[i];
    for (let j = 0; j < ticket.length; j++) {
      const number = ticket[j];
      const constraintPasses = data.constraints.filter((constraint) => {
        return valid(number, constraint)
      }).map((c) => c.label);

      if (!constraintPasses.length) {
        badCount += number;
        break;
      }
    }
  }

  return badCount;
}

function part_b(data: Data): number {
  const filteredTickets = data.nearbyTickets.filter((ticket) => {
    for (let j = 0; j < ticket.length; j++) {
      const number = ticket[j];
      const constraintPasses = data.constraints.filter((constraint) => {
        return valid(number, constraint)
      }).map((c) => c.label);
      if (!constraintPasses.length) {
        return false;
      }
    }

    return true;
  });

  const values = filteredTickets[0].map(() => data.constraints.map((c) => c.label));
  let assignedValues = new Set();

  // initial pass based on constraints
  filteredTickets.forEach((ticket) => {
    for (let j = 0; j < ticket.length; j++) {
      const number = ticket[j];
      const constraintPasses = data.constraints.filter((constraint) => {
        return valid(number, constraint)
      }).map((c) => c.label);
      values[j] = [...values[j]].filter((x) => constraintPasses.indexOf(x) > -1);
      if (values[j].length === 1) {
        assignedValues.add(values[j][0]);
      }
    }
  });

  // Progressively filter based on singles found
  while (assignedValues.size < values.length) {
    const vArray = Array.from(assignedValues.values()) as string[];
    const latest = vArray[vArray.length - 1];
    values.forEach((v) => {
      if (v.length < 2) {
        return;
      }

      const index = v.indexOf(latest);
      if (index > -1) {
        v.splice(index, 1);
        if (v.length === 1) {
          assignedValues.add(v[0]);
        }
      }
    });
  }

  // all together now
  return values.flat().reduce((val: number, label: string, i: number) => {
    if (label.indexOf('departure') > -1) {
      val *= data.ticket[i];
    }
    return val;
  }, 1);
}

function main() {
  let input = fs.readFileSync('input/day16.txt', 'utf8');
  let data: Data = parse(input.split('\n'));

  console.log(part_a(data));
  console.log(part_b(data));
}

main();
