#[macro_use]
extern crate lazy_static;

use array_tool::vec::Intersect;
use regex::Regex;

lazy_static! {
  static ref WHITE_SPACE: Regex = Regex::new(r"[\s\n]").unwrap();
}

fn part_a(arr: Vec<String>) -> usize {
  let mut count = 0;
  for group in arr {
    let clean_string = WHITE_SPACE.replace_all(&group, "");
    let mut set: Vec<_> = clean_string.chars().collect();

    set.sort();
    set.dedup();

    count = count + set.len();
  }

  println!("{}", count);
  return count;
}

fn part_b(arr: Vec<String>) -> usize {
  let mut count = 0;

  for group in arr {
    let mut set: Vec<_> = "abcdefghijklmnopqrstuvwxyz".chars().collect();

    for survey in group.split("\n") {
      let b: Vec<_> = survey.chars().collect();
      set = set.intersect(b);
    }

    count = count +  set.len();
  }

  println!("{}", count);
  return count;
}

fn main() {
  let data = include_str!("../../input/day6.txt");
  let arr: Vec<String> = data
    .split("\n\n")
    .map(|val| val.parse::<String>().unwrap())
    .collect::<Vec<String>>();

  part_a(arr.clone());
  part_b(arr.clone());
}