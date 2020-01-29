package main

import (
	"fmt"
	"log"

	"github.com/code-challenges/advent-of-code/2019/shared"
)

type Moon struct {
	pos []int
	vel []int
}

func (m *Moon) updateVelocity(vel []int) {
	for i := range vel {
		m.vel[i] += vel[i]
	}
}

func (m *Moon) updatePosition() {
	for i := range m.vel {
		m.pos[i] += m.vel[i]
	}
}

func calcGravity(index int, bodies []Moon) []int {
	gravity := []int{0, 0, 0}

	curMoon := bodies[index]
	for i, moon := range bodies {
		if i == index {
			continue
		}

		for j := range curMoon.pos {
			if curMoon.pos[j] > moon.pos[j] {
				// pull in
				gravity[j]--
			} else if curMoon.pos[j] < moon.pos[j] {
				// pull out
				gravity[j]++
			}
		}
	}

	return gravity
}

func calcEnergy(moons []Moon) int {
	totalEnergy := 0
	for _, moon := range moons {
		potEnergy := 0
		for _, posX := range moon.pos {
			potEnergy += shared.AbsInt(posX)
		}

		kineticEnergy := 0
		for _, velX := range moon.vel {
			kineticEnergy += shared.AbsInt(velX)
		}

		totalEnergy += potEnergy * kineticEnergy
	}

	return totalEnergy
}

func part2(input [][]int) string {
	return fmt.Sprintf("fail")
}

func part1(input [][]int, stop int) string {
	moons := make([]Moon, len(input))
	for i, row := range input {
		moons[i] = Moon{
			row,
			[]int{0, 0, 0},
		}
	}

	for i := 0; i < stop; i++ {
		// get updates
		updates := make([][]int, len(moons))
		for j := range moons {
			gravity := calcGravity(j, moons)
			updates[j] = gravity
		}

		// apply updates
		for j, moon := range moons {
			moon.updateVelocity(updates[j])
			moon.updatePosition()
		}

		log.Println("After", stop, "steps")
		for _, moon := range moons {
			log.Println(moon)
		}
		log.Println()
	}

	return fmt.Sprintf("%d", calcEnergy(moons))
}

func main() {
	fmt.Println("p1", part1(inputA, 1000))
	fmt.Println("p2", part2(inputA))
}

// sample
// var inputA = [][]int{
// 	[]int{-1, 0, 2},
// 	[]int{2, -10, -7},
// 	[]int{4, -8, 8},
// 	[]int{3, 5, -1},
// }

var inputA = [][]int{
	[]int{-13, 14, -7},
	[]int{-18, 9, 0},
	[]int{0, -3, -3},
	[]int{-15, 3, -13},
}
