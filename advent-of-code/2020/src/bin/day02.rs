use std::collections::HashMap;


fn build_map(word: String) -> HashMap<char, i32> {
  let mut map: HashMap<char, i32> = HashMap::new();

  for c in word.chars() {
      let count = map.get(&c);
      let mut value = 0;

      if count.is_some() {
        value = i32::from(*count.unwrap_or(&0));
      }

      map.insert(c, value + 1);
  }

  return map;
}

fn part_a(arr: Vec<String>) -> i32 {
  let mut count = 0;
  for x in arr.iter() {
    let v: Vec<&str> = x.rsplit(':').collect();

    let char_map = build_map(v[0].trim().to_string());
    let rest: Vec<&str> = v[1].rsplit(' ').collect();
    let mut valid = true;

    for i in (0..rest.len()-1).step_by(2) {
      let character = rest[i].chars().next().unwrap();
      let range: Vec<i32> = rest[i+1].trim()
        .split('-')
        .map(|val| val.parse::<i32>().unwrap())
        .collect();
      let low = range[0];
      let high = range[1];

      let real_count = char_map.get(&character).get_or_insert(&0) as &i32;

      // println!("{} {} {}", real_count, low, high);
      if real_count < &low ||
        real_count > &high {
          println!("wtf {} {} {}", real_count, low, high);
          valid = false;
          break;
      }
    }

    println!("{:?} {}", x, valid);
    if valid {
      count = count + 1;
    }
  }

  println!("{}", count);
  return count;
}

fn part_b(arr: Vec<String>) -> i32 {
  let mut count = 0;
  for x in arr.iter() {
    let v: Vec<&str> = x.rsplit(':').collect();

    let password = v[0].trim().to_string();
    let rest: Vec<&str> = v[1].rsplit(' ').collect();
    let mut valid = true;

    for i in (0..rest.len()-1).step_by(2) {
      let character = rest[i].chars().next().unwrap();
      let range: Vec<usize> = rest[i+1].trim()
        .split('-')
        .map(|val| val.parse::<usize>().unwrap())
        .collect();
      let low = range[0];
      let high = range[1];

      let low_c = password.chars().nth(low - 1).unwrap();
      let high_c = password.chars().nth(high - 1).unwrap();

      if (character != low_c && character != high_c) ||
        (low_c == high_c) {
        valid = false;
      }

      println!("{} {} {} {}", low_c, high_c, low, high);
    }

    println!("{:?} {}", x, valid);
    if valid {
      count = count + 1;
    }
  }

  println!("{}", count);
  return count;
}

fn main() {
  let arr: Vec<String>  = include_str!("../../input/day2.txt")
    .split("\n")
    .map(|val| val.parse::<String>().unwrap())
    .collect();

  part_a(arr.clone());
  part_b(arr.clone());
}