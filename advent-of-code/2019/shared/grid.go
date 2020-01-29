package shared

import "fmt"

type Point struct {
	X int
	Y int
}

type Point3D struct {
	X int
	Y int
	Z int
}

func (pt Point) String() string {
	return fmt.Sprintf("%d,%d", pt.X, pt.Y)
}
