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


# part 1
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


def part1(input):
    poly = [[0, 0]]
    with open(input) as f:
        for line in f:
            instr = re.split(" ", line)
            poly.append(step(poly[-1], instr))
    # grid area[px] = points_outside + points_inside
    out = perimeter(poly)
    area = shoelace_area(poly)
    return int(out + pick(area, out))


# part 2
def step_hex(prev, inst):
    dir = int(inst[-1], base=16)
    n = int(inst[:5], base=16)
    (x, y) = prev

    match dir:
        case 3:
            return [x, y + n]
        case 1:
            return [x, y - n]
        case 2:
            return [x + n, y]
        case 0:
            return [x - n, y]


def part2(input):
    poly = [[0, 0]]
    with open(input) as f:
        for line in f:
            # ty regex101 -> find everything enclosed in "(#" and ")"
            instr = re.findall("\(#(.+)\)", line)[0]
            poly.append(step_hex(poly[-1], instr))
    # grid area[px] = points_outside + points_inside
    out = perimeter(poly)
    area = shoelace_area(poly)
    return int(out + pick(area, out))


print(part1("../input/day18.txt"))
print(part2("../input/day18.txt"))
