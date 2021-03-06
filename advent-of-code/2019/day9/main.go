package main

import (
	"bufio"
	"fmt"
	"log"
	"os"
	"strconv"
	"strings"
)

func parse(instruction int) (op int, modes []int) {
	op = instruction % 100
	modes = []int{
		(instruction / 100) % 10,
		(instruction / 1000) % 10,
		(instruction / 10000) % 10,
	}
	return
}

func copyProgram(input []int) (c []int) {
	for i := 0; i < len(input); i++ {
		c = append(c, input[i])
	}
	return
}

func CreateMachine(code []int) *Machine {
	return &Machine{copyProgram(code), 0, make(chan int, 2), make(chan int, 2), -1, 0, false}
}

type Machine struct {
	code         []int
	pointer      int
	input        chan int
	Output       chan int
	Latest       int
	relativeBase int
	Done         bool
}

const (
	position  = 0
	immediate = 1
	relative  = 2
)

func (m *Machine) Load(index int) int {
	for len(m.code) < index+1 {
		// dumb
		m.code = append(m.code, 0)
	}

	return m.code[index]
}

func (m *Machine) Read(offset int, mode int) int {
	if mode == position {
		m.Load(m.Load(m.pointer + offset))
		return m.code[m.code[m.pointer+offset]]
	} else if mode == immediate {
		m.Load(m.pointer + offset)
		return m.code[m.pointer+offset]
	} else if mode == relative {
		m.Load(m.Load(m.pointer+offset) + m.relativeBase)
		return m.code[m.code[m.pointer+offset]+m.relativeBase]
	}

	log.Fatal("Bad Read mode", mode)
	return -1
}

func (m *Machine) Write(offset int, mode int, value int) {
	if mode == position {
		m.Load(m.Load(m.pointer + offset))
		m.code[m.code[m.pointer+offset]] = value
		return
	} else if mode == relative {
		m.Load(m.Load(m.pointer+offset) + m.relativeBase)
		m.code[m.code[m.pointer+offset]+m.relativeBase] = value
		return
	}

	log.Fatal("Bad Write mode", mode)
}

func (m *Machine) Pop() int {
	return <-m.Output
}

func (m *Machine) Push(input int) {
	m.input <- input
}

func (m *Machine) Run() {
	for m.RunNext() != 99 {
	}
}

func (m *Machine) RunNext() int {
	op, modes := parse(m.Read(0, immediate))

	switch op {
	case 99:
		m.pointer += 1
		m.Done = true
		break
	case 1:
		m.Write(3, modes[2], m.Read(1, modes[0])+m.Read(2, modes[1]))
		m.pointer += 4
		break
	case 2:
		m.Write(3, modes[2], m.Read(1, modes[0])*m.Read(2, modes[1]))
		m.pointer += 4
		break
	case 3:
		m.Write(1, modes[0], <-m.input)
		m.pointer += 2
		break
	case 4:
		m.Latest = m.Read(1, modes[0])
		m.Output <- m.Latest
		log.Println(m.Latest)
		m.pointer += 2
		break
	case 5:
		p1 := m.Read(1, modes[0])
		p2 := m.Read(2, modes[1])

		if p1 != 0 {
			m.pointer = p2
		} else {
			m.pointer += 3
		}
		break
	case 6:
		p1 := m.Read(1, modes[0])
		p2 := m.Read(2, modes[1])

		if p1 == 0 {
			m.pointer = p2
		} else {
			m.pointer += 3
		}

		break
	case 7:
		p1 := m.Read(1, modes[0])
		p2 := m.Read(2, modes[1])

		if p1 < p2 {
			m.Write(3, modes[2], 1)
		} else {
			m.Write(3, modes[2], 0)
		}

		m.pointer += 4
		break
	case 8:
		p1 := m.Read(1, modes[0])
		p2 := m.Read(2, modes[1])

		if p1 == p2 {
			m.Write(3, modes[2], 1)
		} else {
			m.Write(3, modes[2], 0)
		}

		m.pointer += 4
		break
	case 9:
		p1 := m.Read(1, modes[0])

		m.relativeBase += p1
		m.pointer += 2
		break
	default:
		log.Fatal("Error code", op)
	}

	return op
}

func part1(code []int, value int) int {
	machine := CreateMachine(code)
	machine.Push(value)
	machine.Run()
	return machine.Latest
}

func part2(code []int, value int) int {
	machine := CreateMachine(code)
	machine.Push(value)
	machine.Run()
	return machine.Latest
}

func main() {
	file, err := os.Open("day9/input.txt")
	if err != nil {
		log.Fatal(err)
	}
	defer file.Close()

	scanner := bufio.NewScanner(file)

	scanner.Scan()
	nextLine := scanner.Text()
	stringInput := strings.Split(nextLine, ",")

	input := []int{}
	for _, word := range stringInput {
		num, err := strconv.Atoi(word)
		if err != nil {
			log.Fatal(err)
		}

		input = append(input, num)
	}

	fmt.Println("p1", part1(input, 1))
	fmt.Println("p2", part2(input, 2))

	if err := scanner.Err(); err != nil {
		log.Fatal(err)
	}
}
