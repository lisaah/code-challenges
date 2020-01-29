package main

import (
	"bufio"
	"fmt"
	"log"
	"math"
	"os"
	"sort"
	"strings"
)

type Point struct {
	x int
	y int
}

func gcd(a, b int) int {
	for b != 0 {
		t := b
		b = a % b
		a = t
	}
	return a
}

func magnitude(x, y int) float64 {
	return math.Sqrt(float64(x*x + y*y))
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

func getAsteroids(pt Point, input [][]int) (pts []Point) {
	for i, row := range input {
		for j, val := range row {
			if val == 1 && !(j == pt.x && i == pt.y) {
				pts = append(pts, Point{j, i})
			}
		}
	}

	return
}

func markLine(pt Point, addPt Point, input [][]int, width int, height int) {
	mX := addPt.x - pt.x
	mY := addPt.y - pt.y
	d := gcd(mX, mY)
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
	// totally unnecessary now with angle method
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

func calcAngle(p1 Point, p2 Point) float64 {
	x1 := 0
	y1 := 1
	x2 := p2.x - p1.x
	y2 := -(p2.y - p1.y)

	dot := float64(x1*x2 + y1*y2)
	magnitude := float64(magnitude(x1, y1) * magnitude(x2, y2))
	radianAngle := math.Acos(dot / magnitude)

	angle := radianAngle * 180 / math.Pi

	if x2 < 0 {
		angle = 360 - angle
	}

	return angle
}

func remove(slice []Point, i int) []Point {
	copy(slice[i:], slice[i+1:])
	return slice[:len(slice)-1]
}

func part2(input [][]int, width int, height int) string {
	max := 0
	var maxPt Point

	for j, row := range input {
		for i, value := range row {
			if value == 1 {
				pt := Point{i, j}
				count := count(pt, input, width, height)
				if count > max {
					max = count
					maxPt = pt
				}
			}
		}
	}

	conflicts := make(map[string]int)
	asteroids := getAsteroids(maxPt, input)
	sort.Slice(asteroids, func(i, j int) bool {
		p1 := asteroids[i]
		p2 := asteroids[j]
		p1Angle := calcAngle(maxPt, p1)
		p2Angle := calcAngle(maxPt, p2)

		if p1Angle != p2Angle {
			return p1Angle < p2Angle
		}

		return magnitude(maxPt.x-p1.x, maxPt.y-p1.y) < magnitude(maxPt.x-p2.x, maxPt.y-p2.y)
	})

	i := 0
	iteration := 0
	for len(asteroids) > 0 {
		removeIndices := []int{}
		for index, asteroid := range asteroids {
			angle := fmt.Sprintf("%.6f", calcAngle(maxPt, asteroid))

			pastIteration, ok := conflicts[angle]
			if !ok || pastIteration < iteration {
				i++
				log.Println(i, asteroid)
				if i == 200 {
					return fmt.Sprintf("pt: %+v", asteroid)
				}
				conflicts[angle] = iteration
				removeIndices = append(removeIndices, index)
			}
		}

		for index := len(removeIndices) - 1; index >= 0; index-- {
			asteroids = remove(asteroids, removeIndices[index])
		}

		iteration++
	}

	return fmt.Sprintf("fail")
}

func part1(input [][]int, width int, height int) int {
	max := 0

	// calc := make([][]int, len(input))
	for j, row := range input {
		// calc[j] = make([]int, len(row))
		for i, value := range row {
			if value == 1 {
				pt := Point{i, j}
				count := count(pt, input, width, height)
				if count > max {
					max = count
				}

				// calc[j][i] = count
			}
		}
	}

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

	fmt.Println("p1", part1(input, width, height))
	fmt.Println("p2", part2(input, width, height))

	if err := scanner.Err(); err != nil {
		log.Fatal(err)
	}
}
