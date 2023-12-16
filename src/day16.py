from collections import deque


class SquareGrid:
    def __init__(self, grid):
        self.height = len(grid)
        self.width = len(grid[0])
        self.tiles = grid

        # direction -> next direction
        self.mirrors = {}
        self.mirrors["."] = {">": ">", "V": "V", "<": "<", "^": "^"}
        self.mirrors["|"] = {">": ("V", "^"), "<": ("V", "^"), "V": "V", "^": "^"}
        self.mirrors["-"] = {">": ">", "<": "<", "V": ("<", ">"), "^": ("<", ">")}
        self.mirrors["/"] = {">": "^", "<": "V", "V": "<", "^": ">"}
        self.mirrors["\\"] = {">": "V", "<": "^", "V": ">", "^": "<"}

        # direction -> next x y
        self.dirs = {">": (+1, 0), "<": (-1, 0), "^": (0, -1), "V": (0, +1)}

    def in_bounds(self, id):
        (x, y, _) = id
        return 0 <= x < self.width and 0 <= y < self.height

    def neighbors(self, id):
        (x, y, dir) = id
        reflections = self.mirrors[self.tiles[y][x]][dir]

        neighbors = []
        for reflection in reflections:
            step = self.dirs[reflection]
            neighbors.append((x + step[0], y + step[1], reflection))

        return filter(self.in_bounds, neighbors)


def breadth_first_search(graph, start):
    frontier = deque()
    frontier.append(start)
    reached = {}
    reached[start] = True

    while frontier:
        current = frontier.popleft()
        print(f"  Visiting {current}")
        for next in graph.neighbors(current):
            if next not in reached:
                frontier.append(next)
                reached[next] = True

    return reached


with open("../input/day16.txt") as f:
    grid = [line.strip() for line in f.readlines()]
    print(grid)
    graph = SquareGrid(grid)

    start = (0, 0, ">")
    beam = breadth_first_search(graph, start)

    # part 1
    cells = set()
    for x, y, _ in beam.keys():
        cells.add((x, y))
    print(len(cells))
