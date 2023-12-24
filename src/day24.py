import re
from itertools import combinations

MIN = 200000000000000
MAX = 400000000000000
file = "../input/day24.txt"

# MIN = 7
# MAX = 27
# file = "../test/day24.txt"
hailstones = [h for h in open(file).readlines()]

# fotrmat [x,y,z,dx,dy,dz]
hailstones = list(
    map(
        lambda x: list(
            map(
                int,
                re.findall(r"(\d+)..(\d+)..(\d+).[@].(.?\d+)..(.?\d+)..(.?\d+)", x)[0],
            )
        ),
        hailstones,
    )
)

# print(hailstones)


# https://en.wikipedia.org/wiki/Line%E2%80%93line_intersection
def line_intersection(eq1, eq2):
    (a, c) = eq1
    (b, d) = eq2
    if a == b:
        return None
    else:
        x = (d - c) / (a - b)
        y = a * (d - c) / (a - b) + c
        return (x, y)


def intersects(h1, h2):
    (x1, y1, _, dx1, dy1, _) = h1
    (x2, y2, _, dx2, dy2, _) = h2

    # pos and vel to y = ax+b
    a1 = dy1 / dx1
    b1 = y1 - a1 * x1

    a2 = dy2 / dx2
    b2 = y2 - a2 * x2

    p = line_intersection((a1, b1), (a2, b2))

    # print(f"hailstones {h1} {h2} -> {p}")
    if p is None:
        return 0

    # was the hailstone in that point in the past?
    # x = x0 + dx*t -> t = (x-x0)/dx
    if any(
        [
            (p[0] - x1) / dx1 <= 0,
            (p[1] - y1) / dy1 <= 0,
            (p[0] - x2) / dx2 <= 0,
            (p[1] - y2) / dy2 <= 0,
        ]
    ):
        return 0

    return 1 if all([MIN <= p[0] <= MAX, MIN <= p[1] <= MAX]) else 0


def part1():
    x = combinations(hailstones, 2)
    return sum(map(lambda x: intersects(x[0], x[1]), x))


print(part1())
