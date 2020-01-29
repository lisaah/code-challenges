package main

import (
	"bufio"
	"fmt"
	"log"
	"os"
	"strconv"
	"strings"
	"sync"

	"github.com/code-challenges/advent-of-code/2019/shared"
)

func CreateRobot(code []int) Robot {
	machine := shared.CreateMachine(code)
	return Robot{machine, shared.Point{X: 0, Y: 0}, 0, make(map[string]int), 0, 0}
}

type Robot struct {
	machine    *shared.Machine
	pos        shared.Point
	dir        Direction
	panels     map[string]int
	halfWidth  int
	halfHeight int
}

const (
	NORTH = 0
	EAST  = 1
	SOUTH = 2
	WEST  = 3
)

func directionString(dir Direction) string {
	if dir == 0 {
		return "NORTH"
	}
	if dir == 1 {
		return "EAST"
	}
	if dir == 2 {
		return "SOUTH"
	}
	if dir == 3 {
		return "WEST"
	}

	return ""
}

var moveMap = map[Direction]shared.Point{
	NORTH: shared.Point{X: 0, Y: -1},
	EAST:  shared.Point{X: 1, Y: 0},
	SOUTH: shared.Point{X: 0, Y: 1},
	WEST:  shared.Point{X: -1, Y: 0},
}

type Direction int

func (r *Robot) move(turn int) {
	if turn == 0 {
		r.dir = (r.dir + 3) % 4
	} else if turn == 1 {
		r.dir = (r.dir + 1) % 4
	} else {
		log.Fatal("Bad direction", turn)
	}

	moveVector := moveMap[r.dir]
	r.pos.X += moveVector.X
	r.pos.Y += moveVector.Y

	if shared.AbsInt(r.pos.X) > r.halfWidth {
		r.halfWidth = shared.AbsInt(r.pos.X)
	}

	if shared.AbsInt(r.pos.Y) > r.halfHeight {
		r.halfHeight = shared.AbsInt(r.pos.Y)
	}
}

func (r *Robot) paint(color int) {
	r.panels[r.pos.String()] = color
}

func (r *Robot) getPanelColor(pt shared.Point) int {
	color, ok := r.panels[pt.String()]
	if !ok {
		return 0
	}

	return color
}

func (r *Robot) printState() {
	screen := make([][]string, r.halfHeight*2)
	for i := range screen {
		screen[i] = make([]string, r.halfWidth*2)
		for j := range screen[i] {
			screen[i][j] = "."
		}
	}

	for key, val := range r.panels {
		coords := strings.Split(key, ",")
		x, _ := strconv.Atoi(coords[0])
		y, _ := strconv.Atoi(coords[1])

		printVal := "."
		if val == 1 {
			printVal = "#"
		}

		remapX := x + int(r.halfWidth/2)
		remapY := y + int(r.halfHeight/2)
		screen[remapY][remapX] = printVal
	}

	for _, row := range screen {
		log.Println(row)
	}

	log.Println("---")
}

func (r *Robot) RunPainting() {
	for !r.machine.Done {
		color := r.machine.Pop()
		dir := r.machine.Pop()

		r.paint(color)
		r.move(dir)

		curColor := r.getPanelColor(r.pos)
		r.machine.Push(curColor)
	}
}

func (r *Robot) Run() {

	wg := sync.WaitGroup{}
	wg.Add(1)
	go func(index int) {
		defer wg.Done()
		r.machine.Run()
	}(0)

	wg.Add(1)
	go func(index int) {
		defer wg.Done()
		r.RunPainting()
	}(1)

	wg.Wait()

}

func part1(code []int, value int) int {
	r := CreateRobot(code)
	r.machine.Push(value)
	r.Run()

	return len(r.panels)
}

func part2(code []int, value int) int {
	r := CreateRobot(code)
	r.panels["0,0"] = 1
	r.machine.Push(value)
	r.Run()

	r.printState()
	return len(r.panels)
}

func main() {
	file, err := os.Open("day11/input.txt")
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

	fmt.Println("p1", part1(input, 0))
	fmt.Println("p2", part2(input, 1))

	if err := scanner.Err(); err != nil {
		log.Fatal(err)
	}
}
