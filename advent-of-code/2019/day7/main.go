package main

import (
	"bufio"
	"os"
	"log"
	"fmt"
	"strconv"
	"strings"
	"sync"
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

func (m *Machine) load(offset int, mode int) int {
	if mode == 0 {
		// position
		return m.code[m.code[m.pointer + offset]];
	} else if mode == 1 {
		// immediate
		return m.code[m.pointer + offset];
	} else if mode == 2{
		// relative
		return m.code[m.relativeBase + m.code[m.pointer + offset]];
	}

	return -1;
}

func (m *Machine) read() int {
	return <- m.output;
}

func (m *Machine) write(input int) {
	m.input <- input;
}

func (m *Machine) run() {
	for (m.runNext() != 99) {}
}

func (m *Machine) runNext() int {
	op, modes := parse(m.code[m.pointer])
	switch (op) {
		case 99:
			m.pointer += 1;
			break;
		case 1:
			p1 := m.load(1, modes[0]);
			p2 := m.load(2, modes[1]);
			output := m.code[m.pointer + 3];

			m.code[output] = p1 + p2;
			m.pointer += 4;
			break;
		case 2:
			p1 := m.load(1, modes[0]);
			p2 := m.load(2, modes[1]);
			output := m.code[m.pointer + 3];

			m.code[output] = p1 * p2;
			m.pointer += 4;
			break;
		case 3:
			p1 := m.code[m.pointer + 1];
			m.code[p1] = <-m.input
			m.pointer += 2;
			break;
		case 4:
			m.latest = m.load(1, modes[0])
			m.output <- m.latest;
			m.pointer += 2;
			break;
		case 5:
			p1 := m.load(1, modes[0]);
			p2 := m.load(2, modes[1]);

			if p1 != 0 {
				m.pointer = p2;
			} else {
				m.pointer += 3;
			}
			break;
		case 6:
			p1 := m.load(1, modes[0]);
			p2 := m.load(2, modes[1]);

			if p1 == 0 {
				m.pointer = p2;
			} else {
				m.pointer += 3;
			}

			break;
		case 7:
			p1 := m.load(1, modes[0]);
			p2 := m.load(2, modes[1]);
			output := m.code[m.pointer + 3];

			if p1 < p2 {
				m.code[output] = 1;
			} else {
				m.code[output] = 0;
			}

			m.pointer += 4;
			break;
		case 8:
			p1 := m.load(1, modes[0]);
			p2 := m.load(2, modes[1]);
			output := m.code[m.pointer + 3];

			if p1 == p2 {
				m.code[output] = 1;
			} else {
				m.code[output] = 0;
			}

			m.pointer += 4;
			break;
		case 9:
			p1 := m.load(1, modes[0]);

			m.relativeBase += p1;
			m.pointer += 2;
			break;
		default:
			log.Fatal("Error code", op);
	}

	return op;
}

func part1(code []int) int {
	max := 0;
	permutations := permute([]int{0, 1, 2, 3, 4});

	for _, permutation := range permutations {
		input := 0;
		for _, phaseShift := range permutation {
			machine := createMachine(code);

			machine.write(phaseShift);
			machine.write(input);
			machine.run();

			input = machine.read();
		}

		if input > max {
			max = input;
		}
	}

	return max;
}

func part2(code []int) int {
	max := 0;
	permutations := permute([]int{5, 6, 7, 8, 9});

	for _, permutation := range permutations {
		n := len(permutation);

		// Initialize amplifying machines.
		machines := make([]*Machine, n)
		for index, _ := range machines {
			machines[index] = createMachine(code);
		}

		// Setup initial input
		for index, phaseShift := range permutation {
			machine := machines[index];
			if (index == 0) {
				machine.input = machines[n - 1].output;
				machine.write(phaseShift);
				machine.write(0);
			} else {
				machine.input = machines[index - 1].output;
				machine.write(phaseShift);
			}
		}

		// Let rip
		wg := sync.WaitGroup{}
		for index := range machines {
			wg.Add(1)
			go func(index int) {
				defer wg.Done()
				machines[index].run()
			}(index)
		}
		wg.Wait()

		output := machines[n - 1].latest;
		if output > max {
			max = output;
		}
	}

	return max;
}

func main() {
	file, err := os.Open("day7/input.txt");
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

	// fmt.Println("p1", part1(input));
	fmt.Println("p2", part2(input));

	if err := scanner.Err(); err != nil {
		log.Fatal(err);
	}
}