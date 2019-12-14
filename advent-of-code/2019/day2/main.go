package main

import (
	"bufio"
	"os"
	"log"
	"fmt"
	"strconv"
	"strings"
)

func calculate(numInput []int) []int {
	for i := 0; i < len(numInput); i += 4 {
		opCode := numInput[i];

		switch (opCode) {
			case 99:
				return numInput;
			case 1:
				a := numInput[i+1];
				b := numInput[i+2];
				output := numInput[i+3];
				numInput[output] = numInput[a] + numInput[b];
				break;
			case 2:
				a := numInput[i+1];
				b := numInput[i+2];
				output := numInput[i+3];
				numInput[output] = numInput[a] * numInput[b];
				break;
			default:
				log.Fatal("Error code", opCode);
		}
	}

	return numInput;
}

func copy(input []int) (c []int) {
	for i := 0; i < len(input); i++ {
		c = append(c, input[i]);
	}

	return;
}

func find(numInput []int, needle int) []int {
	for i := 0; i < len(numInput); i++ {
		for j:= 0; j < len(numInput); j++ {
			copy := copy(numInput);
			copy[1] = i;
			copy[2] = j;
			result := calculate(copy);
			if (result[0] == needle) {
				return []int{i, j};
			}
		}
	}

	return []int{-1, -1};
}

func main() {
	file, err := os.Open("day2/input.txt");
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

	// toggle function for part 1/2.
	// result := calculate(numInput);
	result := find(numInput, 19690720);

	fmt.Println(result);

	if err := scanner.Err(); err != nil {
		log.Fatal(err);
	}
}