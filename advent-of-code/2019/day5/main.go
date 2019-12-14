package main

import (
	"bufio"
	"os"
	"log"
	"fmt"
	"strconv"
	"strings"
)

func loadParam(numInput []int, mode int, value int) int {
	if mode == 0 {
		return numInput[value];
	} else {
		return value;
	}
}

func parse(instruction int) (A int, B int, C int, opCode int) {
	A = (instruction / 10000) % 10;
	B = (instruction / 1000) % 10;
	C = (instruction / 100) % 10;
	opCode = instruction % 100;
	return;
}

func calculate(numInput []int, input int) []int {
	incr := 1;
	for i := 0; i < len(numInput); i += incr {
		instruction := numInput[i];
		_, B, C, opCode := parse(instruction)
		// log.Println(i, A, B, C, opCode)

		switch (opCode) {
			case 99:
				return numInput;
			case 1:
				p1 := loadParam(numInput, C, numInput[i+1]);
				p2 := loadParam(numInput, B, numInput[i+2]);
				output := numInput[i+3];

				numInput[output] = p1 + p2;
				incr = 4;
				break;
			case 2:
				p1 := loadParam(numInput, C, numInput[i+1]);
				p2 := loadParam(numInput, B, numInput[i+2]);
				output := numInput[i+3];

				numInput[output] = p1 * p2;
				incr = 4;
				break;
			case 3:
				a := numInput[i+1];
				numInput[a] = input;
				incr = 2;
				break;
			case 4:
				a := numInput[i+1];
				output := numInput[a];
				log.Printf("Output: %d\n", output);
				incr = 2;
				break;
			case 5:
				p1 := loadParam(numInput, C, numInput[i+1]);
				p2 := loadParam(numInput, B, numInput[i+2]);

				if p1 != 0 {
					i = p2;
					incr = 0;
				} else {
					incr = 3;
				}
				break;
			case 6:
				p1 := loadParam(numInput, C, numInput[i+1]);
				p2 := loadParam(numInput, B, numInput[i+2]);

				if p1 == 0 {
					i = p2;
					incr = 0;
				} else {
					incr = 3;
				}

				break;
			case 7:
				p1 := loadParam(numInput, C, numInput[i+1]);
				p2 := loadParam(numInput, B, numInput[i+2]);
				output := numInput[i+3];

				if p1 < p2 {
					numInput[output] = 1;
				} else {
					numInput[output] = 0;
				}

				incr = 4;
				break;
			case 8:
				p1 := loadParam(numInput, C, numInput[i+1]);
				p2 := loadParam(numInput, B, numInput[i+2]);
				output := numInput[i+3];

				if p1 == p2 {
					numInput[output] = 1;
				} else {
					numInput[output] = 0;
				}

				incr = 4;
				break;
			default:
				log.Fatal("Error code", opCode);
		}
	}

	return numInput;
}

func main() {
	file, err := os.Open("day5/input.txt");
	if err != nil {
		log.Fatal(err);
	}
	defer file.Close();

	scanner := bufio.NewScanner(file);

	scanner.Scan()
	nextLine := scanner.Text();
	input := strings.Split(nextLine, ",");

	numInput := []int{};
	for _, word := range input {
		num, err := strconv.Atoi(word);
		if (err != nil) {
			log.Fatal(err);
		}

		numInput = append(numInput, num)
	}

	result := calculate(numInput, 5);
	fmt.Println(result);

	if err := scanner.Err(); err != nil {
		log.Fatal(err);
	}
}