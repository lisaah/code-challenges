package main

import (
	"bufio"
	"fmt"
	"log"
	"os"
	"strings"
)

type Point struct {
	x int
	y int
}

const (
	EAST  = 0
	SOUTH = 1
	WEST  = 2
	NORTH = 3
)

func GCD(a, b int) int {
	for b != 0 {
		t := b
		b = a % b
		a = t
	}
	return a
}

func collectPoints(pt Point, radius int) (pts []Point) {
	for i := pt.x - radius; i <= pt.x+radius; i++ {
		pts = append(pts, Point{i, pt.y + radius})
		pts = append(pts, Point{i, pt.y - radius})
	}

	for j := pt.y - radius + 1; j <= pt.y+radius-1; j++ {
		pts = append(pts, Point{pt.x + radius, j})
		pts = append(pts, Point{pt.x - radius, j})
	}

	return
}

func markLine(pt Point, addPt Point, input [][]int, width int, height int) {
	mX := addPt.x - pt.x
	mY := addPt.y - pt.y
	d := GCD(mX, mY)
	if d < 0 {
		d = -d
	}

	mX /= d
	mY /= d

	x := addPt.x + mX
	y := addPt.y + mY
	for !(x < 0 || y < 0 || y >= width || x >= height) {
		input[y][x] = 0

		x += mX
		y += mY
	}
}

func count(pt Point, input [][]int, width int, height int) (asteroids int) {
	// editing the map, so make copy
	asteroidMap := make([][]int, len(input))
	for i := range input {
		asteroidMap[i] = make([]int, len(input[i]))
		copy(asteroidMap[i], input[i])
	}

	// grassfire
	for radius := 1; pt.x-radius >= 0 || pt.y-radius >= 0 || pt.x+radius < width || pt.y+radius < height; radius++ {
		newPts := collectPoints(pt, radius)
		for _, newPt := range newPts {
			// bounds check
			if newPt.x < 0 || newPt.y < 0 || newPt.x > width-1 || newPt.y > height-1 {
				continue
			}

			// asteroid check
			if asteroidMap[newPt.y][newPt.x] == 0 {
				continue
			}

			asteroids++
			markLine(pt, newPt, asteroidMap, width, height)
		}
	}

	return asteroids
}

func part2(input [][]int, width int, height int) string {
	return ""
}

func part1(input [][]int, width int, height int) int {
	max := 0
	calc := make([][]int, len(input))
	for j, row := range input {
		calc[j] = make([]int, len(row))
		for i, value := range row {
			if value == 1 {
				pt := Point{i, j}
				count := count(pt, input, width, height)
				if count > max {
					max = count
				}

				calc[j][i] = count
			}
		}
	}

	printMap(calc)

	return max
}

func printMap(calc [][]int) {
	for _, row := range calc {
		log.Println(row)
	}
	log.Println("---")
}

func main() {
	file, err := os.Open("day10/input.txt")
	if err != nil {
		log.Fatal(err)
	}
	defer file.Close()

	scanner := bufio.NewScanner(file)
	height := 0
	width := 0
	input := [][]int{}
	for scanner.Scan() {
		nextLine := scanner.Text()
		rawInput := strings.Split(nextLine, "")
		row := []int{}

		width = 0
		for _, word := range rawInput {
			var num int
			if word == "." {
				num = 0
			} else if word == "#" {
				num = 1
			}
			row = append(row, num)
			width++
		}

		input = append(input, row)
		height++
	}

	fmt.Println(part1(input, width, height))
	fmt.Println(part2(input, width, height))

	if err := scanner.Err(); err != nil {
		log.Fatal(err)
	}
}
