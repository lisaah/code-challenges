fn part_a(arr: Vec<i32>) {
  for (i, x) in arr.iter().enumerate() {
    for y in arr.iter().skip(i) {
      if x + y == 2020 {
        println!("Item {} + {} = 2020 => {}", x, y, x * y);
      }
    }
  }
}

fn part_b(arr: Vec<i32>) {
  for (i, x) in arr.iter().enumerate() {
    for (j, y) in arr.iter().skip(i).enumerate() {
      for z in arr.iter().skip(j) {
        if x + y + z == 2020 {
          println!("Item {} + {} + {} = 2020 => {}", x, y, z, x * y * z);
        }
      }
    }
  }
}

fn main() {
  let arr: Vec<i32>  = include_str!("../../input/day1.txt")
    .split("\n")
    .map(|val| val.parse::<i32>().unwrap())
    .collect();

  part_a(arr.clone());
  part_b(arr.clone());
}