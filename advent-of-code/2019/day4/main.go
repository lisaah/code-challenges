package main

import (
	"bufio"
	"os"
	"log"
	"fmt"
	"strconv"
	"strings"
)

func checkStringRules(s string) bool {
	adjacentDigits := false;
	neverDecreases := true;

	sArray := strings.Split(s, "");
	for i := 1; i < len(sArray); i++ {
		adjacentDigits = adjacentDigits || (
			sArray[i] == sArray[i-1] &&
			// toggle below conditions for part 1/2
			(i+1 >= len(sArray) || sArray[i+1] != sArray[i]) &&
			(i-2 < 0 || sArray[i-2] != sArray[i]));

		prevInt, _ := strconv.Atoi(sArray[i-1]);
		iInt, _ := strconv.Atoi(sArray[i]);
		neverDecreases = neverDecreases && prevInt <= iInt;
	}

	// log.Println(s, adjacentDigits, neverDecreases);
	return adjacentDigits && neverDecreases;
}

func calculate(start int, end int) int {
	sum := 0;
	for i := start; i <= end; i++ {
		if (i < 100000 || 999999 < i) {
			continue;
		}

		if (!checkStringRules(fmt.Sprintf("%d", i))) {
			continue;
		}

		sum++;
	}

	return sum;
}

func main() {
	file, err := os.Open("day4/input.txt");
	if err != nil {
		log.Fatal(err);
	}
	defer file.Close();

	scanner := bufio.NewScanner(file);

	scanner.Scan()
	nextLine := scanner.Text();
	input := strings.Split(nextLine, "-");

	r1, err := strconv.Atoi(input[0]);
	if (err != nil) {
		log.Fatal(err);
	}

	r2, err := strconv.Atoi(input[1]);
	if (err != nil) {
		log.Fatal(err);
	}

	result := calculate(r1, r2);

	fmt.Println(result);

	if err := scanner.Err(); err != nil {
		log.Fatal(err);
	}
}