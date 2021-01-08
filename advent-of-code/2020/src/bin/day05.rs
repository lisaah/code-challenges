
fn calc_seat_id(pass: &String) -> i32 {
  let mut low_row = 0;
  let mut high_row = 128;
  let mut low_col = 0;
  let mut high_col = 8;

  for c in pass.chars() {
    if c == 'B' {
      low_row = (low_row + high_row) / 2;
    } else if c == 'F' {
      high_row = (low_row + high_row) / 2;
    } else if c == 'R' {
      low_col = (low_col + high_col) / 2;
    } else if c == 'L' {
      high_col = (low_col + high_col) / 2;
    }
  }

  let result = low_row * 8 + low_col;

  // println!("{} = {} <- {}, {}", pass, result, low_row, low_col);
  return result;
}

fn part_a(mut arr: Vec<String>) -> i32 {
  arr.sort_by(|a, b| calc_seat_id(a).cmp(&calc_seat_id(b)));

  let max_seat_id = calc_seat_id(arr.get(arr.len() - 1).unwrap());
  return max_seat_id;
}

fn part_b(mut arr: Vec<String>) -> i32 {
  arr.sort_by(|a, b| calc_seat_id(a).cmp(&calc_seat_id(b)));

  let mut current_seat_id = calc_seat_id(arr.get(0).unwrap());
  for s in arr.iter() {
    let next_seat_id = calc_seat_id(s);

    if next_seat_id - current_seat_id > 1 {
      // println!("sus {} | {}", current_seat_id, next_seat_id);
      return next_seat_id - 1;
    }

    current_seat_id = next_seat_id;
  }

  return -1;
}

fn main() {
  let data = include_str!("../../input/day5.txt");
  let arr: Vec<String> = data
    .split("\n")
    .map(|val| val.parse::<String>().unwrap())
    .collect::<Vec<String>>();

  part_a(arr.clone());
  part_b(arr.clone());
}