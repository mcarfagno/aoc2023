from collections import deque

MAX_STEPS = 64


class SquareGrid:
    def __init__(self, grid):
        self.height = len(grid)
        self.width = len(grid[0])
        self.tiles = grid

    def in_bounds(self, id):
        (x, y, _) = id
        return 0 <= x < self.width and 0 <= y < self.height

    def passable(self, id):
        (x, y, _) = id
        return self.tiles[y][x] != "#"

    def in_max_steps(self, id):
        (_, _, s) = id
        return s <= MAX_STEPS

    def neighbors(self, id):
        (x, y, s) = id

        neighbors = [
            (x + 1, y, s + 1),
            (x - 1, y, s + 1),
            (x, y + 1, s + 1),
            (x, y - 1, s + 1),
        ]

        neighbors = filter(self.in_bounds, neighbors)
        neighbors = filter(self.passable, neighbors)
        neighbors = filter(self.in_max_steps, neighbors)
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


with open("../input/day21.txt") as f:
    grid = [line.strip() for line in f]

start = None
for y in range(len(grid)):
    for x in range(len(grid[0])):
        if grid[y][x] == "S":
            start = (x, y, 0)

graph = SquareGrid(grid)
x = breadth_first_search(graph, start)
x = list(filter(lambda x: x[2] == MAX_STEPS, list(x.keys())))
print(len(x))
