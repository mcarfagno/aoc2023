from collections import deque

file = "../input/day23.txt"


class SquareGrid:
    def __init__(self, grid):
        self.height = len(grid)
        self.width = len(grid[0])
        self.tiles = grid

    def in_bounds(self, id):
        (x, y, d, _) = id
        return 0 <= x < self.width and 0 <= y < self.height

    def passable(self, id):
        (x, y, d, _) = id
        return self.tiles[y][x] != "#"

    def in_downhill(self, id):
        (x, y, d, _) = id
        t = self.tiles[y][x]
        match t:
            case "<":
                return d != ">"
            case ">":
                return d != "<"
            case "v":
                return d != "^"
            case "^":
                return d != "v"
            case _:
                return True

    def neighbors(self, id):
        (x, y, d, s) = id

        match d:
            case "v":
                neighbors = [
                    (x + 1, y, ">", s + 1),
                    (x - 1, y, "<", s + 1),
                    (x, y + 1, "v", s + 1),
                ]
            case "^":
                neighbors = [
                    (x + 1, y, ">", s + 1),
                    (x - 1, y, "<", s + 1),
                    (x, y - 1, "^", s + 1),
                ]
            case ">":
                neighbors = [
                    (x + 1, y, ">", s + 1),
                    (x, y - 1, "^", s + 1),
                    (x, y + 1, "v", s + 1),
                ]
            case "<":
                neighbors = [
                    (x - 1, y, "<", s + 1),
                    (x, y - 1, "^", s + 1),
                    (x, y + 1, "v", s + 1),
                ]

        neighbors = filter(self.in_bounds, neighbors)
        neighbors = filter(self.passable, neighbors)
        neighbors = filter(self.in_downhill, neighbors)
        return neighbors


def breadth_first_search(graph, start):
    frontier = deque()
    frontier.append(start)
    reached = {}
    reached[start] = True

    while frontier:
        current = frontier.pop()
        # print(f"  Visiting {current}")
        for next in graph.neighbors(current):
            if next not in reached:
                frontier.append(next)
                reached[next] = True

    return reached


def part1(file):
    grid = [line.strip() for line in open(file).readlines()]
    start = None
    h = len(grid)
    w = len(grid[0])
    for x in range(w):
        if grid[0][x] == ".":
            start = (x, 0, "v", 0)
        if grid[-1][x] == ".":
            goal = (x, h - 1, "v", 0)
    # print(f"S: {start} G: {goal}")

    graph = SquareGrid(grid)
    x = breadth_first_search(graph, start)
    x = list(x.keys())
    x = list(filter(lambda x: x[1] == h - 1, x))
    x = max([x[-1] for x in x])
    return x


print(part1(file))
