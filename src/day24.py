import re
from itertools import combinations

MIN = 200000000000000
MAX = 400000000000000
file = "../input/day24.txt"
hailstones = [h for h in open(file).readlines()]

# fotrmat [x,y,z,dx,dy,dz]
hailstones = list(
    map(
        lambda x: list(
            map(
                int,
                re.findall(
                    r"(\d+)..(\d+)..(\d+).[@].([-]?\d+)..([-]?\d+)..([-]?\d+)", x
                )[0],
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
    px = line_intersection((dx1, x1), (dx2, x2))
    py = line_intersection((dy1, y1), (dy2, y2))

    # parallel or intersected in the past
    if any([px is None, py is None]):
        return 0
    if any([px[0] <= 0, py[0] <= 0]):
        return 0
    # print(f"hailstones intersects in {px[1]}{py[1]}")
    if all([px[1] >= MIN, px[1] <= MAX, py[1] >= MIN, py[1] <= MAX]):
        return 1

    # intersects outside
    return 0


def part1():
    x = combinations(hailstones, 2)
    return sum(map(lambda x: intersects(x[0], x[1]), x))


print(part1())
