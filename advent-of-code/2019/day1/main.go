package main

import (
	"bufio"
	"os"
	"log"
	"fmt"
	"strconv"
)

func calculateFuel(mass int) int {
	return int(mass / 3) - 2;
}

func calculate(mass int, includeAdd bool) int {
	// Initial fuel needed
	sum := calculateFuel(mass);

	if (includeAdd) {
		// Fuel needed for fuel
		addFuel := sum;
		for (addFuel > 0) {
			addFuel = calculateFuel(addFuel);

			if (addFuel > 0) {
				sum += addFuel;
			}
		}
	}

	return sum;
}

func main() {
	file, err := os.Open("day1/input.txt");
	if err != nil {
		log.Fatal(err);
	}
	defer file.Close();

	scanner := bufio.NewScanner(file);

	sum := 0;
	for scanner.Scan() {
		nextLine := scanner.Text();
		mass, err := strconv.Atoi(nextLine);
		if (err != nil) {
			log.Fatal(err);
		}

		// toggle bool param for part 1/2
		result := calculate(mass, true);
		sum += result;
	}

	fmt.Println(sum);

	if err := scanner.Err(); err != nil {
		log.Fatal(err);
	}
}