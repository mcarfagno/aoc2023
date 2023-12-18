from math import sqrt, hypot
import re


# cartesian area
# https://en.wikipedia.org/wiki/Shoelace_formula
# Triangle formula
# NOTE: here I assume the last vertex is a copy of the first for (xny1 - x1yn)
def shoelace_area(vertices):
    area = 0
    for i in range(len(vertices) - 1):
        x1, y1 = vertices[i]
        x2, y2 = vertices[i + 1]
        area += (x1 * y2) - (y1 * x2)
    area = abs(area) / 2
    return area


# Pick's theorem -> n points inside grid, given area
# https://en.wikipedia.org/wiki/Pick%27s_theorem
def pick(area, boundary):
    return area + 1 - boundary / 2


# perimeter -> aka n points on the edge of grid
# TODO: getting this from steps counter is easier...
def perimeter(vertices):
    a = []
    for i in range(1, len(vertices)):
        a.append(
            hypot(
                vertices[i][0] - vertices[i - 1][0], vertices[i][1] - vertices[i - 1][1]
            )
        )

    return sum(a)


def step(prev, inst):
    (dir, n, _) = inst
    n = int(n)
    (x, y) = prev

    match dir:
        case "U":
            return [x, y + n]
        case "D":
            return [x, y - n]
        case "L":
            return [x + n, y]
        case "R":
            return [x - n, y]


poly = [[0, 0]]
with open("../input/day18.txt") as f:
    for line in f.readlines():
        instr = re.split(" ", line)
        poly.append(step(poly[-1], instr))

# print(perimeter(poly))
# print(shoelace_area(poly))

# points outside + points inside
print(perimeter(poly) + pick(shoelace_area(poly), perimeter(poly)))
