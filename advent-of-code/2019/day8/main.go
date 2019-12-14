package main

import (
	"bufio"
	"os"
	"log"
	"fmt"
	"strconv"
	"strings"
)

func count(layer []int, value int) (count int) {
	for _, pixel := range layer {
		if pixel == value {
			count++;
		}
	}
	return;
}

func part2(input []int, width int, height int) string {
	finalImage := []int{};
	for i := 0; i < width * height; i++ {
		finalImage = append(finalImage, 2);
	}

	image := [][]int{};
	image = append(image, []int{});

	rowIndex := 0;
	colIndex := 0;

	for index, pixel := range input {
		image[rowIndex] = append(image[rowIndex], pixel);

		if finalImage[colIndex] == 2 {
			finalImage[colIndex] = pixel;
		}

		colIndex++;

		if (index + 1) % (width * height) == 0 {
			image = append(image, []int{});
			rowIndex++;
			colIndex = 0;
		}
	}

	var sb strings.Builder
	for index, pixel := range finalImage {
		if index % width == 0 {
			sb.WriteString("\n");
		}

		if pixel == 1 {
			sb.WriteString(fmt.Sprintf("%d ", pixel));
		} else {
			sb.WriteString(fmt.Sprintf("  "));
		}
	}

	return sb.String()
}

func part1(input []int, width int, height int) int {
	image := [][]int{};
	image = append(image, []int{});

	layerIndex := 0;
	minLayer := image[0];
	beenSet := false;

	for index, pixel := range input {
		layer := append(image[layerIndex], pixel);
		image[layerIndex] = layer;

		if (index + 1) % (width * height) == 0 {
			if count(layer, 0) > 0 && (!beenSet || count(layer, 0) < count(minLayer, 0)) {
				minLayer = layer;
				beenSet = true;
			}

			image = append(image, []int{});
			layerIndex++;
		}
	}

	return count(minLayer, 1) * count(minLayer, 2);
}

func main() {
	file, err := os.Open("day8/input.txt");
	if err != nil {
		log.Fatal(err);
	}
	defer file.Close();

	scanner := bufio.NewScanner(file);

	scanner.Scan()
	nextLine := scanner.Text();
	rawInput := strings.Split(nextLine, "");

	input := []int{};
	for _, word := range rawInput {
		num, err := strconv.Atoi(word);
		if (err != nil) {
			log.Fatal(err);
		}

		input = append(input, num)
	}

	fmt.Println(part1(input, 25, 6));
	fmt.Println(part2(input, 25, 6));

	if err := scanner.Err(); err != nil {
		log.Fatal(err);
	}
}