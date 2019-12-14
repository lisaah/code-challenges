package main

import (
	"bufio"
	"os"
	"log"
	"fmt"
	"strings"
	"strconv"
	"sort"
)

var actions = map[string][]int {
	"U": []int{0, 1},
	"D": []int{0, -1},
	"R": []int{1, 0},
	"L": []int{-1, 0},
};

type Point struct {
	x int;
	y int;
	steps int;
}

func getPoints(wire []string) (points []Point) {
	x := 0;
	y := 0;
	steps := 1;
	for _, line := range wire {
		direction := line[:1];
		count, _ := strconv.Atoi(line[1:]);

		for i := 0; i < count; i++ {
			x += actions[direction][0];
			y += actions[direction][1];

			coord := Point{x, y, steps};
			points = append(points, coord);

			steps++;
		}
	}

	return points;
}

func abs(x int) int {
	if x < 0 {
		return -x
	}
	return x
}

func distance(p Point) int {
	return abs(p.x) + abs(p.y);
}

func intersection(a []Point, b []Point) (c []Point) {
	m := make(map[string]bool)

	for _, item := range a {
		m[fmt.Sprintf("%d,%d", item.x, item.y)] = true
	}

	for _, item := range b {
		if _, ok := m[fmt.Sprintf("%d,%d", item.x, item.y)]; ok {
			c = append(c, item)
		}
	}

	return
}

func sum(grid [][]Point, point Point) int {
	totalSteps := 0;
	for i := 0; i < len(grid); i++ {
		wire := grid[i];

		for j:= 0; j < len(wire); j++ {
			p2 := wire[j];
			if point.x == p2.x && point.y == p2.y {
				totalSteps += p2.steps;
				break;
			}
		}
	}

	return totalSteps;
}

func main() {
	file, err := os.Open("day3/input.txt");
	if err != nil {
		log.Fatal(err);
	}
	defer file.Close();

	scanner := bufio.NewScanner(file);

	grid := [][]Point{};
	intersectionPoints := []Point{};

	firstLine := true;
	for scanner.Scan() {
		nextLine := scanner.Text();
		wire := strings.Split(nextLine, ",");

		points := getPoints(wire);
		grid = append(grid, points);

		if (firstLine) {
			intersectionPoints = points;
			firstLine = false;
		} else {
			intersectionPoints = intersection(intersectionPoints, points);
		}
	}

	// Order
	var result Point;
	sort.Slice(intersectionPoints, func(i, j int) bool {
		p1 := intersectionPoints[i];
		p2 := intersectionPoints[j];

		// // p1
		// p1Val := sum(grid, p1);
		// p2Val := sum(grid, p2);
		// return p1Val < p2Val;

		// p2
		return distance(p1) < distance(p2);
	});

	result = intersectionPoints[0];
	log.Printf("%+v, %d\n", result, distance(result));
	log.Printf("%+v, %d\n", result, sum(grid, result));

	if err := scanner.Err(); err != nil {
		log.Fatal(err);
	}
}