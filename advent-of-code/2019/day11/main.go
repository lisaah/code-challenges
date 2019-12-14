package main

import (
	"bufio"
	"os"
	"log"
	"fmt"
	"strconv"
	"strings"
)

func parse(instruction int) (op int, modes []int) {
	op = instruction % 100;
	modes = []int{
		(instruction / 100) % 10,
		(instruction / 1000) % 10,
		(instruction / 10000) % 10,
	}
	return;
}

func copyProgram(input []int) (c []int) {
	for i := 0; i < len(input); i++ {
		c = append(c, input[i]);
	}
	return;
}

func permute(arr []int) [][]int{
	var helper func([]int, int)
	res := [][]int{}

	helper = func(arr []int, n int){
			if n == 1{
					tmp := make([]int, len(arr))
					copy(tmp, arr)
					res = append(res, tmp)
			} else {
					for i := 0; i < n; i++{
							helper(arr, n - 1)
							if n % 2 == 1{
									tmp := arr[i]
									arr[i] = arr[n - 1]
									arr[n - 1] = tmp
							} else {
									tmp := arr[0]
									arr[0] = arr[n - 1]
									arr[n - 1] = tmp
							}
					}
			}
	}
	helper(arr, len(arr))
	return res
}

func createMachine(code []int) *Machine {
	return &Machine{copyProgram(code), 0, make(chan int, 2), make(chan int, 2), -1, 0};
}

type Machine struct {
	code []int;
	pointer int;
	input chan int;
	output chan int;
	latest int;
	relativeBase int;
}

const (
	position = 0
	immediate = 1
	relative = 2
)

func (m *Machine) load(index int) int {
	for len(m.code) < index + 1 {
		// dumb
		m.code = append(m.code, 0);
	}

	return m.code[index];
}

func (m *Machine) read(offset int, mode int) int {
	if mode == position {
		m.load(m.load(m.pointer + offset));
		return m.code[m.code[m.pointer + offset]];
	} else if mode == immediate {
		m.load(m.pointer + offset);
		return m.code[m.pointer + offset];
	} else if mode == relative {
		m.load(m.load(m.pointer + offset) + m.relativeBase);
		return m.code[m.code[m.pointer + offset] + m.relativeBase];
	}


	log.Fatal("Bad read mode", mode);
	return -1;
}

func (m *Machine) write(offset int, mode int, value int) {
	if mode == position {
		m.load(m.load(m.pointer + offset));
		m.code[m.code[m.pointer + offset]] = value;
		return;
	} else if mode == relative {
		m.load(m.load(m.pointer + offset) + m.relativeBase);
		m.code[m.code[m.pointer + offset] + m.relativeBase] = value;
		return;
	}

	log.Fatal("Bad write mode", mode);
}

func (m *Machine) pop() int {
	return <- m.output;
}

func (m *Machine) push(input int) {
	m.input <- input;
}

func (m *Machine) run() {
	for (m.runNext() != 99) {}
}

func (m *Machine) runNext() int {
	op, modes := parse(m.read(0, immediate))

	// outIndex := m.load(m.pointer + 1);
	// params := []int{m.read(1, modes[0]), m.read(2, modes[1])};
	// log.Println(m.pointer, m.relativeBase, ":", op, modes, params, "=>", outIndex);

	switch (op) {
		case 99:
			m.pointer += 1;
			break;
		case 1:
			m.write(3, modes[2], m.read(1, modes[0]) + m.read(2, modes[1]),);
			m.pointer += 4;
			break;
		case 2:
			m.write(3, modes[2], m.read(1, modes[0]) * m.read(2, modes[1]));
			m.pointer += 4;
			break;
		case 3:
			m.write(1, modes[0], <- m.input);
			m.pointer += 2;
			break;
		case 4:
			m.latest = m.read(1, modes[0])
			// m.output <- m.latest;
			log.Println(m.latest);
			m.pointer += 2;
			break;
		case 5:
			p1 := m.read(1, modes[0]);
			p2 := m.read(2, modes[1]);

			if p1 != 0 {
				m.pointer = p2;
			} else {
				m.pointer += 3;
			}
			break;
		case 6:
			p1 := m.read(1, modes[0]);
			p2 := m.read(2, modes[1]);

			if p1 == 0 {
				m.pointer = p2;
			} else {
				m.pointer += 3;
			}

			break;
		case 7:
			p1 := m.read(1, modes[0]);
			p2 := m.read(2, modes[1]);

			if p1 < p2 {
				m.write(3, modes[2], 1);
			} else {
				m.write(3, modes[2], 0);
			}

			m.pointer += 4;
			break;
		case 8:
			p1 := m.read(1, modes[0]);
			p2 := m.read(2, modes[1]);

			if p1 == p2 {
				m.write(3, modes[2], 1);
			} else {
				m.write(3, modes[2], 0);
			}

			m.pointer += 4;
			break;
		case 9:
			p1 := m.read(1, modes[0]);

			m.relativeBase += p1;
			m.pointer += 2;
			break;
		default:
			log.Fatal("Error code", op);
	}

	return op;
}

func part1(code []int, value int) int {
	machine := createMachine(code);
	machine.push(value);
	machine.run();
	return machine.latest;
}

func part2(code []int, value int) int {
	machine := createMachine(code);
	machine.push(value);
	machine.run();
	return machine.latest;
}

func main() {
	file, err := os.Open("day11/input.txt");
	if err != nil {
		log.Fatal(err);
	}
	defer file.Close();

	scanner := bufio.NewScanner(file);

	scanner.Scan()
	nextLine := scanner.Text();
	stringInput := strings.Split(nextLine, ",");

	input := []int{};
	for _, word := range stringInput {
		num, err := strconv.Atoi(word);
		if (err != nil) {
			log.Fatal(err);
		}

		input = append(input, num)
	}

	fmt.Println("p1", part1(input, 1));
	fmt.Println("p2", part2(input, 2));

	if err := scanner.Err(); err != nil {
		log.Fatal(err);
	}
}