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

        neighbors = []
        reflections = self.mirrors[self.tiles[y][x]][dir]
        for dir_ in reflections:
            (xs, ys) = self.dirs[dir_]
            neighbors.append((x + xs, y + ys, dir_))

        return filter(self.in_bounds, neighbors)


def breadth_first_search(graph, start):
    frontier = deque()
    frontier.append(start)
    reached = {}
    reached[start] = True
    energized = set()

    while frontier:
        current = frontier.popleft()
        # print(f"  Visiting {current}")
        energized.add((current[0], current[1]))
        for next in graph.neighbors(current):
            if next not in reached:
                frontier.append(next)
                reached[next] = True

    return energized


with open("../input/day16.txt") as f:
    grid = [line.strip() for line in f.readlines()]
    graph = SquareGrid(grid)

# part 1
start = (0, 0, ">")
beam = breadth_first_search(graph, start)
print(len(beam))


# part 2
h = len(grid)
w = len(grid[0])
pos = []
pos += [(0, i) for i in range(h)]
pos += [(i, 0) for i in range(w)]
pos += [(w - 1, i) for i in range(h)]
pos += [(i, h - 1) for i in range(w)]
start = [(p[0], p[1], d) for p in pos for d in ("V", "^", ">", "<")]

beams = list(map(lambda x: len(breadth_first_search(graph, x)), start))

print(max(beams))
