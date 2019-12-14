package main

import (
	"bufio"
	"os"
	"log"
	"strings"
)

func length(key string, mapping map[string]string) int {
	total := -1;
	current := key;
	value, ok := mapping[current];
	for ok {
		value, ok = mapping[current];
		current = value;
		total++;
	}

	return total;
}

func main() {
	file, err := os.Open("day6/input.txt");
	if err != nil {
		log.Fatal(err);
	}
	defer file.Close();

	scanner := bufio.NewScanner(file);

	reverseMap := map[string]string{};
	for scanner.Scan() {
		nextLine := scanner.Text();
		input := strings.Split(nextLine, ")");

		left := input[0];
		right := input[1];

		reverseMap[right] = left;
	}

	total := 0;
	for key := range reverseMap {
		total += length(key, reverseMap);
	}

	transfers := 0;
	youLength := length("YOU", reverseMap);
	sanLength := length("SAN", reverseMap);

	youPointer := reverseMap["YOU"];
	sanPointer := reverseMap["SAN"];
	for youPointer != sanPointer {
		if youLength > sanLength {
			youPointer = reverseMap[youPointer];
			youLength--;
			transfers++;
		} else if (sanLength > youLength) {
			sanPointer = reverseMap[sanPointer];
			sanLength--;
			transfers++;
		} else {
			youPointer = reverseMap[youPointer];
			youLength--;
			transfers++;

			sanPointer = reverseMap[sanPointer];
			sanLength--;
			transfers++;
		}
	}

	log.Printf("total: %d, transfers: %d", total, transfers);

	if err := scanner.Err(); err != nil {
		log.Fatal(err);
	}
}