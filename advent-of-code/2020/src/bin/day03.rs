fn calc_trees(arr: Vec<Vec<String>>, i: usize, j: usize) -> i64 {
  let height = 323;
  let mut count = 0;
  let mut row = 0;
  let mut col = 0;

  while row < height {
    if arr[row][col % 31] == "#" {
      count = count + 1;
    }
    row = row + i;
    col = col + j;
  }

  return count;
}

fn part_a(arr: Vec<Vec<String>>) -> i64 {
  let count = calc_trees(arr, 1, 3);
  println!("{}", count);
  return count
}

fn part_b(arr: Vec<Vec<String>>) -> i64 {
  let count = calc_trees(arr.clone(), 1, 1) *
    calc_trees(arr.clone(), 1, 3) *
    calc_trees(arr.clone(), 1, 5) *
    calc_trees(arr.clone(), 1, 7) *
    calc_trees(arr.clone(), 2, 1);

  println!("{}", count);
  return count;
}

fn main() {
  let arr: Vec<Vec<String>>  = include_str!("../../input/day3.txt")
    .split("\n")
    .map(|val| val.parse::<String>().unwrap())
    .map(|val| val.split("")
      .map(|s| s.parse::<String>().unwrap())
      .filter(|s| s != "")
      .collect()
    )
    .collect::<Vec<Vec<String>>>();

  part_a(arr.clone());
  part_b(arr.clone());
}